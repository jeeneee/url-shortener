package com.jeeneee.urlshortener.controller;

import com.jeeneee.urlshortener.model.Url;
import com.jeeneee.urlshortener.model.UrlRequest;
import com.jeeneee.urlshortener.service.UrlService;
import java.io.IOException;
import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    private final UrlService urlService;

    @Value("${application.base-url}")
    private String baseUrl;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<Void> createShortUrl(@Valid @RequestBody UrlRequest request) {
        Url url = urlService.saveIfAbsent(request);
        return ResponseEntity.created(URI.create(baseUrl + url.getShortUrl())).build();
    }

    @GetMapping("/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response)
        throws IOException {
        Url url = urlService.findByShortUrl(shortUrl);
        String redirectUrl = url == null ? baseUrl : url.getLongUrl();
        response.sendRedirect(redirectUrl);
    }
}
