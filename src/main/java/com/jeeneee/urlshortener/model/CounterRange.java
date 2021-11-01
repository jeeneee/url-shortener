package com.jeeneee.urlshortener.model;

import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor(access = PRIVATE)
@Component
public class CounterRange {

    private static final Long SIZE = 100_000L;

    private AtomicLong currentNumber;
    private long lastNumber;
    private boolean hasNext;

    public CounterRange(int counter) {
        initializeRange(counter);
    }

    public void initializeRange(int counter) {
        this.currentNumber = new AtomicLong(counter * SIZE);
        this.lastNumber = (counter + 1) * SIZE - 1;
        this.hasNext = true;
    }

    public long getCurrentNumber() {
        if (!this.hasNext) {
            throw new IllegalArgumentException();
        }

        long number = currentNumber.getAndIncrement();
        this.hasNext = number < lastNumber;

        return number;
    }

    public boolean hasNext() {
        return this.hasNext;
    }
}