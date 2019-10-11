package com.luzhiqing.learn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @version:
 * @Author: 陆志庆
 * @CreateDate: 2019/9/27 17:26
 */
@Component
public class Publisher {

    @Autowired
    ApplicationContext context;

    public void publishEvent(){
        context.publishEvent(new LearnEvent(""));
    }
}
