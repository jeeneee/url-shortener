package com.jeeneee.urlshortener.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeeneee.urlshortener.model.CounterRange;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CounterServiceTest {

    private static final Integer COUNTER = 5;
    private static final Long SIZE = 100_000L;

    private CounterService counterService;

    @BeforeEach
    void setUp() {
        CounterRange counterRange = new CounterRange(COUNTER);
        counterService = new CounterServiceImpl(counterRange);
    }

    @DisplayName("순차적으로 증가하는 넘버를 반환한다.")
    @Test
    void getNumber() {
        IntStream.range(0, 10)
            .forEach(i -> assertThat(counterService.getNumber()).isEqualTo(COUNTER * SIZE + i));
    }
}