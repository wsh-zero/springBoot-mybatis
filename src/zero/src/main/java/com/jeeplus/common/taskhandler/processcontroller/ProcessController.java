package com.jeeplus.common.taskhandler.processcontroller;

import com.jeeplus.common.taskhandler.handler.TaskHandler;
import com.jeeplus.core.persistence.ActEntity;

import java.util.Map;

/**
 * 流程控制器
 * @author chentao
 * @version 2019-04-04
 */
public interface ProcessController {

    void initTaskHandler(ProcessController context);
    ProcessController addTaskHandler(String varName, TaskHandler handler);
    ProcessController bindTable(String tbName);
    /**
     * 添加流程控制器
     */
    ProcessController addTaskHandler(TaskHandler handler);
    Map<String, Object> execute(ActEntity<?> entity);
    Map<String, Object> execute(ActEntity<?> entity, Map<String, Object> vars);
}
