package site.moasis.moasisapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record CommonResponse<T>(T data, String message) {

    public static <T> ResponseEntity<CommonResponse<T>> success(T data, String message) {
        return new ResponseEntity<>(new CommonResponse<>(data, message), HttpStatus.OK);
    }

    public static <T> ResponseEntity<CommonResponse<T>> error(T data, HttpStatus status, String message) {
        return new ResponseEntity<>(new CommonResponse<>(data, message), status);
    }
}

