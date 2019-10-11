package com.luzhiqing.learn;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @Description:
 * @version:
 * @Author: 陆志庆
 * @CreateDate: 2019/9/27 17:26
 */
@Component
public class Listener {
    @Async
    @TransactionalEventListener(fallbackExecution=true)
    public void onApplicationEvent(LearnEvent event){
        try{
        Thread.sleep(300000);}catch(Exception e){
            System.out.println("");
        }
        System.out.println("good good study!!"+event.toString());
    }
}
