package nl.joelchrist.shorty.shorties.domain

import nl.joelchrist.shorty.util.ShortyUtil
import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.URL

data class Shorty(@field:NotEmpty(message = "A shorty should have a full url")
                  @field:URL(message = "A shorty's full url should be a valid url")
                  val url: String = "",
                  val identifier: String? = null,
                  val name: String = ShortyUtil.generateShortIdentifier(5),
                  val owner: String? = null,
                  val metadata: ShortyMetadata? = null)