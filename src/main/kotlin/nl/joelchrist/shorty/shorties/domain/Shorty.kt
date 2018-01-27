package nl.joelchrist.shorty.shorties.domain

import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotNull

data class Shorty(@NotNull(message = "A shorty should have a full url")
                  @URL(message = "A shorty's full url should be a valid url")
                  val url: String,
                  val identifier: String?)