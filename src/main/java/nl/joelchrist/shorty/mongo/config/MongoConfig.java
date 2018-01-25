package nl.joelchrist.shorty.mongo.config;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    DB database(MongoClient mongoClient) {
        return mongoClient.getDB("shorty");
    }

    @Bean
    public Jongo jongo(DB database) {
        return new Jongo(database);
    }

    @Bean
    public MongoCollection shortiesCollection(Jongo jongo) {
        return jongo.getCollection("shorties");
    }
}
