package com.example.mqttrelay.mqtt;

import org.springframework.stereotype.Component;

import com.example.mqttrelay.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageRelay {

    private final MessageCache cache;
    private final MessageHash hash;

    public void relayMessage(Message payload) {
        if(!cache.cacheContains(hash.hash(payload))){
            log.info("Pass Message on to other brokers.");
        }
    }
    
}
