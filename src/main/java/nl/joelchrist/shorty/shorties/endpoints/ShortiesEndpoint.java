package nl.joelchrist.shorty.shorties.endpoints;

import nl.joelchrist.shorty.exceptions.DuplicateShortyException;
import nl.joelchrist.shorty.exceptions.EntityNotFoundException;
import nl.joelchrist.shorty.shorties.domain.Shorty;
import nl.joelchrist.shorty.shorties.managers.ShortiesManager;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
public class ShortiesEndpoint {
    static Logger log = Logger.getLogger(ShortiesEndpoint.class.getName());

    @Autowired
    private ShortiesManager shortiesManager;

    @RequestMapping(value = "/shorties", method = RequestMethod.POST)
    public Shorty createShorty(@Valid @URL @RequestBody String fullUrl) {
        log.info(String.format("Creating shorty for full url: %s", fullUrl));
        return shortiesManager.createShorty(fullUrl);
    }

    @RequestMapping(value = "/shorties/custom", method = RequestMethod.POST)
    public Shorty createShortyWithIdentifier(@Valid @RequestBody Shorty shorty) throws DuplicateShortyException {
        log.info(String.format("Creating shorty for full url: %s", shorty.getFullUrl()));
        return shortiesManager.createShorty(shorty);
    }

    @RequestMapping(value = "/{shortIdentifier}", method = RequestMethod.GET)
    public RedirectView useShorty(@PathVariable("shortIdentifier") String shortIdentifier) throws EntityNotFoundException {
        log.info(String.format("Using shorty with shortIdentifier %s", shortIdentifier));
        Shorty shorty = shortiesManager.findShorty(shortIdentifier);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(shorty.getFullUrl());
        return redirectView;
    }

}
