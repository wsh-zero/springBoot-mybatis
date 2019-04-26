package com.jeeplus.common.taskhandler.alias;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.ResourceUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 别名缓存
 * @author chentao
 * @version 2019-04-02
 */
public class AliasCache {

    private static final AliasCache INSTANCE = new AliasCache();

    private Map<String, String> alias = new HashMap<>();

    public static AliasCache getInstance() {

        return INSTANCE;
    }

    private AliasCache() {
        InputStream is = null;
        JSONArray aliasArray = null;
        try {
            is = new FileInputStream(ResourceUtils.getFile("classpath:act/handlerconf/alias.json"));
            JSONObject json = JSON.parseObject(is, JSONObject.class);
            aliasArray = json.getJSONArray("alias");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < aliasArray.size(); i++) {
            JSONObject obj = aliasArray.getJSONObject(i);
            alias.put(obj.getString("name"), obj.getString("class"));
        }
    }

    public String getType(String name) {
        return alias.get(name);
    }
}
