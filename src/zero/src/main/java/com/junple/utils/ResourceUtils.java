package com.junple.utils;

import com.jeeplus.common.utils.base.PropertiesUtil;
import com.junple.jmail.utils.FileUtils;
import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源文件工具类
 * @author chentao
 * @version 2019-04-11
 */
public class ResourceUtils {

    private static final Map<String, Properties> propertiesCache = new ConcurrentHashMap<>();

    /**
     * 获取路径文件
     */
    public static File getFile(String path) {
        String realPath = ResourceUtils.class.getClassLoader().getResource(path).getPath();
        return new File(realPath);
    }

    /**
     * 获取文件流
     */
    public static InputStream getStream(String path) {
        File file = getFile(path);
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return is;
    }

    /**
     * 获取文件内容
     */
    public static String getContent(String path) {
        InputStream is = getStream(path);
        return FileUtils.read(is);
    }

    /**
     * 写入文件
     */
    public static void write(String path, byte[] bytes) {
        File file = getFile(path);
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 写入文件
     */
    public static void write(String path, String content) {
        write(path, content.getBytes());
    }
    /**
     * 获取配置项
     */
    public static String getPropertie(String path, String name, String defaultValue) {

        Properties properties = propertiesCache.get(path);
        if (properties == null) {
            properties = PropertiesUtil.loadFromFile("classpath://" + path);
            propertiesCache.put(path, properties);
        }
        return PropertiesUtil.getString(properties, name, defaultValue);
    }
}
