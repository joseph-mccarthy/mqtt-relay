package com.example.mqttrelay;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.mqttrelay.mqtt.QueuePublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublishService {
    
    private final QueuePublisher publisher;
    
    public void publishMessage(final Message message){
        publisher.publish(message);
    }
}
