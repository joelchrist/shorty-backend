package nl.joelchrist.shorty.shorties.endpoints;

import nl.joelchrist.shorty.exceptions.DuplicateShortyException;
import nl.joelchrist.shorty.exceptions.EntityNotFoundException;
import nl.joelchrist.shorty.shorties.domain.Shorty;
import nl.joelchrist.shorty.shorties.managers.ShortiesManager;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.logging.Logger;

@RestController
@CrossOrigin()
public class ShortiesEndpoint {
    static Logger log = Logger.getLogger(ShortiesEndpoint.class.getName());

    @Autowired
    private ShortiesManager shortiesManager;

    @RequestMapping(value = "/shorties", method = RequestMethod.POST)
    public ResponseEntity<Shorty> createShorty(@Valid @RequestBody Shorty shorty) {
        String fullUrl = shorty.getFullUrl();
        log.info(String.format("Creating shorty for full url: %s", fullUrl));
        Shorty createdShorty = shortiesManager.createShorty(fullUrl);
        URI location = getUriFromCurrentRequest();
        return ResponseEntity.created(location).body(createdShorty);
    }

    @RequestMapping(value = "/shorties/custom", method = RequestMethod.POST)
    public ResponseEntity<Shorty> createShortyWithIdentifier(@Valid @RequestBody Shorty shorty) throws DuplicateShortyException {
        log.info(String.format("Creating shorty for full url: %s", shorty.getFullUrl()));
        Shorty createdShorty = shortiesManager.createShorty(shorty);
        URI location = getUriFromCurrentRequest();
        return ResponseEntity.created(location).body(createdShorty);
    }

    @RequestMapping(value = "/{shortIdentifier}", method = RequestMethod.GET)
    public RedirectView useShorty(@PathVariable("shortIdentifier") String shortIdentifier) throws EntityNotFoundException {
        log.info(String.format("Using shorty with shortIdentifier %s", shortIdentifier));
        Shorty shorty = shortiesManager.findShorty(shortIdentifier);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(shorty.getFullUrl());
        return redirectView;
    }

    private URI getUriFromCurrentRequest() {
        return ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    }

}
