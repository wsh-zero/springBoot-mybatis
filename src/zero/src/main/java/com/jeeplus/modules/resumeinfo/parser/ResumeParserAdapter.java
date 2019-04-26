package com.jeeplus.modules.resumeinfo.parser;

import com.jeeplus.modules.resumeinfo.entity.ResumeInfo;
import com.jeeplus.modules.resumeinfo.entity.WorkExp;
import org.jsoup.nodes.Document;
import java.util.List;

public abstract class ResumeParserAdapter implements ResumeParser, ResumeItemParser {

    @Override
    public ResumeInfo parse(String host, Document doc) {

        ResumeInfo resumeInfo = parseBaseInfo(doc);
        return resumeInfo;
    }
}
