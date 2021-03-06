/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.mapper

import com.jeeplus.core.persistence.BaseMapper
import com.jeeplus.core.persistence.annotation.MyBatisMapper
import com.jeeplus.modules.kotlin.dto.MapDto
import com.jeeplus.modules.kotlin.entity.AchieveObj
import org.apache.ibatis.annotations.Param

/**
 * 绩效对象配置MAPPER接口
 * @author zero
 * @version 2019-04-17
 */
@MyBatisMapper
interface AchieveObjMapper : BaseMapper<AchieveObj> {
    fun getDeptNameByAchieveObjId(id: String): List<MapDto>
    fun deleteAchieveObjDeptByObjId(@Param("objId") objId: String): Int
    fun saveAchieveObjDept(@Param("objId") objId: String, @Param("deptId") deptId: String): Int
    fun getIdAndName(): List<MapDto>
}