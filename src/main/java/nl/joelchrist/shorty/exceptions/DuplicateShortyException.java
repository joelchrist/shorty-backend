package nl.joelchrist.shorty.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateShortyException extends RuntimeException {
    private final String shortIdentifier;

    public DuplicateShortyException(String shortIdentifier) {
        super(String.format("Shorty with shortIdentifier '%s' already exists", shortIdentifier));
        this.shortIdentifier = shortIdentifier;
    }
}
