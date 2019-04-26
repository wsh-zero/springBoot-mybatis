package com.jeeplus.common.utils;

import com.google.gson.Gson;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.io.FileUtil;
import okhttp3.*;
import org.springframework.security.access.method.P;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class OkHttpUtil {
    /**
     * 上传图片到服务器
     *
     * @param file
     * @param fileUrl
     * @param fileDir
     * @return
     */
    public static ResultUtil fileUpload(MultipartFile file, String fileUrl, String fileDir) {
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件后缀
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            // 用uuid作为文件名，防止生成的临时文件重复
            final File excelFile = File.createTempFile(UUID.randomUUID().toString(), prefix);
            // MultipartFile to File
            file.transferTo(excelFile);
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/from-data"), excelFile);
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", fileName, fileBody)
                    .addFormDataPart("fileUrl", fileUrl)
                    .addFormDataPart("fileDir", fileDir);
            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(Global.getFileServePath() + "/upload")
                    .post(requestBody)
                    .build();
            Response response = new OkHttpClient().newCall(request).execute();
            assert response.body() != null;
            String msg = response.body().string();
            if (excelFile.exists()) {
                excelFile.delete();
            }
            return new Gson().fromJson(msg, ResultUtil.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.failed(1, "上传文件失败!", e.getMessage());
        }

    }

    /**
     * 删除服务器文件
     *
     * @param filePath
     * @return
     */

    public static ResultUtil delFile(String filePath) {
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
                    .add("filePath", filePath)
                    .build();
            Request request = new Request.Builder()
                    .url(Global.getFileServePath() + "/del")
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            String msg = response.body().string();
            return new Gson().fromJson(msg, ResultUtil.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.failed(1, "删除文件失败!", e.getMessage());
        }
    }

    /**
     * 获取服务器文件大小
     *
     * @param filePath
     * @return
     */
    public static String getFileSize(String filePath) {
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
                    .add("filePath", filePath)
                    .build();
            Request request = new Request.Builder()
                    .url(Global.getFileServePath() + "/fileSize")
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            String msg = response.body().string();
            ResultUtil resultUtil = new Gson().fromJson(msg, ResultUtil.class);
            return resultUtil.getCode() == 0 ? resultUtil.getData().toString() : "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public static ResultUtil createQR(String content, String imgPath) {
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
                    .add("content", content)
                    .add("imgPath", imgPath)
                    .build();
            Request request = new Request.Builder()
                    .url(Global.getFileServePath() + "/qr")
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            String msg = response.body().string();
            return new Gson().fromJson(msg, ResultUtil.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.failed(1, "获取二维码失败!", e.getMessage());
        }
    }

    public static ResultUtil createFolder(String filePath) {
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
                    .add("filePath", filePath)
                    .build();
            Request request = new Request.Builder()
                    .url(Global.getFileServePath() + "/createFolder")
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            String msg = response.body().string();
            return new Gson().fromJson(msg, ResultUtil.class);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.failed(1,"创建文件夹失败",e.getMessage());
        }
    }


    /*public static ResultUtil deleteDirectory(String filePath) {
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
                    .add("filePath", filePath)
                    .build();
            Request request = new Request.Builder()
                    .url(Global.getFileServePath() + "/deleteDirectory")
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            String msg = response.body().string();
            return new Gson().fromJson(msg, ResultUtil.class);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.failed(1,"删除文件夹失败",e.getMessage());
        }
    }*/

}
