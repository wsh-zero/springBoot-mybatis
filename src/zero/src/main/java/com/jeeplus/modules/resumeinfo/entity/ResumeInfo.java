/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resumeinfo.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 简历管理Entity
 * @author chentao
 * @version 2019-04-09
 */
public class ResumeInfo extends DataEntity<ResumeInfo> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 简历编号
	private String name;		// 姓名
	private String sex;		// 性别
	private String mobile;		// 手机号
	private String email; // 邮箱
	private String post;		// 应聘岗位
	private String dept;		// 应聘部门
	private String viewer;		// 面试官
	private String interviewTime;		// 面试时间
	private String remark;		// 面试记录
	private String interviewResult;		// 面试结果
	private String status;		// 简历状态
	private String content;		// 简历内容
	private Integer workYears; // 工作年限
	private String birth; // 出生年月
	private String residence; // 现居地址
	private String eduBack; // 学历
	private String domicile; // 户籍
	private String selfEval; // 自我评价
	private JobIntension jobIntension; // 求职意向
	private List<WorkExp> workExps; // 工作经历
	private List<ProjectExp> projectExps; // 项目经验
	private List<EduBackground> eduBackgrounds; // 教育背景
	
	public ResumeInfo() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ResumeInfo(String id){
		super(id);
	}

	@ExcelField(title="简历编号", align=2, sort=1)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@ExcelField(title="姓名", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="性别", align=2, sort=3)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@ExcelField(title="手机号", align=2, sort=4)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ExcelField(title="应聘岗位", align=2, sort=5)
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@ExcelField(title="应聘部门", align=2, sort=6)
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	@ExcelField(title="面试官", align=2, sort=7)
	public String getViewer() {
		return viewer;
	}

	public void setViewer(String viewer) {
		this.viewer = viewer;
	}
	
	@ExcelField(title="面试时间", align=2, sort=8)
	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}
	
	@ExcelField(title="面试记录", align=2, sort=9)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="面试结果", align=2, sort=10)
	public String getInterviewResult() {
		return interviewResult;
	}

	public void setInterviewResult(String interviewResult) {
		this.interviewResult = interviewResult;
	}
	
	@ExcelField(title="简历状态", align=2, sort=11)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="简历内容", align=2, sort=12)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getWorkYears() {
		return workYears;
	}

	public void setWorkYears(Integer workYears) {
		this.workYears = workYears;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getEduBack() {
		return eduBack;
	}

	public void setEduBack(String eduBack) {
		this.eduBack = eduBack;
	}

	public String getDomicile() {
		return domicile;
	}

	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}

	public String getSelfEval() {
		return selfEval;
	}

	public void setSelfEval(String selfEval) {
		this.selfEval = selfEval;
	}

	public JobIntension getJobIntension() {
		return jobIntension;
	}

	public void setJobIntension(JobIntension jobIntension) {
		this.jobIntension = jobIntension;
	}

	public List<WorkExp> getWorkExps() {
		return workExps;
	}

	public void setWorkExps(List<WorkExp> workExps) {
		this.workExps = workExps;
	}

	public List<ProjectExp> getProjectExps() {
		return projectExps;
	}

	public void setProjectExps(List<ProjectExp> projectExps) {
		this.projectExps = projectExps;
	}

	public List<EduBackground> getEduBackgrounds() {
		return eduBackgrounds;
	}

	public void setEduBackgrounds(List<EduBackground> eduBackgrounds) {
		this.eduBackgrounds = eduBackgrounds;
	}
}