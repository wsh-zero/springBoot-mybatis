package com.jeeplus.modules.resumeinfo.parser;

import com.jeeplus.modules.resumeinfo.entity.ResumeInfo;
import com.jeeplus.modules.resumeinfo.entity.WorkExp;
import org.jsoup.nodes.Document;
import java.util.List;

public interface ResumeItemParser {

    /**
     * 解析基本信息
     */
    ResumeInfo parseBaseInfo(Document doc);
    /**
     * 解析工作经历
     */
    List<WorkExp> parseWorkExp(Document doc);
}
