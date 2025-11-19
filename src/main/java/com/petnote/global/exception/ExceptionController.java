package com.petnote.global.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Log4j2
public class ExceptionController {

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<Map<String,Object>> handleAuth(AuthenticationException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(401)
                .body(Map.of("result","ERROR","message","아이디 또는 비밀번호가 올바르지 않습니다."));
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<Map<String,Object>> handleDenied(AccessDeniedException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(403)
                .body(Map.of("result","ERROR","message","접근 권한이 없습니다."));
    }

    @ExceptionHandler(PetNoteException.class)
    public ResponseEntity<Map<String, Object>> handlePetNote(PetNoteException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        int status = e.getStatus() == 0 ? 500 : e.getStatus();
        String message = Objects.requireNonNullElse(e.getMessage(), "처리 중 오류가 발생했습니다.");
        String result = Objects.requireNonNullElse(e.getResult(), "ERROR");

        return ResponseEntity.status(status).body(Map.of("result", result,"message", message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleOthers(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(500)
                .body(Map.of("result","ERROR","message","처리 중 오류가 발생했습니다."));
    }
}
