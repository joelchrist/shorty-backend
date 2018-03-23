package nl.joelchrist.shorty.shorties.domain

import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.URL

data class ShortyMetadata(@field:NotEmpty(message = "Metadata should have a full url")
                          @field:URL(message = "Metadata url should be a valid url")
                          val url: String = "",
                          @field:NotEmpty(message = "Metadata should have a uuid")
                          val uuid: String = "")