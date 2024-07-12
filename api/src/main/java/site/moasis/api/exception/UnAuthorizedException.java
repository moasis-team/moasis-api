package site.moasis.api.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends ServerException {
    public UnAuthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
