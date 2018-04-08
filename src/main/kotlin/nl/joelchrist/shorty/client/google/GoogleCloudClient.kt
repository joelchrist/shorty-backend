package nl.joelchrist.shorty.client.google

import com.google.cloud.storage.Blob
import com.google.cloud.storage.Bucket
import com.google.cloud.storage.Storage
import nl.joelchrist.shorty.client.Client
import nl.joelchrist.shorty.shorties.domain.ShortyAccessType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class GoogleCloudClient(@Autowired private val bucket: Bucket) : Client {
    fun save(name: String, entity: InputStream, contentType: String, access: ShortyAccessType = ShortyAccessType.PUBLIC) =
            when(access) {
                ShortyAccessType.PUBLIC -> bucket.create(name, entity, contentType, Bucket.BlobWriteOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ))
                ShortyAccessType.PRIVATE -> bucket.create(name, entity, contentType, Bucket.BlobWriteOption.predefinedAcl(Storage.PredefinedAcl.PRIVATE))
            }

    fun find(name: String): Blob? =
            bucket.get(name)

    fun delete(name: String): Boolean =
            bucket.get(name).delete()
}