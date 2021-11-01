package com.jeeneee.urlshortener.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class UrlResponse {

    private String longUrl;
    private String shortUrl;

    private UrlResponse(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    public static UrlResponse from(Url url) {
        return new UrlResponse(url.getLongUrl(), url.getShortUrl());
    }
}
