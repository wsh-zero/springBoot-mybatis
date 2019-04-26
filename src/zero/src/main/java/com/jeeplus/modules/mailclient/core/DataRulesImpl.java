package com.jeeplus.modules.mailclient.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据规则实现类
 * @author chentao
 * @version 2019-04-09
 */
public class DataRulesImpl implements DataRules {

    public static class RuleItem {
        private String fieldName;
        private String rule;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }
    }

    private List<RuleItem> ruleItems = new ArrayList<>();

    @Override
    public DataRules addRule(String fieldName, String rule) {
        RuleItem item = new RuleItem();
        item.fieldName = fieldName;
        item.rule = rule;
        ruleItems.add(item);
        return this;
    }

    @Override
    public RuleItem getRule(int index) {
        return ruleItems.get(index);
    }

    @Override
    public int size() {
        return ruleItems.size();
    }
}
