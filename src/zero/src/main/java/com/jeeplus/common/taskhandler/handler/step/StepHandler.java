package com.jeeplus.common.taskhandler.handler.step;

import com.jeeplus.common.taskhandler.handler.TaskHandler;
import com.jeeplus.core.persistence.ActEntity;

/**
 * 步骤处理器
 * @author chentoa
 * @version 2019-04-04
 */
public abstract class StepHandler implements TaskHandler {

    @Override
    public String execute(ActEntity<?> actEntity) {
        onStep(actEntity);
        return null;
    }

    public abstract void onStep(ActEntity<?> actEntity);
}
