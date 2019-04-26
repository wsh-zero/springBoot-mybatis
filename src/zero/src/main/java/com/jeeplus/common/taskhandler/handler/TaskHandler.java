package com.jeeplus.common.taskhandler.handler;

import com.jeeplus.core.persistence.ActEntity;

/**
 * 任务处理者选择器
 * @author Junple
 */
public interface TaskHandler {

    /**
     * 选择处理者
     * @return 处理者loginname
     */
    String execute(ActEntity<?> actEntity);
}
