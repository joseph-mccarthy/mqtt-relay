package com.example.mqttrelay.mqtt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.var;

@Component
public class MessageRecieved implements MqttCallback {

    private MqttClient client;
    private Set<String> map = Collections.synchronizedSet(new HashSet<>());
    
    public MessageRecieved() throws MqttException, NoSuchAlgorithmException {
        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);

        this.client = new MqttClient("tcp://pi-3.local:1883", "random", new MemoryPersistence());
        this.client.setCallback(this);
        this.client.connect(conOpt);

        this.client.subscribe("#", 0);

    }

    @Scheduled(fixedRate = 100)
    public void clearMap() {
        System.out.println("Clearing Map " + new Date());
        map.clear();
    }

    @Scheduled(fixedRate = 1)
    public void messageFromRest() throws NoSuchAlgorithmException, MqttPersistenceException, MqttException {
        var topic = "test";
        var message = UUID.randomUUID().toString();
        map.add(hashme(topic,message));
        var m = new MqttMessage(message.getBytes());
        this.client.publish(topic,m);
    }

    public void messageArrived(String topic, MqttMessage message) throws MqttException, NoSuchAlgorithmException {
        var hash = hashme(topic, message.toString());
        if (!map.contains(hash)) {
            sendToOthers(topic, message);
        }
        map.remove(hash);
        System.out.println(map.size());
    }

    private void sendToOthers(String topic, MqttMessage message) {
        System.out.println(topic + " : " + new String(message.getPayload()));
    }

    private String hashme(String topic, String message) throws NoSuchAlgorithmException{
        var value = topic + message;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(value.getBytes());

        return new String(md.digest());
    }

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}