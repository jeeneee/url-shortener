package com.jeeneee.urlshortener.repository;

import com.jeeneee.urlshortener.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String> {

    Url findByLongUrl(String longUrl);

    Url findByShortUrl(String shortUrl);
}
