package com.vens.learn.controller;

import com.vens.learn.mq.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqController {
    @Autowired
    Sender sender;
    @GetMapping(path = "/fire")
    public boolean fire(){
        sender.send();
        return true;
    }
}
