/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resumeinfo.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.resumeinfo.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 简历管理MAPPER接口
 * @author chentao
 * @version 2019-04-09
 */
@MyBatisMapper
public interface ResumeInfoMapper extends BaseMapper<ResumeInfo> {

    void insertWorkExp(@Param("exps") List<WorkExp> exps, @Param("resumeId") String resumeId);

    void insertProjectExp(@Param("exps") List<ProjectExp> exps, @Param("resumeId") String resumeId);

    void insertEduBackground(@Param("edus") List<EduBackground> edus, @Param("resumeId") String resumeId);

    void insertJobIntension(@Param("job")JobIntension job, @Param("resumeId") String resumeId);

    JobIntension getJobIntension(@Param("resumeId") String resumeId);

    List<WorkExp> getWorpExp(@Param("resumeId") String resumeId);

    List<ProjectExp> getProjectExp(@Param("resumeId") String resumeId);

    List<EduBackground> getEduBack(@Param("resumeId") String resumeId);
}