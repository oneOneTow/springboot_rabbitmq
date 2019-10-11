package com.luzhiqing.learn;

import org.springframework.context.ApplicationEvent;

/**
 * @Description:
 * @version:
 * @Author: 陆志庆
 * @CreateDate: 2019/9/27 17:27
 */
public class LearnEvent extends ApplicationEvent {
    public LearnEvent(Object source) {
        super(source);
    }
}
