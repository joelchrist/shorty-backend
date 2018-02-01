package nl.joelchrist.shorty.shorties.managers;

import nl.joelchrist.shorty.exceptions.DuplicateShortyException;
import nl.joelchrist.shorty.exceptions.EntityNotFoundException;
import nl.joelchrist.shorty.shorties.domain.Shorty;
import nl.joelchrist.shorty.shorties.repositories.ShortiesRepository;
import nl.joelchrist.shorty.util.ShortyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

@Service
public class ShortiesManager {
    @Autowired
    private ShortyUtil shortyUtil;

    @Autowired
    private ShortiesRepository shortiesRepository;

    public Shorty createShorty(String fullUrl) {
        String shortIdentifier;
        do {
            shortIdentifier = shortyUtil.generateShortIdentifier();
        } while (shortiesRepository.findShorty(shortIdentifier).isPresent());
        Shorty shorty = new Shorty(fullUrl, shortIdentifier);
        return createShorty(shorty);
    }

    public Shorty createShorty(Shorty shorty) {
        Optional<Shorty> shortyOptional = shortiesRepository.findShorty(shorty.getShortIdentifier());
        if (shortyOptional.isPresent()) {
            throw new DuplicateShortyException(shorty.getShortIdentifier());
        }
        shortiesRepository.saveShorty(shorty);
        return shorty;
    }

    public Shorty findShorty(String shortIdentifier) throws EntityNotFoundException {
        return shortiesRepository.findShorty(shortIdentifier).orElseThrow(() ->
                new EntityNotFoundException(Shorty.class, shortIdentifier));
    }
}
