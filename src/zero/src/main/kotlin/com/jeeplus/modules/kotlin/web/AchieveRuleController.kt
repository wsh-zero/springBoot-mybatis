/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.web

import com.jeeplus.common.json.AjaxJson
import com.jeeplus.common.utils.StringUtils
import com.jeeplus.core.persistence.Page
import com.jeeplus.core.web.BaseController
import com.jeeplus.modules.kotlin.entity.AchieveRule
import com.jeeplus.modules.kotlin.service.AchieveJudgeService
import com.jeeplus.modules.kotlin.service.AchieveObjService
import com.jeeplus.modules.kotlin.service.AchieveRuleService
import org.apache.shiro.authz.annotation.Logical
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 绩效考核规则Controller
 * @author zero
 * @version 2019-04-22
 */
@Controller
@RequestMapping(value = ["\${adminPath}/achieve/achieveRule"])
open class AchieveRuleController : BaseController() {

    @Autowired
    lateinit var achieveRuleService: AchieveRuleService
    @Autowired
    lateinit var achieveObjService: AchieveObjService
    @Autowired
    lateinit var achieveJudgeService: AchieveJudgeService

    @ModelAttribute
    open operator fun get(id: String?): AchieveRule {
        var entity: AchieveRule? = null
        if (StringUtils.isNotBlank(id)) {
            entity = achieveRuleService.get(id!!)
        }
        if (entity == null) {
            entity = AchieveRule()
        }
        return entity
    }

    /**
     * 绩效考核规则列表页面
     */
    @RequiresPermissions("achieve:achieveRule:list")
    @RequestMapping(value = ["list", ""])
    open fun list(achieveRule: AchieveRule, model: Model): String {
        model.addAttribute("achieveRule", achieveRule)
        return "modules/achieve/achieveRuleList"
    }

    /**
     * 绩效考核规则列表数据
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveRule:list")
    @RequestMapping(value = ["data"])
    open fun data(achieveRule: AchieveRule, request: HttpServletRequest, response: HttpServletResponse, model: Model): Map<String, Any> {
        val page = achieveRuleService.findPage(Page<AchieveRule>(request, response), achieveRule)
        return getBootstrapData<Any>(page)
    }

    /**
     * 查看，增加，编辑绩效考核规则表单页面
     */
    @RequiresPermissions(value = ["achieve:achieveRule:view", "achieve:achieveRule:add", "achieve:achieveRule:edit"], logical = Logical.OR)
    @RequestMapping(value = ["form"])
    open fun form(achieveRule: AchieveRule, model: Model): String {
        model.addAttribute("achieveObj", achieveObjService.getIdAndName())
        model.addAttribute("achieveJudge", achieveJudgeService.getIdAndName())
        model.addAttribute("achieveRule", achieveRule)
        return "modules/achieve/achieveRuleForm"
    }

    /**
     * 保存绩效考核规则
     */
    @ResponseBody
    @RequiresPermissions(value = ["achieve:achieveRule:add", "achieve:achieveRule:edit"], logical = Logical.OR)
    @RequestMapping(value = ["save"])
    @Throws(Exception::class)
    open fun save(achieveRule: AchieveRule, model: Model): AjaxJson {
        val j = AjaxJson()
        /**
         * 后台hibernate-validation插件校验
         */
        val errMsg = beanValidator(achieveRule)
        if (StringUtils.isNotBlank(errMsg)) {
            j.isSuccess = false
            j.msg = errMsg
            return j
        }
        val map = HashMap<String, Any>()
        map["id"] = achieveRule.id!!
        map["name"] = achieveRule.name!!
        if (achieveRuleService.isExitData(map)) {
            j.isSuccess = false
            j.msg = "评分表名称已经存在"
            return j
        }
        map.remove("name")
        map["number"] = achieveRule.number!!
        if (achieveRuleService.isExitData(map)) {
            j.isSuccess = false
            j.msg = "评分表编号已经存在"
            return j
        }
        //新增或编辑表单保存
        achieveRuleService.save(achieveRule)//保存
        j.isSuccess = true
        j.msg = "保存绩效考核规则成功"
        return j
    }

    /**
     * 删除绩效考核规则
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveRule:del")
    @RequestMapping(value = ["delete"])
    open fun delete(achieveRule: AchieveRule): AjaxJson {
        val j = AjaxJson()
        achieveRuleService.delete(achieveRule)
        j.msg = "删除绩效考核规则成功"
        return j
    }

    /**
     * 批量删除绩效考核规则
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveRule:del")
    @RequestMapping(value = ["deleteAll"])
    open fun deleteAll(ids: String): AjaxJson {
        val j = AjaxJson()
        val idArray = ids.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (id in idArray) {
            achieveRuleService.delete(achieveRuleService.get(id))
        }
        j.msg = "删除绩效考核规则成功"
        return j
    }
}