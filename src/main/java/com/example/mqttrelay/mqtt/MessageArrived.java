package com.example.mqttrelay.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import com.example.mqttrelay.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageArrived implements MqttCallback {

    private final MessageRelay relay;

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Message payload = Message.builder().topic(topic).payload(new String(message.getPayload())).build();
        relay.relayMessage(payload);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("message " + token.getMessageId() + " devlivered.");
    }

}
