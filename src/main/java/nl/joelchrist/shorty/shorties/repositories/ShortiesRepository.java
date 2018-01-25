package nl.joelchrist.shorty.shorties.repositories;

import nl.joelchrist.shorty.shorties.domain.Shorty;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ShortiesRepository {

    @Autowired
    private MongoCollection shortiesCollection;

    public void saveShorty(Shorty shorty) {
        shortiesCollection.save(shorty);
    }

    public Optional<Shorty> findShorty(String shortIdentifier) {
        Shorty shorty = shortiesCollection.findOne("{shortIdentifier: #}", shortIdentifier).as(Shorty.class);
        return Optional.ofNullable(shorty);
    }
}
