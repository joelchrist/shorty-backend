package nl.joelchrist.shorty.shorties.domain

import com.fasterxml.jackson.annotation.JsonFormat
import nl.joelchrist.shorty.util.ShortyUtil
import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.URL
import java.util.Date

data class Shorty(@field:NotEmpty(message = "A shorty should have a full url")
                  @field:URL(message = "A shorty's full url should be a valid url")
                  val url: String = "",
                  val identifier: String = ShortyUtil.generateShortIdentifier(5),
                  val name: String = ShortyUtil.generateShortIdentifier(5),
                  val owner: String? = null,
                  @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                  val date: Date = Date(),
                  val metadata: ShortyMetadata? = null)