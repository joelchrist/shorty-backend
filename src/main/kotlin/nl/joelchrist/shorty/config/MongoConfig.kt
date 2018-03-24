package nl.joelchrist.shorty.config

import com.mongodb.DB
import com.mongodb.MongoClient
import org.jongo.Jongo
import org.jongo.MongoCollection
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MongoConfig(@Value("\${spring.data.mongodb.database}") private val database: String, @Value("\${spring.data.mongodb.collection}") private val collection: String) {
    @Bean
    internal fun database(client: MongoClient) = client.getDB(database)

    @Bean
    fun jongo(database: DB): Jongo = Jongo(database)

    @Bean
    fun shortiesCollection(jongo: Jongo): MongoCollection = jongo.getCollection(collection)
}