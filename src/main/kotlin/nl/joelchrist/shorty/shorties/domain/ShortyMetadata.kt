package nl.joelchrist.shorty.shorties.domain

import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.URL

data class ShortyMetadata(@field:NotEmpty(message = "Metadata should have a medialink")
                          @field:URL(message = "Metadata medialink should be a valid url")
                          val medialink: String = "",
                          @field:NotEmpty(message = "Metadata should have a uuid")
                          val uuid: String = "")