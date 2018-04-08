package nl.joelchrist.shorty.shorties.repositories

import nl.joelchrist.shorty.shorties.domain.Shorty
import org.jongo.MongoCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ShortiesRepository(@Autowired private val shortiesCollection: MongoCollection) {
    fun save(entity: Shorty) {
        shortiesCollection.save(entity)
    }

    fun findByIdentifier(identifier: String) : Shorty? = shortiesCollection.findOne("{identifier: '$identifier'}").`as`(Shorty::class.java)

    fun findByOwner(owner: String) : List<Shorty>? = shortiesCollection.find("{owner: '$owner'}").`as`(Shorty::class.java).toList()

    fun findAll(): List<Shorty>? = shortiesCollection.find().`as`(Shorty::class.java).toList()
}