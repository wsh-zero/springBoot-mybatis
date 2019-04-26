/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.web

import com.jeeplus.common.json.AjaxJson
import com.jeeplus.common.utils.StringUtils
import com.jeeplus.core.persistence.Page
import com.jeeplus.core.web.BaseController
import com.jeeplus.modules.kotlin.entity.AchieveConfig
import com.jeeplus.modules.kotlin.service.AchieveConfigService
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
import kotlin.collections.HashMap as HashMap1

/**
 * 绩效配置Controller
 * @author zero
 * @version 2019-04-16
 */
@Controller
@RequestMapping(value = ["\${adminPath}/achieve/achieveConfig"])
open class AchieveConfigController : BaseController() {

    @Autowired
    lateinit var achieveConfigService: AchieveConfigService
    @Autowired
    lateinit var achieveStandardService: AchieveStandardService

    @ModelAttribute
    open operator fun get(id: String?): AchieveConfig {
        var entity: AchieveConfig? = null
        if (StringUtils.isNotBlank(id)) {
            entity = achieveConfigService.get(id!!)
        }
        if (entity == null) {
            entity = AchieveConfig()
        }
        return entity
    }

    /**
     * 绩效配置列表页面
     */
    @RequiresPermissions("achieve:achieveConfig:list")
    @RequestMapping(value = ["list", ""])
    open fun list(achieveConfig: AchieveConfig, model: Model): String {
        model.addAttribute("achieveConfig", achieveConfig)
        return "modules/achieve/achieveConfigList"
    }

    /**
     * 绩效配置列表数据
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveConfig:list")
    @RequestMapping(value = ["data"])
    open fun data(achieveConfig: AchieveConfig, request: HttpServletRequest, response: HttpServletResponse, model: Model): Map<String, Any> {
        val page = achieveConfigService.findPage(Page(request, response), achieveConfig)
        return getBootstrapData<Any>(page)
    }

    /**
     * 查看，增加，编辑绩效配置表单页面
     */
    @RequiresPermissions(value = ["achieve:achieveConfig:view", "achieve:achieveConfig:add", "achieve:achieveConfig:edit"], logical = Logical.OR)
    @RequestMapping(value = ["form"])
    open fun form(achieveConfig: AchieveConfig, model: Model): String {
        model.addAttribute("achieveConfig", achieveConfig)
        return "modules/achieve/achieveConfigForm"
    }

    /**
     * 保存绩效配置
     */
    @ResponseBody
    @RequiresPermissions(value = ["achieve:achieveConfig:add", "achieve:achieveConfig:edit"], logical = Logical.OR)
    @RequestMapping(value = ["save"])
    @Throws(Exception::class)
    open fun save(achieveConfig: AchieveConfig, model: Model): AjaxJson {
        val j = AjaxJson()
        /**
         * 后台hibernate-validation插件校验
         */
        val errMsg = beanValidator(achieveConfig)
        if (StringUtils.isNotBlank(errMsg)) {
            j.isSuccess = false
            j.msg = errMsg
            return j
        }
        val map = HashMap<String, Any>()
        map["id"] = achieveConfig.id!!
        map["name"] = achieveConfig.name!!
        val exitData = achieveConfigService.isExitData(map)
        if (exitData) {
            j.isSuccess = false
            j.msg = "考核标准已经存在"
            return j
        }
        //新增或编辑表单保存
        achieveConfigService.save(achieveConfig)//保存
        j.isSuccess = true
        j.msg = "保存绩效配置成功"
        return j
    }

    /**
     * 删除绩效配置
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveConfig:del")
    @RequestMapping(value = ["delete"])
    open fun delete(achieveConfig: AchieveConfig): AjaxJson {
        val j = AjaxJson()
        achieveConfigService.delete(achieveConfig)
        j.msg = "删除绩效配置成功"
        return j
    }

    /**
     * 批量删除绩效配置
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveConfig:del")
    @RequestMapping(value = ["deleteAll"])
    open fun deleteAll(ids: String): AjaxJson {
        val j = AjaxJson()
        val idArray = ids.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (id in idArray) {
            achieveConfigService.delete(achieveConfigService.get(id))
            achieveStandardService.deleteByForeignKey(id)
        }
        j.msg = "删除绩效配置成功"
        return j
    }

}