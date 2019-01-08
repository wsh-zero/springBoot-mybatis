package com.wsh.util;

public class FileUtil {

    /**
     * 文件上传根目录
     */
    public static final String ROOT_PATH = "D:/resoures/file/";

    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     *
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String uuid, String fileOriginName) {
        return uuid + FileUtil.getSuffix(fileOriginName);
    }
}
