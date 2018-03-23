package nl.joelchrist.shorty.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "A shorty with this identifier already exists")
class DuplicateShortyException(identifier: String) : RuntimeException("Shorty with identifier '$identifier' already exists")