package nl.joelchrist.shorty.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "A shorty with this shortIdentifier already exists")
public class DuplicateShortyException extends RuntimeException {
    private final String shortIdentifier;

    public DuplicateShortyException(String shortIdentifier) {
        super(String.format("Shorty with shortIdentifier '%s' already exists", shortIdentifier));
        this.shortIdentifier = shortIdentifier;
    }
}
