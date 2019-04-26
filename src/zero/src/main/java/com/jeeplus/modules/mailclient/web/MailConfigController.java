package com.jeeplus.modules.mailclient.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.mailclient.core.DataRules;
import com.jeeplus.modules.mailclient.entity.MailConfig;
import com.jeeplus.modules.mailclient.mailhandler.ClientUnreadMailHandler;
import com.junple.jmail.client.MailClient;
import com.junple.jmail.properties.MailProperties;
import com.junple.jmail.properties.SimpleMailProperties;
import com.junple.jmail.utils.FileUtils;
import com.junple.utils.ResourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * @author junple
 */
@Controller
@RequestMapping("/${adminPath}/mailconf")
public class MailConfigController {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private ClientUnreadMailHandler unreadMailHandler;

    @RequestMapping("")
    public String getConfigPage(String saveSuccess, MailConfig config, Model model) {
        if (config == null) {
            config = new MailConfig();
        }
        MailProperties properties = mailClient.getProperties();
        if (properties != null) {
            config.setHost(properties.host());
            config.setPort(properties.port());
            config.setAddress(properties.address());
            config.setPassword(properties.password());
        }
        model.addAttribute("config", config);
        model.addAttribute("isRunning", mailClient.isRunning());
        if (mailClient.isRunning()) {
            model.addAttribute("runningTime", mailClient.getRunningTime());
        }
        if (StringUtils.isNotBlank(saveSuccess)) {
            model.addAttribute("saveSuccess", true);
        }
        return "modules/mailconf/mailconf";
    }

    @RequestMapping("/save")
    public String saveInfo(MailConfig config, Model model) throws IOException {

        MailProperties properties = new SimpleMailProperties()
                .host(config.getHost())
                .port(config.getPort())
                .address(config.getAddress())
                .password(config.getPassword());
        mailClient.setProperties(properties);
        // 序列化配置到文件中
        File file = new File("src/main/resources/data/mail/mailproperties.dat");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(properties);
        model.addAttribute("config", config);
        model.addAttribute("saveSuccess", true);
        return "redirect:../mailconf";
    }

    @RequestMapping("/start")
    public String start() {

        if (mailClient.getProperties() != null) {
            mailClient.start();
        }
        return "redirect:../mailconf";
    }

    @RequestMapping("/restart")
    public String restart() {

        mailClient.stop();
        mailClient.start();
        return "redirect:../mailconf";
    }


    @RequestMapping("/stop")
    public String stop() {

        mailClient.stop();
        return "redirect:../mailconf";
    }

    @RequestMapping("/saverules")
    public @ResponseBody String saveRules(@RequestBody String params) {

        Map<String, DataRules> rulesMap = ClientUnreadMailHandler.transferJsonToConfig(params);
        unreadMailHandler.setRulesMap(rulesMap);
        ResourceUtils.write(ClientUnreadMailHandler.DATA_PATH, params);
        return "1";
    }

    @RequestMapping("/getrules")
    public @ResponseBody String getRules() {

        JSONObject obj = new JSONObject();
        obj.put("code", "1");
        obj.put("data", JSONArray.parseArray(ResourceUtils.getContent(ClientUnreadMailHandler.DATA_PATH)));
        return obj.toJSONString();
    }
}
