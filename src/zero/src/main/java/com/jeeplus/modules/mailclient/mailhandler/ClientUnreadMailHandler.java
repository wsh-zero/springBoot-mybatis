package com.jeeplus.modules.mailclient.mailhandler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.mailclient.core.DataRules;
import com.jeeplus.modules.mailclient.core.DataRulesImpl;
import com.jeeplus.modules.resumeinfo.entity.ResumeInfo;
import com.jeeplus.modules.resumeinfo.parser.ResumeParser;
import com.jeeplus.modules.resumeinfo.service.ResumeInfoService;
import com.junple.jmail.client.MailClient;
import com.junple.jmail.entity.MailObject;
import com.junple.jmail.handler.UnreadMailHandler;
import com.junple.utils.ResourceUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;

/**
 * 未读邮件处理器实现类
 * @author chentao
 * @version 2019-04-09
 */
public class ClientUnreadMailHandler extends UnreadMailHandler {

    private Map<String, DataRules> rulesMap;

    public static final String DATA_PATH = "data/mail/rulesconf.json";

    @Autowired
    private ResumeInfoService resumeInfoService;

    @Autowired
    private ResumeParser resumeParser;

    public ClientUnreadMailHandler() {
        // rulesMap = initRules();
    }

    @Override
    public void readMessage(MailClient context, MailObject mailObject) {

        // 首先解析邮箱域名
        String address = mailObject.getSender();
        String host = getMailHost(address);
        String content = mailObject.getTextContent();
        Document doc = Jsoup.parse(content);
        ResumeInfo resumeInfo = resumeParser.parse(host, doc);
        resumeInfo.setContent(content);
        resumeInfoService.insert(resumeInfo);
    }

    private Map<String, DataRules> initRules() {

        String data = ResourceUtils.getContent(ClientUnreadMailHandler.DATA_PATH);
        return transferJsonToConfig(data);
    }

    public void setRulesMap(Map<String, DataRules> rulesMap) {
        this.rulesMap = rulesMap;
    }

    private Map<String, String> executeRules(Document document, DataRules rules) {

        Map<String, String> resultMap = new HashMap<>();
        for (int i = 0; i < rules.size(); i ++) {
            DataRulesImpl.RuleItem item = rules.getRule(i);
            String name = item.getFieldName();
            String rule = item.getRule();
            // jsoup选取元素值
            Elements elements = document.select(rule);
            if (elements.size() >= 1) {
                resultMap.put(name, elements.get(0).text());
            } else {
                resultMap.put(name, null);
            }
        }
        return resultMap;
    }

    private String getMailHost(String address) {
        String[] splits = address.split("@");
        if (splits.length == 2) {
            return splits[1];
        }
        else {
            return "";
        }
    }

    public static Map<String, DataRules> transferJsonToConfig(String jsonStr) {

        JSONArray array = JSONArray.parseArray(jsonStr);
        Map<String, DataRules> rulesMap = new HashMap<>();
        for (int i = 0; i < array.size() ; i++) {
            JSONObject obj = array.getJSONObject(i);
            String name = obj.getString("name");
            String host = obj.getString("host");
            DataRules rules = new DataRulesImpl();
            JSONArray ruleArray = obj.getJSONArray("rules");
            for (int j = 0; j < ruleArray.size() ; j++) {
                JSONObject ruleItem = ruleArray.getJSONObject(j);
                rules.addRule(ruleItem.getString("name"), ruleItem.getString("rule"));
            }
            rulesMap.put(host, rules);
        }
        return rulesMap;
    }
}
