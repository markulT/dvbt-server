package com.osmos.server.banner;

import com.osmos.server.banner.exceptions.BannerFieldDoesNotExist;
import com.osmos.server.responseDto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = BannerController.class)
public class BannerAdvice {

    @ExceptionHandler(BannerFieldDoesNotExist.class)
    public ResponseEntity<ErrorResponse> handleFieldDoesNotExist(BannerFieldDoesNotExist e) {
        return ResponseEntity.status(404).body(ErrorResponse.builder().errorCode(404).errorMessage(e.getMessage()).build());
    }

}
