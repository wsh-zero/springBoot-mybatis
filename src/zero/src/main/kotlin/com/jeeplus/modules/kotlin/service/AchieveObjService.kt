/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.service

import com.google.common.collect.Lists
import com.jeeplus.core.persistence.Page
import com.jeeplus.core.service.CrudService
import com.jeeplus.modules.kotlin.dto.MapDto
import com.jeeplus.modules.kotlin.entity.AchieveObj
import com.jeeplus.modules.kotlin.mapper.AchieveObjMapper
import com.jeeplus.modules.personnel.plan.service.RankService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 绩效对象配置Service
 * @author zero
 * @version 2019-04-17
 */
@Service
@Transactional(readOnly = true)
open class AchieveObjService : CrudService<AchieveObjMapper, AchieveObj>() {

    @Autowired
    lateinit var rankService: RankService
    @Autowired
    lateinit var achieveObjMapper: AchieveObjMapper

    override fun get(id: String): AchieveObj {
        val entity = super.get(id)
        val deptIdList = Lists.newArrayList<String>()
        val deptList = achieveObjMapper.getDeptNameByAchieveObjId(entity.id)
        for (dept in deptList) {
            deptIdList.add(dept.key)
        }
        //获取拥有的部门和部门id集合,用于页面回显使用
        entity.dept = deptList
        entity.deptIdList = deptIdList
        return entity
    }

    override fun findList(achieveObj: AchieveObj): List<AchieveObj> {
        return super.findList(achieveObj)
    }

    open fun getIdAndName(): List<MapDto> {
        return achieveObjMapper.getIdAndName()
    }

    override fun findPage(page: Page<AchieveObj>, achieveObj: AchieveObj): Page<AchieveObj> {
        val findPage = super.findPage(page, achieveObj)
        findPage.list.map {
            it.rankName = rankService.get(it.rankId).rankName
            it.dept = achieveObjMapper.getDeptNameByAchieveObjId(it.id)
        }
        return findPage
    }

    @Transactional(readOnly = false)
    override fun save(achieveObj: AchieveObj) {
        super.save(achieveObj)
        //处理中间表数据
        achieveObjMapper.deleteAchieveObjDeptByObjId(achieveObj.id)
        achieveObj.deptIdList?.let{
            for (deptId in it) {
                achieveObjMapper.saveAchieveObjDept(achieveObj.id, deptId)
            }
        }

    }

    @Transactional(readOnly = false)
    override fun delete(achieveObj: AchieveObj) {
        super.delete(achieveObj)
    }

}