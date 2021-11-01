package com.jeeneee.urlshortener.model;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor(access = PRIVATE)
@Document("urls")
public class Url implements Serializable {

    @Id
    private String id;

    private String longUrl;

    private String shortUrl;

    public Url(String longUrl, String shortUrl) {
        validateParameters(longUrl, shortUrl);
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    private void validateParameters(String longUrl, String shortUrl) {
        if (StringUtils.isBlank(longUrl) || StringUtils.isBlank(shortUrl)) {
            throw new IllegalArgumentException();
        }
    }
}
