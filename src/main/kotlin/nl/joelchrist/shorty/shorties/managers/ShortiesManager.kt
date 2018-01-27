package nl.joelchrist.shorty.shorties.managers

import nl.joelchrist.shorty.exceptions.DuplicateShortyException
import nl.joelchrist.shorty.exceptions.EntityNotFoundException
import nl.joelchrist.shorty.shorties.domain.Shorty
import nl.joelchrist.shorty.shorties.repositories.ShortiesRepository
import nl.joelchrist.shorty.util.ShortyUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShortiesManager(@Autowired private val shortyUtil: ShortyUtil, @Autowired private val shortiesRepository: ShortiesRepository) {
    fun create(url: String) = Shorty(url, shortyUtil.generateShortIdentifier(5))

    fun create(entity: Shorty): Shorty =
        if (entity.identifier?.let { shortiesRepository.find(it) } == null) entity.also(shortiesRepository::save)
        else throw DuplicateShortyException(entity.identifier)

    fun find(identifier: String): Shorty = shortiesRepository.find(identifier) ?: throw EntityNotFoundException(Shorty::class, identifier)
}