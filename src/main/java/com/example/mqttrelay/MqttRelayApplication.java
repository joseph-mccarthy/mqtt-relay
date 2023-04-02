package com.example.mqttrelay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MqttRelayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqttRelayApplication.class, args);
	}

}
