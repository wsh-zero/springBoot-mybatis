package com.wsh.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileUtil {

    /**
     * 根目录
     */
    public static final String CATALOG_PATH = "D:";
    /**
     * 文件路径
     */
    public static final String RELATIVE_PATH = "/resoures/file/";
    /**
     * 绝对路径
     */
    public static final String ROOT_PATH = CATALOG_PATH + RELATIVE_PATH;
    /**
     * 文件路径
     */
    public static final String SUFFIX_JPG = ".jpg";

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

    public static void uploadPicture(String fileName, MultipartFile file) {
        try {
//            if (null == file) {
//                return ResultUtil.failed(1, "获取文件信息失败!!");
//            }
            // 获取文件名
//            String originalFilename = file.getOriginalFilename();
//            if (Strings.isNullOrEmpty(originalFilename)) {
//                return ResultUtil.failed(1, "获取文件名称失败!!");
//            }
//            String fileName = FileUtil.getFileName(uuid, originalFilename);
            File dest = new File(ROOT_PATH + fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);
//            return ResultUtil.success("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
//            return ResultUtil.failed(1, "上传失败", e.getMessage());
        }

    }

    /**
     * 删除单个文件
     */
    public static void deleteFile(String fileName) {
        File file = new File(ROOT_PATH + fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }
}
