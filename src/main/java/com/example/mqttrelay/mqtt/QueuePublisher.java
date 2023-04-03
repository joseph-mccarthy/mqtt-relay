package com.example.mqttrelay.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import com.example.mqttrelay.Message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class QueuePublisher{

    private final MqttClient client;
    private final MessageCache cache;
    private final MessageHash hash;

    public void publish(final Message message){
        MqttMessage toPublish = new MqttMessage(message.getPayload().getBytes());
        try {
            var hashedMessage = hash.hash(message);
            cache.addItem(hashedMessage);
            client.publish(message.getTopic(), toPublish);
        } catch (MqttException e) {
            log.error("Unable to publish message", e);
        }
    }
}