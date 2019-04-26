package com.jeeplus.common.taskhandler.handler;

import com.jeeplus.core.persistence.ActEntity;

public class SimpleNameTaskHandler implements TaskHandler {

    private String name;

    public SimpleNameTaskHandler(String name) {
        this.name = name;
    }

    @Override
    public String execute(ActEntity<?> actEntity) {
        return name;
    }
}
