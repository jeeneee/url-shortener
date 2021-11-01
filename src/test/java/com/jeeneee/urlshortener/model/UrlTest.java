package com.jeeneee.urlshortener.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UrlTest {

    private static final String LONG_URL = "https://github.com/jeeneee";
    private static final String SHORT_URL = "A3ec81D";

    @DisplayName("URL 생성 - 예외 발생")
    @Test
    void createUrlWithoutAnyUrl() {
        assertThrows(IllegalArgumentException.class, () -> new Url(LONG_URL, null));
        assertThrows(IllegalArgumentException.class, () -> new Url(null, SHORT_URL));
        assertThrows(IllegalArgumentException.class, () -> new Url(null, null));
    }

    @DisplayName("URL 생성")
    @Test
    void createUrl() {
        Url url = new Url(LONG_URL, SHORT_URL);
        assertEquals(LONG_URL, url.getLongUrl());
        assertEquals(SHORT_URL, url.getShortUrl());
    }
}