package com.petnote.api.auth.controller;

import com.petnote.api.auth.dto.SignupDTO;
import com.petnote.api.auth.jwt.JwtProvider;
import com.petnote.api.auth.refresh.RefreshTokenService;
import com.petnote.api.auth.service.AuthService;
import com.petnote.api.user.dto.ResponseDTO;
import com.petnote.api.user.entity.UserEntity;
import com.petnote.api.user.service.UserService;
import com.petnote.global.config.AuthVerifyProperties;
import com.petnote.global.config.VerifyCodeService;
import com.petnote.global.exception.PetNoteException;
import com.petnote.global.utill.MailManager;
import com.petnote.global.utill.TempPasswordGenerator;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Transactional
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final AuthService authService;
    private final UserService userService;
    private final MailManager mailManager;

    private final VerifyCodeService codeSvc;
    private final AuthVerifyProperties props;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.jwt.refresh-exp-days}")
    private int REFRESH_TOKEN_EXPIRE_DAY;

    public record LoginReq(@NotBlank String userId, @NotBlank String password, String deviceId) {}
    public record TokenRes(String accessToken) {}

    @PostMapping("/signup")
    public ResponseEntity<SignupDTO> signup(@RequestBody SignupDTO dto) throws PetNoteException {
        log.info("signup dto: {}", dto);
        if(authService.signup(dto)){
            dto.setPassword(null);
            return ResponseEntity.ok().body(dto);
        }else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenRes> login(@RequestBody LoginReq req) {
        log.info("login req: {}", req);
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.userId(), req.password()));
        var user = (User) authentication.getPrincipal();
        String userId = user.getUsername();

        String access = jwtProvider.generateAccessToken(userId, Map.of("role", "USER"));
        String refresh = jwtProvider.generateRefreshToken(access);
        //TODO 운영 전환 시 redis 환경 설정 후 주석 풀기, yaml redis 주석 해제
        //refreshTokenService.save(userId, req.deviceId == null ? "device" : req.deviceId, refresh, Duration.ofDays(REFRESH_TOKEN_EXPIRE_DAY));

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refresh)
                //TODO 운영 시 secure -> true
                .httpOnly(true).secure(false)
                .sameSite("Lax")
                .path("/auth")
                .maxAge(Duration.ofDays(REFRESH_TOKEN_EXPIRE_DAY))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new TokenRes(access));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRes> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                            @RequestParam(defaultValue = "device") String deviceId) {
        if(refreshToken == null || refreshToken.isEmpty()){
            return ResponseEntity.status(401).build();
        }
        var claims = jwtProvider.parseToken(refreshToken).getBody();
        String userId = claims.getSubject();

        /*if(!refreshTokenService.validate(userId, deviceId, refreshToken)){
            return ResponseEntity.status(401).build();
        }*/

        String newAccess = jwtProvider.generateAccessToken(userId, Map.of("role", "USER"));
        //TODO 운영 전환 시 redis 환경 설정 후 주석 풀기, yaml redis 주석 해제
        //refreshTokenService.save(userId, newAccess, newRefresh, Duration.ofDays(REFRESH_TOKEN_EXPIRE_DAY));

        return ResponseEntity.ok()
                .body(new TokenRes(newAccess));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                       @RequestParam(defaultValue = "device") String deviceId) {
        if(refreshToken != null){
            String userId = jwtProvider.parseToken(refreshToken).getBody().getSubject();
            //refreshTokenService.invalidate(userId, deviceId);
        }
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                //TODO 운영 시 secure -> true
                .secure(false)
                .sameSite("Lax")
                .path("/auth")
                .maxAge(0)
                .build();
        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    @GetMapping("/check-userId")
    public ResponseEntity<ResponseDTO> checkId(@RequestParam String userId) {
        Optional<UserEntity> user = userService.getUserByUserId(userId);
        if(user.isPresent()){
            return ResponseEntity.ok().body(
                    ResponseDTO.builder()
                            .message("불가능 ID")
                            .status(false)
                            .build());
        }else{
            return ResponseEntity.ok().body(
                    ResponseDTO.builder()
                            .message("가능 ID")
                            .status(true)
                            .build());
        }
    }

    @GetMapping("/find-id/request")
    public ResponseEntity<ResponseDTO> sendFindIdCode(@RequestParam String email) throws Exception {
        Optional<UserEntity> user = userService.getUserByEmail(email);
        if(user.isEmpty()){
            throw PetNoteException.builder().status(400).message("등록된 계정이 없습니다.").build();
        }
        String code = codeSvc.issueCode(VerifyCodeService.Purpose.FIND_ID, email, null);

        mailManager.codeSendMail(email,code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/find-id/verify")
    public ResponseEntity<ResponseDTO> findIdVerify(@RequestBody Map<String,String> body) {
        String email = body.get("email");
        String code  = body.get("code");
        if (!codeSvc.verifyCode(VerifyCodeService.Purpose.FIND_ID, email, null, code))
            return ResponseEntity.ok(ResponseDTO
                    .builder()
                    .status(false)
                    .build());
        Optional<UserEntity> user = userService.getUserByEmail(email);
        if(user.isEmpty()){
            return ResponseEntity.ok(ResponseDTO
                    .builder()
                    .status(false)
                    .build());
        }
        return ResponseEntity.ok(ResponseDTO
                .builder()
                .status(true)
                .message(user.get().getUserId())
                .build());
    }

    @PostMapping("/reset-password/request")
    public ResponseEntity<ResponseDTO> sendResetPwCode(@RequestBody Map<String,String> body) throws Exception {
        String userId = body.get("userId");
        String email = body.get("email");
        Optional<UserEntity> user = userService.getUserByUserIdAndEmail(userId, email);
        if(user.isEmpty()){
            throw PetNoteException.builder().status(400).message("등록된 계정이 없습니다.").build();
        }
        String code = codeSvc.issueCode(VerifyCodeService.Purpose.FIND_ID, email, userId);

        mailManager.codeSendMail(email,code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password/verify")
    public ResponseEntity<ResponseDTO> resetPwVerify(@RequestBody Map<String,String> body) throws Exception {
        String userId = body.get("userId");
        String email = body.get("email");
        String code  = body.get("code");

        if (!codeSvc.verifyCode(VerifyCodeService.Purpose.FIND_ID, email, userId, code))
            return ResponseEntity.ok(ResponseDTO
                    .builder()
                    .status(false)
                    .build());

        String resetPw = TempPasswordGenerator.generate();
        int count = userService.updateTempPasswordByUserIdAndEmail(userId, email, passwordEncoder.encode(resetPw));
        if(count == 0){
            throw PetNoteException.builder().status(400).message("처리 중 오류가 발생했습니다.").build();
        }

        mailManager.resetPwSendMail(email, resetPw);
        return ResponseEntity.ok(ResponseDTO
                .builder()
                .status(true)
                .build());
    }

}
