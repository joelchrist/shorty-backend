package nl.joelchrist.shorty.shorties.endpoints

import nl.joelchrist.shorty.shorties.domain.Shorty
import nl.joelchrist.shorty.shorties.managers.ShortiesManager
import nl.joelchrist.shorty.util.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.web.servlet.view.RedirectView
import javax.validation.Valid

@RestController
@CrossOrigin
class ShortiesEndpoint(@Autowired private val shortiesManager: ShortiesManager){
    companion object {
        private val log = getLogger(ShortiesEndpoint::class)
        private fun getUriFromCurrentRequest() = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()
    }

    @RequestMapping(value = ["/shorties", "/shorties/custom"],  method = [(RequestMethod.POST)])
    fun createShorty(@Validated @RequestBody shorty: Shorty) : ResponseEntity<Shorty> =
            ResponseEntity
                    .created(getUriFromCurrentRequest())
                    .body(shorty.takeIf { it.identifier != null }?.let(shortiesManager::create) ?: shorty.url.let(shortiesManager::create))
                    .also { log.info("Creating shorty for url: ${shorty.url}") }

    @RequestMapping(value = ["/{identifier}"],  method = [(RequestMethod.GET)])
    fun useShorty(@PathVariable(value = "identifier") identifier: String) : RedirectView =
            RedirectView()
                    .also { it.url = shortiesManager.find(identifier).url }
                    .also { log.info("Using shorty with identifier $identifier") }

}