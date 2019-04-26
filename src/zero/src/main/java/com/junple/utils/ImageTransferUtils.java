package com.junple.utils;

import com.alibaba.fastjson.JSONObject;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.springframework.util.Base64Utils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片转换工具类
 * @author chentao
 * @version 2019-04-10
 */
public final class ImageTransferUtils {

    private static class Holder {
        static ITesseract INSTATNCE = new Tesseract();
    }

    public static String transferImageToString(byte[] bytes) {

        String bs64 = Base64Utils.encodeToString(bytes);
        RequestBody body = new FormBody.Builder().add("bs64", bs64).build();
        String serverHost = ResourceUtils.getPropertie("properties/jeeplus.properties", "common-service-host", "");
        JSONObject resultObj = OkHttpUtils.sendPost(concatUrl(serverHost, "/ocrservice/doocr"), body, JSONObject.class);
        return resultObj.getString("data");
    }

    private static String concatUrl(String host, String path) {

        return "http://" + host + path;
    }
}
