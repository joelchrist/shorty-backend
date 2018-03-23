package nl.joelchrist.shorty.util

import nl.joelchrist.shorty.shorties.domain.Shorty
import org.apache.commons.text.RandomStringGenerator
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ShortyUtil {
    companion object {
        fun generateShortIdentifier(@Value("\${shorty.identifier.length}") length: Int) = RandomStringGenerator.Builder()
                .withinRange(charArrayOf('a', 'z'))
                .build()
                .generate(length)!!
    }
}

fun Shorty.transformShorty(url: String? = null, identfier: String? = null, name: String? = null, owner: String? = null): Shorty =
        this.copy(url = url ?: this.url,
                identifier = identfier ?: this.identifier,
                name = name ?: this.name,
                owner = owner ?: this.owner)