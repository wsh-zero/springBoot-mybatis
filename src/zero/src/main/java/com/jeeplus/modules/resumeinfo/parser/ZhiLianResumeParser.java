package com.jeeplus.modules.resumeinfo.parser;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.resumeinfo.entity.*;
import com.junple.jmail.utils.FileUtils;
import com.junple.utils.ImageTransferUtils;
import com.junple.utils.OkHttpUtils;
import net.sourceforge.tess4j.TesseractException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.Base64Utils;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * 智联招聘解析器
 *
 * @author chentao
 * @version 2019-04-10
 */
public class ZhiLianResumeParser extends ResumeParserAdapter {

    /**
     * 联系方式
     */
    private class Contact {
        String email;
        String mobile;
    }

    // 获取联系方式的链接
    public static final String CONTACT_URL = "https://rd5.zhaopin.com/api/rd/email/landing/decrypt/info?_=1554873143962&x-zp-page-request-id=7cf61587c655465e8ab395c8a0faacac-1554873143837-144310";

    @Override
    public ResumeInfo parseBaseInfo(Document doc) {

        ResumeInfo info = new ResumeInfo();
        Element element = doc.select("#original-content")
                .select("table").get(3);
        Elements elements = element.getElementsByTag("tr");
        // 设置姓名
        info.setName(elements.get(0).children().first().text());
        Element baseInfoElmt = elements.get(1).getElementsByTag("td").first();
        Elements baseInfoElmtFont = baseInfoElmt.getElementsByTag("font");
        // 设置性别
        info.setSex(baseInfoElmtFont.get(0).text());
        // 设置工作年限
        info.setWorkYears(getDigist(baseInfoElmtFont.get(1).text()));
        String birthStr = baseInfoElmtFont.get(2).text();
        // 设置出生年月
        info.setBirth(birthStr.substring(1, birthStr.length() - 1));
        String baseInfoOwnText = baseInfoElmt.ownText();
        String[] texts = baseInfoOwnText.split(" ");
        // 设置现居住地
        info.setResidence(texts[0].replace("现居住于", ""));
        // 设置学历
        info.setEduBack(texts[1]);
        // 设置户籍地址
        info.setDomicile(texts[2].replace("户口", ""));
        // 联系方式
        Contact contact = getContact(element);
        info.setMobile(contact.mobile);
        info.setEmail(contact.email);
        // 自我评价
        Element slefEval = doc.select("td[colspan=2][style=word-break:break-all;word-wrap:break-word;padding-left:12px;]").first();
        info.setSelfEval(slefEval.text());
        info.setJobIntension(getJobIntension(doc));
        info.setWorkExps(getWorkExp(doc));
        // 设置项目经验
        info.setProjectExps(getProjectExp(doc));
        // 设置教育背景
        info.setEduBackgrounds(getEduBackground(doc));
        return info;
    }

    /**
     * 获取教育背景
     */
    private List<EduBackground> getEduBackground(Element elmt) {

        Elements elements = elmt.getElementsContainingOwnText("教育经历");
        if (elements.size() == 0) {
            return null;
        }
        Element element = elements.get(0);
        Element container = element.parent().parent().parent().parent().parent();
        Elements tables = container.children();
        List<EduBackground> edus = new ArrayList<>();
        int tableCount = 0;
        for (int i = 0; i < tables.size(); i++) {
            Element table = tables.get(i);
            if ("table".equals(table.tagName()) == false) {
                continue;
            }
            if (tableCount == 0) {
                tableCount++;
                continue;
            }
            EduBackground edu = new EduBackground();
            Elements rows = table.getElementsByTag("tbody").get(1).select("tr");
            // 设置时间
            edu.setTime(rows.get(0).getElementsByTag("td").get(0).text().trim());
            String text = rows.get(0).getElementsByTag("td").get(1).text().trim();
            String[] textSplit = text.split(" ");
            edu.setSchool(textSplit[0]);
            edu.setMajor(textSplit[1]);
            edu.setDegree(textSplit[2]);
            edus.add(edu);
        }
        return edus;
    }

    /**
     * 获取项目经验
     */
    private List<ProjectExp> getProjectExp(Element elmt) {
        Elements elements = elmt.getElementsContainingOwnText("项目经验");
        if (elements.size() == 0) {
            return null;
        }
        Element element = elements.get(0);
        Element container = element.parent().parent().parent().parent().parent();
        Elements tables = container.children();
        List<ProjectExp> exps = new ArrayList<>();
        int tableCount = 0;
        for (int i = 0; i < tables.size(); i++) {
            Element table = tables.get(i);
            if ("table".equals(table.tagName()) == false) {
                continue;
            }
            if (tableCount == 0) {
                tableCount++;
                continue;
            }
            ProjectExp exp = new ProjectExp();
            Element body = table.getElementsByTag("tbody").get(1);
            Elements rows = body.select("tr");
            // 设置时间
            exp.setTime(rows.get(0).getElementsByTag("td").get(0).text());
            // 设置项目名称
            exp.setName(rows.get(0).getElementsByTag("td").get(1).text());
            // 设置项目简介
            exp.setIntro(rows.get(1).text().replace("项目简介：", "").trim());
            exps.add(exp);
        }
        return exps;
    }

    /**
     * 获取工作经历
     */
    private List<WorkExp> getWorkExp(Element elmt) {

        Elements elements = elmt.getElementsContainingOwnText("工作经历");
        if (elements.size() == 0) {
            return null;
        }
        Element element = elements.get(0);
        Element container = element.parent().parent().parent().parent().parent().parent();
        Elements tables = container.children().get(0).children();
        List<WorkExp> exps = new ArrayList<>();
        int tableCount = 0;
        for (int i = 0; i < tables.size(); i++) {
            Element table = tables.get(i);
            if ("table".equals(table.tagName()) == false) {
                continue;
            }
            if (tableCount == 0) {
                tableCount++;
                continue;
            }
            WorkExp exp = new WorkExp();
            Element body = table.getElementsByTag("tbody").get(1);
            Elements rows = body.getElementsByTag("tr");
            // 设置工作时间
            String time = rows.get(0).getElementsByTag("td").get(0).text();
            String[] timeSplit = time.split("-");
            exp.setStartDate(timeSplit[0].trim());
            exp.setEndDate(timeSplit[1].trim());
            // 设置公司
            exp.setCompany(rows.get(0).getElementsByTag("td").get(1).text());
            // 设置岗位与薪资
            String row2 = rows.get(1).getElementsByTag("td").get(1).text();
            String[] row2split = row2.split("\\|");
            exp.setPost(row2split[0].trim());
            exp.setSalary(row2split[1].trim());
            // 设置行业
            String[] row3split = rows.get(2).getElementsByTag("td").get(1).text().split("\\|");
            exp.setIndustryType(row2split[0].trim());
            // 设置工作描述
            String desc = rows.get(3).getElementsByTag("td").get(1).text().trim();
            exp.setDescribe(desc.replace("工作描述：", ""));
            exps.add(exp);
        }
        return exps;
    }

    /**
     * 获取求职意向
     */
    private JobIntension getJobIntension(Element elmt) {

        Element tableElmt = elmt.getElementsByTag("table").get(10);
        Elements rows = tableElmt.getElementsByTag("tr");
        JobIntension intension = new JobIntension();
        // 期望工作地点
        intension.setWorkPlace(rows.get(0).getElementsByTag("td").get(1).text());
        // 期望工作性质
        intension.setWorkNature(rows.get(1).getElementsByTag("td").get(1).text());
        // 期望从事职业/岗位
        intension.setJob(rows.get(2).getElementsByTag("td").get(1).text());
        // 薪水范围
        String salary = rows.get(3).getElementsByTag("td").get(1).text();
        String[] salarySplit = salary.split("元");
        String salaryArea = salarySplit[0];
        String[] salaryAreaSplit = salaryArea.split("-");
        Integer salaryMin = Integer.valueOf(salaryAreaSplit[0]);
        Integer salaryMax = Integer.valueOf(salaryAreaSplit[1]);
        String unit = "元" + salarySplit[1];
        intension.setUnit(unit);
        intension.setMinSalary(salaryMin);
        intension.setMaxSalary(salaryMax);
        // 目前状况
        intension.setCurStatus(rows.get(4).getElementsByTag("td").get(1).text());
        // 期望从事行业
        intension.setExpectIndustry(rows.get(5).getElementsByTag("td").get(1).text());
        return intension;
    }

    /**
     * 提取联系方式
     */
    private Contact getContact(Element elmt) {
        // 获取链接地址
        Element contactUrl = elmt.getElementsByTag("a").get(0);
        String url = contactUrl.attr("href");
        // 提取链接参数，获取token
        String[] splits = url.split("=");
        String token = null;
        if (splits.length == 2) {
            token = splits[1];
        }
        // 请求数据
        JSONObject requestJson = new JSONObject();
        requestJson.put("resumeStatus", "2");
        requestJson.put("token", token);
        String resultStr = OkHttpUtils.postJson(ZhiLianResumeParser.CONTACT_URL, requestJson);
        JSONObject responseJson = JSONObject.parseObject(resultStr);
        JSONObject dataJson = responseJson.getJSONObject("data");
        String[] emailSplits = dataJson.getString("email").split(",");
        String email64 = emailSplits[emailSplits.length - 1];
        String[] mobileSplits = dataJson.getString("phone").split(",");
        String mobile64 = mobileSplits[mobileSplits.length - 1];
        // 图片文字识别
        String email = ImageTransferUtils.transferImageToString(Base64Utils.decodeFromString(email64));
        String mobile = ImageTransferUtils.transferImageToString(Base64Utils.decodeFromString(mobile64));
        Contact contact = new Contact();
        contact.email = email;
        contact.mobile = mobile;
        return contact;
    }

    @Override
    public List<WorkExp> parseWorkExp(Document doc) {
        return null;
    }

    public static Integer getDigist(String str) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch <= '9' && ch >= '0') {
                sb.append(ch);
            } else {
                break;
            }
        }
        return Integer.parseInt(sb.toString());
    }

    public static void main(String[] args) throws TesseractException {

        String content = FileUtils.readFile("E:/a.html");
        Document doc = Jsoup.parse(content);
        ResumeParser parser = new ZhiLianResumeParser();
        parser.parse(null, doc);

    }
}