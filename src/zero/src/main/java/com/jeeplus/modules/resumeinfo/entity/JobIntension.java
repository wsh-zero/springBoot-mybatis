package com.jeeplus.modules.resumeinfo.entity;

/**
 * 求职意向实体类
 * @author chentao
 * @version 2019-04-10
 */
public class JobIntension {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 简历表id
     */
    private String resumeId;
    /**
     * 期望工作地区
     */
    private String workPlace;
    /**
     * 期望工作性质
     */
    private String workNature;
    /**
     * 期望从事职业/岗位
     */
    private String job;
    /**
     * 最低薪水
     */
    private Integer minSalary;
    /**
     * 最高薪水
     */
    private Integer maxSalary;
    /**
     * 薪水单位
     */
    private String unit;
    /**
     * 目前状况
     */
    private String curStatus;
    /**
     * 期望从事行业
     */
    private String expectIndustry;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResumeId() {
        return resumeId;
    }

    public void setResumeId(String resumeId) {
        this.resumeId = resumeId;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkNature() {
        return workNature;
    }

    public void setWorkNature(String workNature) {
        this.workNature = workNature;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(String curStatus) {
        this.curStatus = curStatus;
    }

    public String getExpectIndustry() {
        return expectIndustry;
    }

    public void setExpectIndustry(String expectIndustry) {
        this.expectIndustry = expectIndustry;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
