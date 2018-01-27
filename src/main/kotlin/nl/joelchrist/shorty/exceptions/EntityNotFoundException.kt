package nl.joelchrist.shorty.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.reflect.KClass

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Couldn't find entity with this criteria")
class EntityNotFoundException(entity: KClass<*>, criteria: String) : RuntimeException("Couldn't find $entity with criteria '$criteria'")