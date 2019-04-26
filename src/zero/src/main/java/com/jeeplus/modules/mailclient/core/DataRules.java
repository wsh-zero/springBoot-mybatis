package com.jeeplus.modules.mailclient.core;

/**
 * 数据规则
 * @author chentao
 * @version 2019-04-09
 */
public interface DataRules {

    DataRules addRule(String fieldName, String rule);
    DataRulesImpl.RuleItem getRule(int index);
    int size();
}
