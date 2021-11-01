package com.jeeneee.urlshortener.service;

import com.jeeneee.urlshortener.generator.HashGenerator;
import com.jeeneee.urlshortener.model.Url;
import com.jeeneee.urlshortener.model.UrlRequest;
import com.jeeneee.urlshortener.repository.UrlRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final CounterService counterService;
    private final HashGenerator hashGenerator;

    public UrlServiceImpl(UrlRepository urlRepository, CounterService counterService,
        HashGenerator hashGenerator) {
        this.urlRepository = urlRepository;
        this.counterService = counterService;
        this.hashGenerator = hashGenerator;
    }

    @Override
    public Optional<Url> findByLongUrl(String longUrl) {
        return urlRepository.findByLongUrl(longUrl);
    }

    @Override
    public Optional<Url> findByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }

    @Override
    public Url saveIfAbsent(UrlRequest request) {
        String longUrl = request.getLongUrl();
        return findByLongUrl(longUrl).orElseGet(() -> saveUrl(longUrl));
    }

    private Url saveUrl(String longUrl) {
        String shortUrl = hashGenerator.encode(counterService.getNumber());
        return urlRepository.save(new Url(longUrl, shortUrl));
    }
}
