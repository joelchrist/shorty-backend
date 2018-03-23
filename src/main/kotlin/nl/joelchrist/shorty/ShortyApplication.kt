package nl.joelchrist.shorty

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ShortyApplication

fun main(args: Array<String>) {
    SpringApplication.run(ShortyApplication::class.java, *args)

}