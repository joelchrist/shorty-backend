package nl.joelchrist.shorty.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    private final Class clazz;

    public EntityNotFoundException(Class clazz, String criteria) {
        super(String.format("Could't find %s with criteria '%s'", clazz, criteria));
        this.clazz = clazz;
    }
}
