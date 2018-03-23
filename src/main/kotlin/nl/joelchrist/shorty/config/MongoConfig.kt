package nl.joelchrist.shorty.config

import com.mongodb.DB
import com.mongodb.MongoClient
import org.jongo.Jongo
import org.jongo.MongoCollection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MongoConfig {
    @Bean
    internal fun database(client: MongoClient) = client.getDB("shorty")

    @Bean
    fun jongo(database: DB): Jongo = Jongo(database)

    @Bean
    fun shortiesCollection(jongo: Jongo): MongoCollection = jongo.getCollection("shorties")
}