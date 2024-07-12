package site.moasis.api.exception;

import org.springframework.http.HttpStatus;

public abstract class ServerException extends RuntimeException {
    private final HttpStatus status;

    public ServerException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
