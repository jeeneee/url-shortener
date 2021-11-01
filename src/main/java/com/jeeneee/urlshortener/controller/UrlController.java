package com.jeeneee.urlshortener.controller;

import com.jeeneee.urlshortener.model.Url;
import com.jeeneee.urlshortener.model.UrlRequest;
import com.jeeneee.urlshortener.model.UrlResponse;
import com.jeeneee.urlshortener.service.UrlService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/url")
@RestController
public class UrlController {

    private final UrlService urlService;

    @Value("${application.base-url}")
    private String baseUrl;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<UrlResponse> createShortUrl(@Valid @RequestBody UrlRequest request) {
        Url url = urlService.saveIfAbsent(request);
        return ResponseEntity.created(URI.create(baseUrl + '/' + url.getShortUrl()))
            .body(UrlResponse.from(url));
    }
}
