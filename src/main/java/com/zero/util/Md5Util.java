package com.zero.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    /**
     * 利用MD5进行加密
     */
    public static String md5Encode(String pwd, String amount) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            return base64en.encode(md5.digest((pwd + amount).getBytes("utf-8")));
        } catch (NoSuchAlgorithmException n) {
            n.printStackTrace();
        } catch (UnsupportedEncodingException u) {
            u.printStackTrace();
        }
        //加密出错设置为1111
        return "1111";
    }

    public static boolean md5dEcode(String pwd, String amount, String oldpasswd) {
        return md5Encode(pwd, amount).equalsIgnoreCase(oldpasswd);
    }

}
