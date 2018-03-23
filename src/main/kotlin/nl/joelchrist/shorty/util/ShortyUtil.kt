package nl.joelchrist.shorty.util

import org.apache.commons.text.RandomStringGenerator
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ShortyUtil {
    fun generateShortIdentifier(@Value("\${shorty.identifier.length}") length: Int) = RandomStringGenerator.Builder()
            .withinRange(charArrayOf('a', 'z'))
            .build()
            .generate(length)!!
}