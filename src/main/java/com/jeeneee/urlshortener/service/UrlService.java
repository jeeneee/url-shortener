package com.jeeneee.urlshortener.service;

import com.jeeneee.urlshortener.model.Url;
import com.jeeneee.urlshortener.model.UrlRequest;
import java.util.Optional;

public interface UrlService {

    Optional<Url> findByLongUrl(String longUrl);

    Optional<Url> findByShortUrl(String shortUrl);

    Url saveIfAbsent(UrlRequest request);
}
