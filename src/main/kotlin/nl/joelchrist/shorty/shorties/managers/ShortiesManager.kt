package nl.joelchrist.shorty.shorties.managers

import nl.joelchrist.shorty.exceptions.DuplicateShortyException
import nl.joelchrist.shorty.exceptions.EntityNotFoundException
import nl.joelchrist.shorty.shorties.domain.Shorty
import nl.joelchrist.shorty.shorties.repositories.ShortiesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShortiesManager(@Autowired private val shortiesRepository: ShortiesRepository) {
    fun create(entity: Shorty): Shorty =
            (entity.identifier.let { shortiesRepository.findByIdentifier(it) })
                    ?.let { throw DuplicateShortyException(entity.identifier) }
                    ?: entity.also(shortiesRepository::save)

    fun findByIdentifier(identifier: String): Shorty = shortiesRepository.findByIdentifier(identifier) ?: throw EntityNotFoundException(Shorty::class, identifier)

    fun findByOwner(owner: String): List<Shorty>? = shortiesRepository.findByOwner(owner)

    fun all(): List<Shorty>? = shortiesRepository.findAll()

    fun delete(identifier: String): Boolean = shortiesRepository.delete(identifier).takeIf { it == true } ?: throw EntityNotFoundException(Shorty::class, identifier)
}