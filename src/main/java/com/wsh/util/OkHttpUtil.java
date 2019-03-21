package com.wsh.util;

import com.google.common.base.Strings;
import okhttp3.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class OkHttpUtil {
    private static final String SERVER = "http://localhost:2222/";

    public static ResultUtil fileUpload(MultipartFile file, String modules) {
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
                    .addFormDataPart("file", fileName, fileBody);
            if (!Strings.isNullOrEmpty(modules)) {
                builder.addFormDataPart("modules", modules);
            }
            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(SERVER + "upload")
                    .post(requestBody)
                    .build();
            Response response = new OkHttpClient().newCall(request).execute();
            assert response.body() != null;
            String msg = response.body().string();
            if (excelFile.exists()) {
                excelFile.delete();
            }
            return Utils.getGson().fromJson(msg, ResultUtil.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.failed(1, "上传文件失败!", e.getMessage());
        }

    }

    public static ResultUtil delFile(String filePath) {
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
                    .add("filePath", filePath)
                    .build();
            Request request = new Request.Builder()
                    .url(SERVER + "del")
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            String msg = response.body().string();
            return Utils.getGson().fromJson(msg, ResultUtil.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.failed(1, "删除文件失败!", e.getMessage());
        }
    }
}
