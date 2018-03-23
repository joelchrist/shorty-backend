package nl.joelchrist.shorty.shorties.endpoints

import nl.joelchrist.shorty.exceptions.EntityNotFoundException
import nl.joelchrist.shorty.shorties.domain.Shorty
import nl.joelchrist.shorty.shorties.domain.ShortyMetadata
import nl.joelchrist.shorty.shorties.managers.ShortiesManager
import nl.joelchrist.shorty.util.getLogger
import org.hibernate.validator.constraints.NotBlank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
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
import org.springframework.web.servlet.view.RedirectView
import java.util.UUID
import javax.validation.constraints.NotNull

@RestController
@CrossOrigin
class ShortiesEndpoint(@Autowired private val shortiesManager: ShortiesManager) {
    companion object {
        private val log = getLogger(ShortiesEndpoint::class)
        private fun getUriFromCurrentRequest() = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()
    }

    @RequestMapping(value = ["/shorties"], method = [(RequestMethod.POST)])
    fun createShorty(@Validated @RequestBody shorty: Shorty): ResponseEntity<Shorty> =
            ResponseEntity
                    .created(getUriFromCurrentRequest())
                    .body(shorty.takeIf { it.identifier != null }?.let(shortiesManager::create) ?: shorty.url.let(shortiesManager::create).let(shortiesManager::create))
                    .also { log.info("Creating shorty for url ${shorty.url}") }

    @RequestMapping(value = ["/files"], method = [(RequestMethod.POST)])
    fun createFile(@Validated @NotNull @NotBlank file: MultipartFile): ResponseEntity<ShortyMetadata> =
            ResponseEntity
                    .created(getUriFromCurrentRequest())
                    .body(UUID.randomUUID().let { ShortyMetadata(shortiesManager.create(it, file), it.toString()) })
                    .also { log.info("Creating file ${file.originalFilename}") }

    @RequestMapping(value = ["/{identifier}"], method = [(RequestMethod.GET)])
    fun useShorty(@PathVariable(value = "identifier") identifier: String): RedirectView =
            RedirectView()
                    .also { it.url = shortiesManager.find(identifier).url }
                    .also { log.info("Using shorty with identifier $identifier") }

    @RequestMapping(value = ["files/{identifier}"], method = [(RequestMethod.GET)])
    fun useFile(@PathVariable(value = "identifier") identifier: String): ResponseEntity<Resource> =
            ResponseEntity
                    .ok()
                    .let {
                        val resource = shortiesManager.find(identifier)
                                .takeUnless { it.metadata == null }
                                ?.let { shortiesManager.findFile(identifier, it.metadata!!.uuid) }
                                .also { log.info("Using file with identifier $identifier") }
                                ?: throw EntityNotFoundException(Shorty::class, identifier)

                        it
                                .header(HttpHeaders.CONTENT_DISPOSITION, resource.contentDisposition)
                                .contentLength(resource.getContent().size.toLong())
                                .contentType(MediaType.parseMediaType(resource.contentType))
                                .body(ByteArrayResource(resource.getContent()))
                    }
}