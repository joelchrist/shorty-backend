package nl.joelchrist.shorty.shorties.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

public class Shorty {
    @NotNull(message = "A shorty should have a full url")
    @NotEmpty(message = "A shorty should have a full url")
    @URL(message = "A shorty's full url should be a valid url")
    private String fullUrl;

    private String shortIdentifier;

    public Shorty(String fullUrl, String shortIdentifier) {
        this.fullUrl = fullUrl;
        this.shortIdentifier = shortIdentifier;
    }

    public Shorty() {
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getShortIdentifier() {
        return shortIdentifier;
    }

    public void setShortIdentifier(String shortIdentifier) {
        this.shortIdentifier = shortIdentifier;
    }

}
