package com.jeeplus.modules.resumeinfo.parser;

import com.jeeplus.modules.resumeinfo.entity.ResumeInfo;
import org.jsoup.nodes.Document;

/**
 * 简历解析器
 * @author chentao
 * @version 2019-04-10
 */
public interface ResumeParser {

    /**
     * 解析简历信息
     */
    ResumeInfo parse(String host, Document doc);

}
