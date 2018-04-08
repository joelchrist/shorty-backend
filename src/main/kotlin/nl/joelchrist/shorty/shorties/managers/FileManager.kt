package nl.joelchrist.shorty.shorties.managers

import com.google.cloud.storage.Blob
import nl.joelchrist.shorty.client.google.GoogleCloudClient
import nl.joelchrist.shorty.exceptions.EntityNotFoundException
import nl.joelchrist.shorty.shorties.domain.ShortyMetadata
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class FileManager(@Autowired private val storageClient: GoogleCloudClient) {
    fun create(uuid: UUID, file: MultipartFile): String =
            file.let { storageClient.save(uuid.toString(), file.inputStream, file.contentType).mediaLink }

    fun find(uuid: String): Blob =
            storageClient.find(uuid) ?: throw EntityNotFoundException(ShortyMetadata::class, uuid)

    fun delete(uuid: String): Boolean =
        storageClient.delete(uuid).takeIf { it } ?: throw EntityNotFoundException(ShortyMetadata::class, uuid)
}