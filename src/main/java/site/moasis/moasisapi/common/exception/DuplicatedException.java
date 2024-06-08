package site.moasis.moasisapi.common.exception;

import org.springframework.http.HttpStatus;

public class DuplicatedException extends ServerException {
    public DuplicatedException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
