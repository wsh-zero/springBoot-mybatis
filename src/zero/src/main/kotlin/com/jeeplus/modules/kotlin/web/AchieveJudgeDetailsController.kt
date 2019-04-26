/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.web

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.jeeplus.common.json.AjaxJson
import com.jeeplus.common.utils.StringUtils
import com.jeeplus.core.web.BaseController
import com.jeeplus.modules.kotlin.dto.MapDto
import com.jeeplus.modules.kotlin.entity.AchieveJudgeDetails
import com.jeeplus.modules.kotlin.service.AchieveJudgeDetailsService
import org.apache.shiro.authz.annotation.Logical
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 绩效评定详情Controller
 * @author zero
 * @version 2019-04-19
 */
@Controller
@RequestMapping(value = ["\${adminPath}/achieve/achieveJudgeDetails"])
open class AchieveJudgeDetailsController : BaseController() {

    @Autowired
    lateinit var achieveJudgeDetailsService: AchieveJudgeDetailsService

    @ModelAttribute
    open operator fun get(id: String?): AchieveJudgeDetails {
        var entity: AchieveJudgeDetails? = null
        if (StringUtils.isNotBlank(id)) {
            entity = achieveJudgeDetailsService.get(id!!)
        }
        if (entity == null) {
            entity = AchieveJudgeDetails()
        }
        return entity
    }

    /**
     * 绩效评定详情列表页面
     */
    @RequiresPermissions("achieve:achieveJudgeDetails:list")
    @RequestMapping(value = ["list", ""])
    open fun list(achieveJudgeDetails: AchieveJudgeDetails, request: HttpServletRequest, response: HttpServletResponse, model: Model): String {

        model.addAttribute("achieveJudgeDetails", achieveJudgeDetails)
        return "modules/achieve/achieveJudgeDetailsList"
    }

    @ResponseBody
    @RequestMapping(value = ["get/judge/details"])
    open fun getIdAndNameByAchieveJudgeId(achieveJudgeId: String): List<MapDto> {
        return achieveJudgeDetailsService.getIdAndNameByAchieveJudgeId(achieveJudgeId)
    }


    /**
     * 查看，增加，编辑绩效评定详情表单页面
     */
    @RequiresPermissions(value = ["achieve:achieveJudgeDetails:view", "achieve:achieveJudgeDetails:add", "achieve:achieveJudgeDetails:edit"], logical = Logical.OR)
    @RequestMapping(value = ["form"])
    open fun form(achieveJudgeDetails: AchieveJudgeDetails, model: Model): String {
        if (achieveJudgeDetails.parent != null && StringUtils.isNotBlank(achieveJudgeDetails.parent!!.id)) {
            achieveJudgeDetails.parent = achieveJudgeDetailsService.get(achieveJudgeDetails.parent!!.id)
            // 获取排序号，最末节点排序号+30
            if (StringUtils.isBlank(achieveJudgeDetails.id)) {
                val achieveJudgeDetailsChild = AchieveJudgeDetails()
                achieveJudgeDetailsChild.parent = AchieveJudgeDetails(achieveJudgeDetails.parent!!.id)
                val list = achieveJudgeDetailsService.findList(achieveJudgeDetails)
                if (list.isNotEmpty()) {
                    achieveJudgeDetails.sort = list[list.size - 1].sort
                    if (achieveJudgeDetails.sort != null) {
                        achieveJudgeDetails.sort = achieveJudgeDetails.sort + 30
                    }
                }
            }
        }
        if (achieveJudgeDetails.sort == null) {
            achieveJudgeDetails.sort = 30
        }
        model.addAttribute("achieveJudgeDetails", achieveJudgeDetails)
        return "modules/achieve/achieveJudgeDetailsForm"
    }

    /**
     * 保存绩效评定详情
     */
    @ResponseBody
    @RequiresPermissions(value = ["achieve:achieveJudgeDetails:add", "achieve:achieveJudgeDetails:edit"], logical = Logical.OR)
    @RequestMapping(value = ["save"])
    @Throws(Exception::class)
    open fun save(achieveJudgeDetails: AchieveJudgeDetails, model: Model): AjaxJson {
        val j = AjaxJson()
        /**
         * 后台hibernate-validation插件校验
         */
        val errMsg = beanValidator(achieveJudgeDetails)
        if (StringUtils.isNotBlank(errMsg)) {
            j.isSuccess = false
            j.msg = errMsg
            return j
        }

        //新增或编辑表单保存

        if (!StringUtils.isNotBlank(achieveJudgeDetails.achieveJudgeId)) {
            achieveJudgeDetails.achieveJudgeId = achieveJudgeDetails.temporaryId
        }
        val save2 = achieveJudgeDetailsService.save2(achieveJudgeDetails)//保存
        if (null != save2) return save2
        j.isSuccess = true
        j.put("achieveJudgeDetails", achieveJudgeDetails)
        j.msg = "保存绩效评定详情成功"
        return j
    }

    @ResponseBody
    @RequestMapping(value = ["getChildren"])
    open fun getChildren(parentId: String, achieveJudgeId: String?): List<AchieveJudgeDetails> {
        val result: MutableList<AchieveJudgeDetails> = mutableListOf()
        if (StringUtils.isNotBlank(achieveJudgeId)) {
            val parentIdStr = if ("-1" == parentId) "0" else parentId
            val children2 = achieveJudgeDetailsService.getChildren2(parentIdStr, achieveJudgeId)
            children2?.let { result.addAll(it) }
        }
        return result
    }

    /**
     * 删除绩效评定详情
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveJudgeDetails:del")
    @RequestMapping(value = ["delete"])
    open fun delete(achieveJudgeDetails: AchieveJudgeDetails): AjaxJson {
        val j = AjaxJson()
        achieveJudgeDetailsService.delete(achieveJudgeDetails)
        j.isSuccess = true
        j.msg = "删除绩效评定详情成功"
        return j
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = ["treeData"])
    open fun treeData(extId: String?, response: HttpServletResponse, achieveJudgeDetails: AchieveJudgeDetails): List<Map<String, Any>> {
        val mapList = Lists.newArrayList<Map<String, Any>>()
        if (!StringUtils.isNotBlank(achieveJudgeDetails.achieveJudgeId)) {
            achieveJudgeDetails.achieveJudgeId = achieveJudgeDetails.temporaryId
        }
        val list = achieveJudgeDetailsService.findList(achieveJudgeDetails)
        for (i in list.indices) {
            val e = list[i]
            if (StringUtils.isBlank(extId) || extId != null && extId != e.id && e.parentIds.indexOf(",$extId,") == -1) {
                val map = Maps.newHashMap<String, Any>()
                map["id"] = e.id
                map["text"] = e.name
                if (StringUtils.isBlank(e.parentId) || "0" == e.parentId) {
                    map["parent"] = "#"
                    val state = Maps.newHashMap<String, Any>()
                    state["opened"] = true
                    map["state"] = state
                } else {
                    map["parent"] = e.parentId
                }
                mapList.add(map)
            }
        }
        return mapList
    }

}