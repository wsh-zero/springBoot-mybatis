package com.jeeplus.common.taskhandler.processcontroller;

import com.jeeplus.common.taskhandler.alias.AliasCache;
import com.jeeplus.common.taskhandler.exception.ErrorHandlerConfigException;
import com.jeeplus.common.taskhandler.handler.TaskHandler;
import org.springframework.util.ResourceUtils;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 资源文件流程控制器
 * @author chentao
 * @version 2019-04-02
 */
public class ResourceProcessController extends ProcessControllerAdapter {

    /**
     * 资源文件名称
     */
    private String resourceName;

    public ResourceProcessController(String resourceName) {
        this.resourceName = resourceName;
        initTaskHandler0(this);
    }

    @Override
    public void initTaskHandler(ProcessController context) {
        // donothing
    }

    private void initTaskHandler0(ProcessController context) {
        try {
            // 获取对应的资源配置文件
            File file = ResourceUtils.getFile("classpath:" + resourceName);
            // 开始解析
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ( (line = reader.readLine()) != null) {
                parseLine(line, context);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析一行配置
     */
    private void parseLine(String line, ProcessController context) {

        String[] splitStr = line.split(":");
        if (splitStr.length < 2) {
            try {
                throw new ErrorHandlerConfigException(line);
            } catch (ErrorHandlerConfigException e) {
                e.printStackTrace();
            }
        }
        String varName = splitStr[0];
        String type = splitStr[1];
        String argsStr = splitStr.length > 2 ? splitStr[2] : null;
        TaskHandler handler = null;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(parseType(type));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (argsStr != null) {
            // 解析args
            String[] args = argsStr.split(" ");
            Class<?>[] clazzes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                clazzes[i] = String.class;
            }
            Constructor constructor = null;
            try {
                constructor = clazz.getConstructor(clazzes);
                // 创建处理者实例
                handler = (TaskHandler) constructor.newInstance(args);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            // 创建处理者实例
            try {
                handler = (TaskHandler) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        context.addTaskHandler(varName, handler);
    }

    private String parseType(String type) {

        if (type.startsWith("@")) {
            String aliaName = type.substring(2, type.length() - 1);
            AliasCache cache = AliasCache.getInstance();
            return cache.getType(aliaName);
        } else {
            return type;
        }
    }
}
