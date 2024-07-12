package site.moasis.api.exception;

import org.springframework.http.HttpStatus;

public class UnSupportedNetworkException extends ServerException {
    public UnSupportedNetworkException(String message) {
        super(HttpStatus.NOT_IMPLEMENTED, message);
    }
}
