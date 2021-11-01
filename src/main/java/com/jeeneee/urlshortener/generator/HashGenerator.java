package com.jeeneee.urlshortener.generator;

import org.springframework.stereotype.Component;

@Component
public class HashGenerator {

    private static final int LENGTH = 7;
    private static final long MAX_NUMBER = 3_521_614_606_207L;
    private static final char[] BASE62 = {'K', 'i', 'w', '0', 'r', '3', 'a', 'n', 'p', 'A', 'q',
        'Y', 't', 'M', 'e', 'Z', 'N', 'L', 'H', 'f', 'v', 'I', '9', 'g', 'c', 'T', 'F', 'u', 'B',
        'x', 'E', '7', '1', 'j', 'Q', 'P', 'd', 'D', '2', 'X', '4', 'b', 'O', 'V', 'W', 'y', 'C',
        '6', 'l', 'o', 'h', '8', 'z', 'S', '5', 'J', 's', 'G', 'k', 'R', 'U', 'm'};

    public String encode(long number) {
        if (number < 0 || number > MAX_NUMBER) {
            throw new IllegalArgumentException("number must be in given range");
        }

        var result = new StringBuilder(LENGTH);
        while (number > 0) {
            result.append(BASE62[(int) (number % BASE62.length)]);
            number /= BASE62.length;
        }
        while (result.length() < LENGTH) {
            result.append(BASE62[0]);
        }

        return result.reverse().toString();
    }
}
