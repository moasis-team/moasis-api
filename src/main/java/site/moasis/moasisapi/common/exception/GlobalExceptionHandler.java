package site.moasis.moasisapi.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.moasis.moasisapi.common.response.CommonResponse;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<CommonResponse<Void>> handleCustomException(ServerException e) {
        logger.info("[Error]: status: {}, message: {}", e.getStatus(), e.getMessage());
        return CommonResponse.error(null, e.getStatus(), e.getMessage());
    }

    // http 메서드 DTO validation 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Void>> handleConstraintViolationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();

        logger.info("[Validation Error]: {}", errorMessage);
        return CommonResponse.error(null, HttpStatus.BAD_REQUEST, errorMessage);
    }
}

