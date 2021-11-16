package com.jeeneee.urlshortener.service;

import static org.apache.curator.framework.CuratorFrameworkFactory.newClient;

import com.jeeneee.urlshortener.model.CounterRange;
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
        try (var client = newClient(baseUrl, new RetryNTimes(3, 100))) {
            client.start();
            Integer counter = Integer.valueOf(new String(client.getData().forPath(path)));
            counter++;
            client.setData().forPath(path, String.valueOf(counter).getBytes());
            return counter;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}