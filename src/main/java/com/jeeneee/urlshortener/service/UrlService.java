package com.jeeneee.urlshortener.service;

import com.jeeneee.urlshortener.model.Url;
import com.jeeneee.urlshortener.model.UrlRequest;

public interface UrlService {

    Url findByLongUrl(String longUrl);

    Url findByShortUrl(String shortUrl);

    Url saveIfAbsent(UrlRequest request);
}
