package com.jeeplus.modules.resumeinfo.parser;

import com.jeeplus.modules.resumeinfo.entity.ResumeInfo;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * author: chentao
 */
@Component
public class BaseResumeParser implements ResumeParser {

    private Map<String, ResumeParser> parserMap = new HashMap<>();

    public BaseResumeParser() {
        parserMap.put(ResumeHostNames.ZHILIAN, new ZhiLianResumeParser());
    }

    @Override
    public ResumeInfo parse(String host, Document doc) {

        ResumeParser parser = parserMap.get(host);
        ResumeInfo info = parser.parse(host, doc);
        return info;
    }
}
