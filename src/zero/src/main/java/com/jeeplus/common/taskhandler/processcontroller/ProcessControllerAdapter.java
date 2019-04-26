package com.jeeplus.common.taskhandler.processcontroller;

import com.jeeplus.common.taskhandler.alias.ProcessControllerCache;
import com.jeeplus.common.taskhandler.handler.TaskHandler;
import com.jeeplus.core.persistence.ActEntity;
import java.util.*;

/**
 * 流程控制器适配器
 * @author chentao
 * @version 2019-04-04
 */
public abstract class ProcessControllerAdapter implements ProcessController {

    protected Map<String, TaskHandler> handlers = new HashMap<>();
    protected List<TaskHandler> stepHandlers = new ArrayList<>();
    protected String tableName = "";

    public ProcessControllerAdapter() {
        initTaskHandler(this);
    }

    @Override
    public ProcessController addTaskHandler(String varName, TaskHandler handler) {
        handlers.put(varName, handler);
        return this;
    }

    @Override
    public ProcessController addTaskHandler(TaskHandler handler) {
        stepHandlers.add(handler);
        return this;
    }

    @Override
    public Map<String, Object> execute(ActEntity<?> entity) {

        Map<String, Object> vars = new HashMap<>();
        return execute(entity, vars);
    }

    @Override
    public ProcessController bindTable(String tbName) {
        this.tableName = tbName;
        ProcessControllerCache.getInstance().add(tbName, this);
        return this;
    }

    @Override
    public Map<String, Object> execute(ActEntity<?> entity, Map<String, Object> vars) {
        for (Map.Entry<String, TaskHandler> entry : handlers.entrySet()) {

            TaskHandler handler = entry.getValue();
            String result = handler.execute(entity);
            vars.put(entry.getKey(), result);
        }
        for (TaskHandler handler : stepHandlers) {
            handler.execute(entity);
        }
        return vars;
    }
}
