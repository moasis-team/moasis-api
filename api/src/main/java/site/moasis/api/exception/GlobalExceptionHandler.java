package site.moasis.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.moasis.api.interceptor.CommonHttpRequestInterceptor;
import site.moasis.api.response.CommonResponse;
import site.moasis.api.response.ErrorCode;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<CommonResponse<Void>> handleCustomException(ServerException e) {
        log.info("[Error]: status: {}, message: {}", e.getStatus(), e.getMessage());
        return CommonResponse.error(null, e.getStatus(), e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String eventId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
        log.warn("[ServerException] eventId = {}, errorMsg = {}", eventId, NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        BindingResult bindingResult = e.getBindingResult();
        FieldError fe = bindingResult.getFieldError();
        if (fe != null) {
            String message = "Request Error" + " " + fe.getField() + "=" + fe.getRejectedValue() + " (" + fe.getDefaultMessage() + ")";
            return CommonResponse.error(ErrorCode.COMMON_INVALID_PARAMETER.name(), message);
        } else {
            return CommonResponse.error(ErrorCode.COMMON_INVALID_PARAMETER.name(), ErrorCode.COMMON_INVALID_PARAMETER.getErrorMsg());
        }
    }
}

