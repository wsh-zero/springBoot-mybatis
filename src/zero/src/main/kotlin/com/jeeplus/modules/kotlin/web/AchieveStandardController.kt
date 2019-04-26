/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.web

import com.jeeplus.common.json.AjaxJson
import com.jeeplus.common.utils.StringUtils
import com.jeeplus.core.persistence.Page
import com.jeeplus.core.web.BaseController
import com.jeeplus.modules.kotlin.entity.AchieveStandard
import com.jeeplus.modules.kotlin.service.AchieveStandardService
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
 * 绩效评定标准Controller
 * @author zero
 * @version 2019-04-16
 */
@Controller
@RequestMapping(value = ["\${adminPath}/achieve/achieveStandard"])
open class AchieveStandardController : BaseController() {

    @Autowired
    lateinit var achieveStandardService: AchieveStandardService

    @ModelAttribute
    open operator fun get(id: String?): AchieveStandard {
        var entity: AchieveStandard? = null
        if (StringUtils.isNotBlank(id)) {
            entity = achieveStandardService.get(id!!)
        }
        if (entity == null) {
            entity = AchieveStandard()
        }
        return entity
    }

    @ResponseBody
    @RequestMapping(value = ["get/standard/name"])
    open fun getRankDeptByRankId(achieveConfigId: String): List<String> {
        return achieveStandardService.getAchieveStandardNameByAchieveConfigId(achieveConfigId)
    }

    /**
     * 绩效评定标准列表页面
     */
    @RequiresPermissions("achieve:achieveStandard:list")
    @RequestMapping(value = ["list", ""])
    open fun list(achieveStandard: AchieveStandard, model: Model): String {
        model.addAttribute("achieveStandard", achieveStandard)
        return "modules/achieve/achieveStandardList"
    }

    /**
     * 绩效评定标准列表数据
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveStandard:list")
    @RequestMapping(value = ["data"])
    open fun data(achieveStandard: AchieveStandard, request: HttpServletRequest, response: HttpServletResponse, model: Model): Map<String, Any> {
        val page = achieveStandardService.findPage(Page<AchieveStandard>(request, response), achieveStandard)
        return getBootstrapData<Any>(page)
    }

    /**
     * 查看，增加，编辑绩效评定标准表单页面
     */
    @RequiresPermissions(value = ["achieve:achieveStandard:view", "achieve:achieveStandard:add", "achieve:achieveStandard:edit"], logical = Logical.OR)
    @RequestMapping(value = ["form"])
    open fun form(achieveStandard: AchieveStandard, model: Model): String {
        model.addAttribute("achieveStandard", achieveStandard)
        return "modules/achieve/achieveStandardForm"
    }

    /**
     * 保存绩效评定标准
     */
    @ResponseBody
    @RequiresPermissions(value = ["achieve:achieveStandard:add", "achieve:achieveStandard:edit"], logical = Logical.OR)
    @RequestMapping(value = ["save"])
    @Throws(Exception::class)
    open fun save(achieveStandard: AchieveStandard, model: Model): AjaxJson {
        val j = AjaxJson()
        /**
         * 后台hibernate-validation插件校验
         */
        val errMsg = beanValidator(achieveStandard)
        if (StringUtils.isNotBlank(errMsg)) {
            j.isSuccess = false
            j.msg = errMsg
            return j
        }

        val map = HashMap<String, Any>()
        map["id"] = achieveStandard.id!!
        map["name"] = achieveStandard.name!!
        map["achieve_config_id"] = achieveStandard.achieveConfigId!!
        val exitData = achieveStandardService.isExitData(map)
        if (exitData) {
            j.isSuccess = false
            j.msg = "绩效评定标准已经存在"
            return j
        }
        //新增或编辑表单保存
        achieveStandardService.save(achieveStandard)//保存
        j.isSuccess = true
        j.msg = "保存绩效评定标准成功"
        return j
    }

    /**
     * 删除绩效评定标准
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveStandard:del")
    @RequestMapping(value = ["delete"])
    open fun delete(achieveStandard: AchieveStandard): AjaxJson {
        val j = AjaxJson()
        achieveStandardService.delete(achieveStandard)
        j.msg = "删除绩效评定标准成功"
        return j
    }

    /**
     * 批量删除绩效评定标准
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveStandard:del")
    @RequestMapping(value = ["deleteAll"])
    open fun deleteAll(ids: String): AjaxJson {
        val j = AjaxJson()
        val idArray = ids.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (id in idArray) {
            achieveStandardService.delete(achieveStandardService.get(id))
        }
        j.msg = "删除绩效评定标准成功"
        return j
    }

}