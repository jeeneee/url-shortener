package com.jeeneee.urlshortener.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.jeeneee.urlshortener.generator.HashGenerator;
import com.jeeneee.urlshortener.model.CounterRange;
import com.jeeneee.urlshortener.model.Url;
import com.jeeneee.urlshortener.model.UrlRequest;
import com.jeeneee.urlshortener.repository.UrlRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    private static final String LONG_URL = "https://urlshortener.shop/abcdefghijklmnopqrstuvwxyz";
    private static final String SHORT_URL = "A3ec81D";

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private CounterService counterService;
    private HashGenerator hashGenerator;
    private UrlService urlService;
    private Url url;

    @BeforeEach
    void setUp() {
        hashGenerator = new HashGenerator();
        urlService = new UrlServiceImpl(urlRepository, counterService, hashGenerator);
        url = new Url(LONG_URL, SHORT_URL);
    }

    @DisplayName("Long URL이 없으면 빈 객체를 반환한다.")
    @Test
    void findByLongUrl_Absent_ReturnEmpty() {
        given(urlRepository.findByLongUrl(any())).willReturn(Optional.empty());

        assertThat(urlService.findByLongUrl(LONG_URL)).isEmpty();
    }

    @DisplayName("Long URL이 있으면 해당 URL을 반환한다.")
    @Test
    void findByLongUrl_Exists_ReturnUrl() {
        given(urlRepository.findByLongUrl(any())).willReturn(Optional.of(url));

        assertThat(urlService.findByLongUrl(LONG_URL)).containsSame(url);
    }

    @DisplayName("Short URL이 없으면 빈 객체를 반환한다.")
    @Test
    void findByShortUrl_Absent_ReturnEmpty() {
        given(urlRepository.findByShortUrl(any())).willReturn(Optional.empty());

        assertThat(urlService.findByShortUrl(SHORT_URL)).isEmpty();
    }

    @DisplayName("Short URL이 있으면 해당 URL 반환한다.")
    @Test
    void findByShortUrl_Exists_ReturnUrl() {
        given(urlRepository.findByShortUrl(any())).willReturn(Optional.of(url));

        assertThat(urlService.findByShortUrl(SHORT_URL)).containsSame(url);
    }

    @DisplayName("저장된 URL이 있으면 해당 URL을 반환한다.")
    @Test
    void saveIfAbsent_Exists_ReturnUrl() {
        given(urlRepository.findByLongUrl(any())).willReturn(Optional.of(url));

        assertThat(urlService.findByLongUrl(LONG_URL)).containsSame(url);
    }

    @DisplayName("저장된 URL이 없으면 Short URL을 생성하고 저장한다.")
    @Test
    void saveIfAbsent_Absent_SaveUrl() {
        UrlRequest request = new UrlRequest(LONG_URL);
        given(urlRepository.findByLongUrl(any())).willReturn(Optional.empty());
        given(counterService.getNumber()).willReturn(1_000_000_000L);
        given(urlRepository.save(any(Url.class))).willReturn(url);

        assertThat(urlService.saveIfAbsent(request)).isSameAs(url);
    }
}