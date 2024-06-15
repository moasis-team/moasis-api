package site.moasis.moasisapi.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.moasis.moasisapi.common.response.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<CommonResponse<Void>> handleCustomException(ServerException e) {
        logger.info("[Error]: status: {}, message: {}", e.getStatus(), e.getMessage());
        return CommonResponse.error(null, e.getStatus(), e.getMessage());
    }
}

