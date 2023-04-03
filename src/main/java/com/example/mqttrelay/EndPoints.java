package com.example.mqttrelay;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class EndPoints {
    
    private final PublishService publishService;

    @RequestMapping(method = RequestMethod.POST)
    @Async
    public void distributeMessage(@RequestBody final Message message){
         publishService.publishMessage(message);   
    }
}
