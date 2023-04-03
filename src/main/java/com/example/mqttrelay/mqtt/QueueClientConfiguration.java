package com.example.mqttrelay.mqtt;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueClientConfiguration{


    @Bean
    public MqttClient getClient(final MessageArrived callback) throws MqttSecurityException, MqttException{
        MqttConnectOptions conOpt = new MqttConnectOptions();
        // conOpt.setCleanSession(true);
        conOpt.setAutomaticReconnect(true);

        MqttClient client = new MqttClient("tcp://pi-3.local:1883", UUID.randomUUID().toString(), new MemoryPersistence());
        client.setCallback(callback);
        client.connect(conOpt);

        client.subscribe("#");

        return client;
    }

}
