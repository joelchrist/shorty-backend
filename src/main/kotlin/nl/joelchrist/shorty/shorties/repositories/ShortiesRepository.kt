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

    fun find(identifier: String) : Shorty? = shortiesCollection.findOne("{identifier: '$identifier'}").`as`(Shorty::class.java)
}