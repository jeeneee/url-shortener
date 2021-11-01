package com.jeeneee.urlshortener.model;

import static lombok.AccessLevel.PRIVATE;

import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class UrlRequest {

    @Pattern(regexp = "[(https?)://(www.)?a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&//=]*)")
    private String longUrl;

    public UrlRequest(String longUrl) {
        this.longUrl = longUrl;
    }
}
