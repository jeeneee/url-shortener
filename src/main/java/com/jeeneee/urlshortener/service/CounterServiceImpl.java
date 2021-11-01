package com.jeeneee.urlshortener.service;

import static org.apache.curator.framework.CuratorFrameworkFactory.newClient;

import com.jeeneee.urlshortener.model.CounterRange;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.VersionedValue;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl implements CounterService {

    @Value("${spring.zookeeper.base-url}")
    private String baseUrl;

    @Value("${spring.zookeeper.counter-path}")
    private String path;

    private final CounterRange counterRange;

    public CounterServiceImpl(CounterRange counterRange) {
        this.counterRange = counterRange;
    }

    @Override
    public long getNumber() {
        if (!counterRange.hasNext()) {
            Integer counter = getCounter();
            counterRange.initializeRange(counter);
        }
        return counterRange.getCurrentNumber();
    }

    private Integer getCounter() {
        var client = newClient(baseUrl, new RetryNTimes(3, 100));
        client.start();
        var sharedCounter = new SharedCount(client, path, 0);
        try {
            sharedCounter.start();

            VersionedValue<Integer> counter = sharedCounter.getVersionedValue();
            while (!sharedCounter.trySetCount(counter, counter.getValue() + 1)) {
                counter = sharedCounter.getVersionedValue();
            }

            sharedCounter.close();
            client.close();

            return counter.getValue();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}