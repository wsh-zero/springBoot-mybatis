package com.wsh.util.rly;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.wsh.util.ResultUtil;

import java.util.HashMap;

/**
 * 容联云短信发送封装
 */
public class RlySMS {
    private static CCPRestSmsSDK restAPI;
    private static final String ACCOUNT_SID = "8aaf070869dc0b880169dcfa4326005c";
    private static final String ACCOUNT_TOKEN = "ac05f4ac48584247aed007b2d9290e6f";
    private static final String APP_ID = "8aaf070869dc0b880169dcfa43860062";

    static {
        restAPI = new CCPRestSmsSDK();
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN);
        restAPI.setAppId(APP_ID);
    }

    /**
     * @param phones     手机号,多个用(,)逗号隔开
     * @param templateId 短信模板ID
     * @param datas      短信相关内容
     * @return
     */
    public static ResultUtil rlySendSms(String phones, String templateId, String[] datas) {
        HashMap<String, Object> result = restAPI.sendTemplateSMS(phones, templateId, datas);
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
//            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
//            Set<String> keySet = data.keySet();
//            for (String key : keySet) {
//                Object object = data.get(key);
//                System.out.println(key + " = " + object);
//            }
            return ResultUtil.success("发送成功!!");
        } else {
            return ResultUtil.failed(1, String.format("错误码= %s 错误信息= %s", result.get("statusCode"), result.get("statusMsg")));
        }
    }

}
