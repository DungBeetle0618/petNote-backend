package com.petnote.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petnote.api.auth.controller.AuthController;
import com.petnote.api.auth.dto.LoginDTO;
import com.petnote.api.auth.dto.SignupDTO;
import com.petnote.api.auth.refresh.RefreshTokenService;
import com.petnote.api.user.repository.UserRepository;
import com.petnote.api.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Test
    @DisplayName("회원가입 성공 테스트")
    @Disabled("임시")
    void signUpTest() throws Exception {
        // given
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setUserId("swkim1");
        signupDTO.setEmail("swkim@wezlesoft.co.kr");
        signupDTO.setPassword("qwer1234!");

        // when
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupDTO)))
            .andExpect(status().isOk());

        // then
        assertTrue(userService.getUserByUserId("swkim1").isPresent());
    }
    
    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccessTest() throws Exception {
        AuthController.LoginReq loginReq = new AuthController.LoginReq("swkim", "qwer1234!", "S21");

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("refreshToken"))
                .andReturn();

        Cookie refreshTokenCookie = result.getResponse().getCookie("refreshToken");
        assertNotNull(refreshTokenCookie, "refreshToken 쿠키가 존재해야 함");
        assertTrue(refreshTokenCookie.isHttpOnly(), "HttpOnly 속성이 설정되어야 함");
        System.out.println("refreshToken = " + refreshTokenCookie.getValue());

        String responseBody = result.getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(responseBody);
        String accessToken = json.get("accessToken").asText();
        assertNotNull(accessToken, "accessToken이 body에 존재해야 함");
        System.out.println("accessToken = " + accessToken);

        //assertTrue(refreshTokenService.validate(loginReq.userId(), loginReq.deviceId(), refreshTokenCookie.getValue()));
        //System.out.println("refreshTokenService.validate = " + refreshTokenService.validate(loginReq.userId(), loginReq.deviceId(), refreshTokenCookie.getValue()));
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void loginFailTest() throws Exception {
        AuthController.LoginReq loginReq = new AuthController.LoginReq("swkim", "qwer1234!!", "S21");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그인 후 요청 테스트")
    void loginSuccessAndRequestTest() throws Exception {
        AuthController.LoginReq loginReq = new AuthController.LoginReq("swkim", "qwer1234!", "S21");

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("refreshToken"))
                .andReturn();

        Cookie refreshTokenCookie = result.getResponse().getCookie("refreshToken");
        assertNotNull(refreshTokenCookie, "refreshToken 쿠키가 존재해야 함");
        assertTrue(refreshTokenCookie.isHttpOnly(), "HttpOnly 속성이 설정되어야 함");
        System.out.println("refreshToken = " + refreshTokenCookie.getValue());

        String responseBody = result.getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(responseBody);
        String accessToken = json.get("accessToken").asText();
        assertNotNull(accessToken, "accessToken이 body에 존재해야 함");
        System.out.println("accessToken = " + accessToken);

        // 만료 accessT 접근
        MvcResult result1 = mockMvc.perform(get("/user/"+loginReq.userId())
                        .header("Authorization", "Bearer " + expiredAccessToken(loginReq.userId()))
                        .cookie(refreshTokenCookie))
                .andExpect(status().is4xxClientError())
                .andReturn();
        
        // accessT 발급 요청
        MvcResult result2 = mockMvc.perform(post("/auth/refresh")
                .header("Authorization", "Bearer " + expiredAccessToken(loginReq.userId()))
                .cookie(refreshTokenCookie))
                .andExpect(status().isOk())
                .andReturn();

        //assertTrue(refreshTokenService.validate(loginReq.userId(), loginReq.deviceId(), refreshTokenCookie.getValue()));
        //System.out.println("refreshTokenService.validate = " + refreshTokenService.validate(loginReq.userId(), loginReq.deviceId(), refreshTokenCookie.getValue()));
    }

    @Test
    @DisplayName("로그아웃 테스트")
    void logoutTest() throws Exception {
        AuthController.LoginReq loginReq = new AuthController.LoginReq("swkim", "qwer1234!", "S21");

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("refreshToken"))
                .andReturn();

        Cookie refreshTokenCookie = result.getResponse().getCookie("refreshToken");
        assertNotNull(refreshTokenCookie, "refreshToken 쿠키가 존재해야 함");
        assertTrue(refreshTokenCookie.isHttpOnly(), "HttpOnly 속성이 설정되어야 함");
        System.out.println("refreshToken = " + refreshTokenCookie.getValue());

        String responseBody = result.getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(responseBody);
        String accessToken = json.get("accessToken").asText();
        assertNotNull(accessToken, "accessToken이 body에 존재해야 함");
        System.out.println("accessToken = " + accessToken);

        MvcResult result1 = mockMvc.perform(post("/auth/logout")
                .cookie(refreshTokenCookie))
                .andExpect(status().isNoContent())
                .andReturn();

        Cookie refreshTokenCookie1 = result1.getResponse().getCookie("refreshToken");
        assert refreshTokenCookie1 != null;
        assertTrue(refreshTokenCookie1.getValue().isEmpty(), "refreshToken value empty");


    }
    
    private String expiredAccessToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
            .setSubject(username)
            .claim("token_type", "access")
            .setIssuer("petNote")
            .setIssuedAt(Date.from(now.minusSeconds(120)))
            .setExpiration(Date.from(now.minusSeconds(60))) // 이미 과거 → 만료
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode("aZbK93jf39Jf02FslkFja3021hfjs93Kd8sfl3kfj9w9s2dkj3")), SignatureAlgorithm.HS256)
            .compact();
    }
    
}
