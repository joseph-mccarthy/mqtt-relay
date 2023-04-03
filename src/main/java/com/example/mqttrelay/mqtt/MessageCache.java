package com.example.mqttrelay.mqtt;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageCache {
    private Set<String> cache = Collections.synchronizedSet(new HashSet<>());

    public void addItem(String data){
        cache.add(data);
    }

    public void removeItem(String data){
        cache.remove(data);
    }

    @Scheduled(fixedRate = 100)
    public void clearCache(){
        cache.clear();
    }

    public boolean cacheContains(String data){
        return cache.contains(data);
    }
}
