package nl.joelchrist.shorty.shorties.endpoints

import nl.joelchrist.shorty.shorties.domain.Shorty
import nl.joelchrist.shorty.shorties.domain.ShortyMetadata
import nl.joelchrist.shorty.shorties.managers.FileManager
import nl.joelchrist.shorty.shorties.managers.ShortiesManager
import nl.joelchrist.shorty.util.getLogger
import org.hibernate.validator.constraints.NotBlank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID
import javax.servlet.http.HttpServletResponse
import javax.validation.constraints.NotNull

@RestController
@CrossOrigin
class ShortiesEndpoint(@Autowired private val shortiesManager: ShortiesManager, @Autowired private val fileManager: FileManager) {
    companion object {
        private val log = getLogger(ShortiesEndpoint::class)
        private fun getUriFromCurrentRequest() = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()
    }

    @RequestMapping(value = ["/shorties"], method = [(RequestMethod.POST)])
    fun createShorty(@Validated @RequestBody shorty: Shorty): ResponseEntity<Shorty> =
            ResponseEntity
                    .created(getUriFromCurrentRequest())
                    .body(shorty.let(shortiesManager::create))
                    .also { log.info("Creating shorty for url ${shorty.url}") }

    @RequestMapping(value = ["/files"], method = [(RequestMethod.POST)])
    fun createFile(@Validated @NotNull @NotBlank file: MultipartFile): ResponseEntity<ShortyMetadata> =
            ResponseEntity
                    .created(getUriFromCurrentRequest())
                    .body(UUID.randomUUID().let { ShortyMetadata(fileManager.create(it, file), it.toString()) })
                    .also { log.info("Creating file ${file.originalFilename}") }

    @RequestMapping(value = ["/{identifier}"], method = [(RequestMethod.GET)])
    fun useShorty(@PathVariable(value = "identifier") identifier: String, res: HttpServletResponse): ResponseEntity<Any> =
            shortiesManager.find(identifier)
                    .let {
                        when (it.metadata) {
                            null -> {
                                res.sendRedirect(it.url)
                                log.info("Using file with identifier $identifier")
                                ResponseEntity.ok().build()
                            }
                            else -> {
                                val resource = it
                                        .let { fileManager.find(it.metadata!!.uuid) }
                                        .also { log.info("Using file with identifier $identifier") }

                                ResponseEntity
                                        .ok()
                                        .header(HttpHeaders.CONTENT_DISPOSITION, resource.contentDisposition)
                                        .contentLength(resource.getContent().size.toLong())
                                        .contentType(MediaType.parseMediaType(resource.contentType))
                                        .body(ByteArrayResource(resource.getContent()))
                            }
                        }
                    }
}