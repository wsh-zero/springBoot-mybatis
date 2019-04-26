package com.jeeplus.modules.resumeinfo.entity;

/**
 * 工作经历实体类
 * @author chentao
 * @version 2019-04-10
 */
public class WorkExp {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 简历信息表id
     */
    private Integer resumeId;
    /**
     * 公司名称
     */
    private String company;
    /**
     * 入职时间
     */
    private String startDate;
    /**
     * 离职时间
     */
    private String endDate;
    /**
     * 岗位名称
     */
    private String post;
    /**
     * 薪资水平
     */
    private String salary;
    /**
     * 薪资单位
     */
    private String unit;
    /**
     * 行业类型
     */
    private String industryType;
    /**
     * 工作描述
     */
    private String describe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
