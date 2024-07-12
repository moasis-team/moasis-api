package site.moasis.api.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ServerException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
