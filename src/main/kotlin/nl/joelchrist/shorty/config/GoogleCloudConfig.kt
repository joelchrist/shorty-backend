package nl.joelchrist.shorty.config

import com.google.cloud.storage.Bucket
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class GoogleCloudConfig(@Value("\${google.cloud.bucket.name}") private val name: String) {
    @Bean
    fun storage(): Storage = StorageOptions.getDefaultInstance().service!!

    @Bean
    fun bucket(storage: Storage): Bucket = storage.get(name)
}