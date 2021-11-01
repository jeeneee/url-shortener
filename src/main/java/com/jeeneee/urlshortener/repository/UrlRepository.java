package com.jeeneee.urlshortener.repository;

import com.jeeneee.urlshortener.model.Url;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String> {

    Optional<Url> findByLongUrl(String longUrl);

    Optional<Url> findByShortUrl(String shortUrl);
}
