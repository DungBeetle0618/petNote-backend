package com.petnote.global.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Log4j2
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> exception(Exception e) {
        log.error(e);
        int status = 500;
        String message = "처리 중 오류가 발생했습니다.";

        if(e instanceof PetNoteException e1) {
            message = e1.getMessage();
            status = e1.getStatus() == 0 ? 500 : e1.getStatus();
        }

        return ResponseEntity.status(status).body(Map.of("message", message));

    }
}
