package com.jeeplus.modules.resumeinfo.entity;

/**
 * 学历实体类
 * @author chentao
 * @version 2019-04-10
 */
public class EduBackground {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 简历表主键
     */
    private String resumeId;
    /**
     * 时间
     */
    private String time;
    /**
     * 学校
     */
    private String school;
    /**
     * 专业
     */
    private String major;
    /**
     * 学位
     */
    private String degree;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
