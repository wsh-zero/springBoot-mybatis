package com.jeeplus.common.taskhandler.alias;

import com.jeeplus.common.taskhandler.processcontroller.ProcessController;
import java.util.HashMap;
import java.util.Map;

public class ProcessControllerCache {

    private Map<String, ProcessController> cache = new HashMap<>();
    public static final ProcessControllerCache INSTANCE = new ProcessControllerCache();

    private ProcessControllerCache() {

    }

    public static ProcessControllerCache getInstance() {
        return INSTANCE;
    }

    public void add(String name, ProcessController controller) {
        cache.put(name, controller);
    }

    public ProcessController get(String name) {
        return cache.get(name);
    }
}
