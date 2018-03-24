package nl.joelchrist.shorty.shorties.managers

import com.google.cloud.storage.Blob
import nl.joelchrist.shorty.client.google.GoogleCloudClient
import nl.joelchrist.shorty.exceptions.DuplicateShortyException
import nl.joelchrist.shorty.exceptions.EntityNotFoundException
import nl.joelchrist.shorty.shorties.domain.Shorty
import nl.joelchrist.shorty.shorties.repositories.ShortiesRepository
import nl.joelchrist.shorty.util.ShortyUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class ShortiesManager(@Autowired private val shortiesRepository: ShortiesRepository, @Autowired private val storageClient: GoogleCloudClient) {
    fun create(url: String) = Shorty(url, ShortyUtil.generateShortIdentifier(5))

    fun create(entity: Shorty): Shorty =
            (entity.identifier?.let { shortiesRepository.find(it) })
                    ?.let { throw DuplicateShortyException(entity.identifier) }
                    ?: entity.also(shortiesRepository::save)

    fun create(uuid: UUID, file: MultipartFile): String =
            file.let { storageClient.save(uuid.toString(), file.inputStream, file.contentType).mediaLink }

    fun find(identifier: String): Shorty = shortiesRepository.find(identifier) ?: throw EntityNotFoundException(Shorty::class, identifier)

    fun findFile(identifier: String, uuid: String): Blob =
            shortiesRepository.find(identifier)
                    ?.let { storageClient.find(uuid) }
                    ?: throw EntityNotFoundException(Shorty::class, identifier)
}