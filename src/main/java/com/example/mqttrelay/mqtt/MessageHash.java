package com.example.mqttrelay.mqtt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.example.mqttrelay.Message;

@Component
public class MessageHash {

    public String hash(Message message) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(message.toString().getBytes());

            return new String(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }

    }
}
