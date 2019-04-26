package com.jeeplus.modules.resumeinfo.entity;

/**
 * 项目经验实体类
 * @author chentao
 * @version 2019-04-10
 */
public class ProjectExp {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 简历信息表主键
     */
    private String resumeId;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目介绍
     */
    private String intro;
    /**
     * 经历时间
     */
    private String time;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
