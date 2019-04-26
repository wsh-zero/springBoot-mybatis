/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.web

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.jeeplus.common.json.AjaxJson
import com.jeeplus.common.utils.StringUtils
import com.jeeplus.core.web.BaseController
import com.jeeplus.modules.kotlin.entity.AchieveRuleDetails
import com.jeeplus.modules.kotlin.service.AchieveRuleDetailsService
import com.jeeplus.modules.kotlin.vo.AchieveJudgeDetails2VO
import org.apache.shiro.authz.annotation.Logical
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 绩效考核规则详情Controller
 * @author zero
 * @version 2019-04-26
 */
@Controller
@RequestMapping(value = ["\${adminPath}/achieve/achieveRuleDetails"])
open class AchieveRuleDetailsController : BaseController() {

    @Autowired
    lateinit var achieveRuleDetailsService: AchieveRuleDetailsService

    @ModelAttribute
    open operator fun get(id: String?): AchieveRuleDetails {
        var entity: AchieveRuleDetails? = null
        if (StringUtils.isNotBlank(id)) {
            entity = achieveRuleDetailsService.get(id!!)
        }
        if (entity == null) {
            entity = AchieveRuleDetails()
        }
        return entity
    }

    /**
     * 绩效考核规则详情列表页面
     */
    @RequiresPermissions("achieve:achieveRuleDetails:list")
    @RequestMapping(value = ["list", ""])
    open fun list(achieveRuleDetails: AchieveRuleDetails, request: HttpServletRequest, response: HttpServletResponse, model: Model): String {

        model.addAttribute("achieveRuleDetails", achieveRuleDetails)
        return "modules/achieve/achieveRuleDetailsList"
    }

    /**
     * 查看，增加，编辑绩效考核规则详情表单页面
     */
    @RequiresPermissions(value = ["achieve:achieveRuleDetails:view", "achieve:achieveRuleDetails:add", "achieve:achieveRuleDetails:edit"], logical = Logical.OR)
    @RequestMapping(value = ["form"])
    open fun form(achieveRuleDetails: AchieveRuleDetails, model: Model): String {
        if (achieveRuleDetails.parent != null && StringUtils.isNotBlank(achieveRuleDetails.parent!!.id)) {
            achieveRuleDetails.parent = achieveRuleDetailsService.get(achieveRuleDetails.parent!!.id)
            // 获取排序号，最末节点排序号+30
            if (StringUtils.isBlank(achieveRuleDetails.id)) {
                val achieveJudgeDetailsChild = AchieveRuleDetails()
                achieveJudgeDetailsChild.parent = AchieveRuleDetails(achieveRuleDetails.parent!!.id)
                val list = achieveRuleDetailsService.findList(achieveRuleDetails)
                if (list.isNotEmpty()) {
                    achieveRuleDetails.sort = list[list.size - 1].sort
                    if (achieveRuleDetails.sort != null) {
                        achieveRuleDetails.sort = achieveRuleDetails.sort + 30
                    }
                }
            }
        }
        if (achieveRuleDetails.sort == null) {
            achieveRuleDetails.sort = 30
        }
        model.addAttribute("achieveRuleDetails", achieveRuleDetails)
        return "modules/achieve/achieveRuleDetailsForm"
    }

    /**
     * 查看，增加，编辑绩效考核规则详情表单页面
     */
    @RequiresPermissions(value = ["achieve:achieveRuleDetails:view", "achieve:achieveRuleDetails:add", "achieve:achieveRuleDetails:edit"], logical = Logical.OR)
    @RequestMapping(value = ["form1"])
    open fun form1(achieveRuleDetails: AchieveRuleDetails, model: Model): String {
        if (achieveRuleDetails.parent != null && StringUtils.isNotBlank(achieveRuleDetails.parent!!.id)) {
            achieveRuleDetails.parent = achieveRuleDetailsService.get(achieveRuleDetails.parent!!.id)
            // 获取排序号，最末节点排序号+30
            if (StringUtils.isBlank(achieveRuleDetails.id)) {
                val achieveJudgeDetailsChild = AchieveRuleDetails()
                achieveJudgeDetailsChild.parent = AchieveRuleDetails(achieveRuleDetails.parent!!.id)
                val list = achieveRuleDetailsService.findList(achieveRuleDetails)
                if (list.isNotEmpty()) {
                    achieveRuleDetails.sort = list[list.size - 1].sort
                    if (achieveRuleDetails.sort != null) {
                        achieveRuleDetails.sort = achieveRuleDetails.sort + 30
                    }
                }
            }
        }
        if (achieveRuleDetails.sort == null) {
            achieveRuleDetails.sort = 30
        }
        model.addAttribute("achieveRuleDetails", achieveRuleDetails)
        return "modules/achieve/achieveRuleDetailsForm1"
    }
    /**
     * 查看，增加，编辑绩效考核规则详情表单页面
     */
    @RequiresPermissions(value = ["achieve:achieveRuleDetails:view", "achieve:achieveRuleDetails:add", "achieve:achieveRuleDetails:edit"], logical = Logical.OR)
    @RequestMapping(value = ["form2"])
    open fun form2(achieveRuleDetails: AchieveRuleDetails, model: Model): String {
        if (achieveRuleDetails.parent != null && StringUtils.isNotBlank(achieveRuleDetails.parent!!.id)) {
            achieveRuleDetails.parent = achieveRuleDetailsService.get(achieveRuleDetails.parent!!.id)
            // 获取排序号，最末节点排序号+30
            if (StringUtils.isBlank(achieveRuleDetails.id)) {
                val achieveJudgeDetailsChild = AchieveRuleDetails()
                achieveJudgeDetailsChild.parent = AchieveRuleDetails(achieveRuleDetails.parent!!.id)
                val list = achieveRuleDetailsService.findList(achieveRuleDetails)
                if (list.isNotEmpty()) {
                    achieveRuleDetails.sort = list[list.size - 1].sort
                    if (achieveRuleDetails.sort != null) {
                        achieveRuleDetails.sort = achieveRuleDetails.sort + 30
                    }
                }
            }
        }
        if (achieveRuleDetails.sort == null) {
            achieveRuleDetails.sort = 30
        }
        model.addAttribute("achieveRuleDetails", achieveRuleDetails)
        return "modules/achieve/achieveRuleDetailsForm2"
    }

    /**
     * 保存绩效考核规则详情
     */
    @ResponseBody
    @RequiresPermissions(value = ["achieve:achieveRuleDetails:add", "achieve:achieveRuleDetails:edit"], logical = Logical.OR)
    @RequestMapping(value = ["save"])
    @Throws(Exception::class)
    open fun save(achieveRuleDetails: AchieveRuleDetails, model: Model): AjaxJson {
        val j = AjaxJson()
        /**
         * 后台hibernate-validation插件校验
         */
        val errMsg = beanValidator(achieveRuleDetails)
        if (StringUtils.isNotBlank(errMsg)) {
            j.isSuccess = false
            j.msg = errMsg
            return j
        }

        //新增或编辑表单保存
        achieveRuleDetailsService.save(achieveRuleDetails)//保存
        j.isSuccess = true
        j.put("achieveRuleDetails", achieveRuleDetails)
        j.msg = "保存绩效考核规则详情成功"
        return j
    }

    @ResponseBody
    @RequestMapping(value = ["getChildren"])
    open fun getChildren(parentId: String): List<AchieveRuleDetails> {
        val parentIdStr = if ("-1" == parentId) "0" else parentId
        return achieveRuleDetailsService.getChildren(parentIdStr)
    }


    @ResponseBody
    @RequestMapping(value = ["getChildren2"])
    open fun getChildren2(parentId: String, achieveRuleId: String): List<AchieveJudgeDetails2VO<AchieveRuleDetails>> {
        val parentIdStr = if ("-1" == parentId) "0" else parentId
        return achieveRuleDetailsService.getChildren2(parentIdStr,achieveRuleId)
    }

    /**
     * 删除绩效考核规则详情
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveRuleDetails:del")
    @RequestMapping(value = ["delete"])
    open fun delete(achieveRuleDetails: AchieveRuleDetails): AjaxJson {
        val j = AjaxJson()
        achieveRuleDetailsService.delete(achieveRuleDetails)
        j.isSuccess = true
        j.msg = "删除绩效考核规则详情成功"
        return j
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = ["treeData"])
    open fun treeData(@RequestParam(required = false) extId: String?, response: HttpServletResponse): List<Map<String, Any>> {
        val mapList = Lists.newArrayList<Map<String, Any>>()
        val list = achieveRuleDetailsService.findList(AchieveRuleDetails())
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