package com.jeeneee.urlshortener.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CounterRangeTest {

    private static final Integer COUNTER = 5;
    private static final Long SIZE = 100_000L;
    private CounterRange counterRange;

    @BeforeEach
    void setUp() {
        counterRange = new CounterRange(COUNTER);
    }

    @DisplayName("범위 초기화")
    @Test
    void initializeRange() {
        counterRange.initializeRange(COUNTER);
        assertAll(
            () -> assertTrue(counterRange.hasNext()),
            () -> assertEquals(COUNTER * SIZE + SIZE - 1, counterRange.getLastNumber())
        );
    }

    @DisplayName("SIZE 초과하는 횟수의 넘버를 가져오면 예외가 발생한다.")
    @Test
    void getCurrentNumber_OutOfRange_ExceptionThrown() {
        IntStream.range(0, Math.toIntExact(SIZE)).forEach(i -> counterRange.getCurrentNumber());
        assertThrows(IllegalArgumentException.class, () -> counterRange.getCurrentNumber());
    }

    @DisplayName("현재 인스턴스에서 관리하는 넘버를 가져온다.")
    @Test
    void getCurrentNumber() {
        assertEquals(COUNTER * SIZE, counterRange.getCurrentNumber());
        assertEquals(COUNTER * SIZE + 1, counterRange.getCurrentNumber());
    }
}