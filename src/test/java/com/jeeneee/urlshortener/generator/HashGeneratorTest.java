package com.jeeneee.urlshortener.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HashGeneratorTest {

    private final HashGenerator hashGenerator = new HashGenerator();

    @DisplayName("유니크 해시 생성 - 범위를 벗어나면 예외가 발생한다.")
    @Test
    void encode_OutOfRange_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class,
            () -> hashGenerator.encode(-1));
        assertThrows(IllegalArgumentException.class,
            () -> hashGenerator.encode(3_521_614_606_208L));
    }

    @DisplayName("7자리 유니크한 해시 생성")
    @Test
    void encode_Normal_Success() {
        assertEquals(7, hashGenerator.encode(10_000_000_000L).length());
    }
}