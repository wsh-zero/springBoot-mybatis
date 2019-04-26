/**
 * Copyright  2015-2020 [JeePlus](http://www.jeeplus.org/) All rights reserved.
 */
package com.jeeplus.modules.kotlin.web

import com.jeeplus.common.json.AjaxJson
import com.jeeplus.common.utils.IdGen
import com.jeeplus.common.utils.StringUtils
import com.jeeplus.core.persistence.Page
import com.jeeplus.core.web.BaseController
import com.jeeplus.modules.kotlin.entity.AchieveJudge
import com.jeeplus.modules.kotlin.service.AchieveConfigService
import com.jeeplus.modules.kotlin.service.AchieveJudgeService
import com.jeeplus.modules.kotlin.utils.Const
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
 * 绩效评定表配置Controller
 * @author zero
 * @version 2019-04-18
 */
@Controller
@RequestMapping(value = ["\${adminPath}/achieve/achieveJudge"])
open class AchieveJudgeController : BaseController() {

    @Autowired
    lateinit var achieveJudgeService: AchieveJudgeService
    @Autowired
    lateinit var achieveConfigService: AchieveConfigService

    @ModelAttribute
    open operator fun get(id: String?): AchieveJudge {
        var entity: AchieveJudge? = null
        if (StringUtils.isNotBlank(id)) {
            entity = achieveJudgeService.get(id!!)
        }
        if (entity == null) {
            entity = AchieveJudge()
        }
        return entity
    }

    /**
     * 绩效评定表配置列表页面
     */
    @RequiresPermissions("achieve:achieveJudge:list")
    @RequestMapping(value = ["list", ""])
    open fun list(achieveJudge: AchieveJudge, model: Model): String {
        model.addAttribute("achieveJudge", achieveJudge)
        return "modules/achieve/achieveJudgeList"
    }

    /**
     * 绩效评定表配置列表数据
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveJudge:list")
    @RequestMapping(value = ["data"])
    open fun data(achieveJudge: AchieveJudge, request: HttpServletRequest, response: HttpServletResponse, model: Model): Map<String, Any> {
        val page = achieveJudgeService.findPage(Page<AchieveJudge>(request, response), achieveJudge)
        return getBootstrapData<Any>(page)
    }

    /**
     * 查看，增加，编辑绩效评定表配置表单页面
     */
    @RequiresPermissions(value = ["achieve:achieveJudge:view", "achieve:achieveJudge:add", "achieve:achieveJudge:edit"], logical = Logical.OR)
    @RequestMapping(value = ["form"])
    open fun form(achieveJudge: AchieveJudge, model: Model): String {
        //新增时生成临时id
        if (!StringUtils.isNotBlank(achieveJudge.id)) {
            achieveJudge.temporaryId = IdGen.uuid()
        }
        model.addAttribute("achieveConfig", achieveConfigService.getIdAndName())
        model.addAttribute("achieveJudge", achieveJudge)
        return "modules/achieve/achieveJudgeForm"
    }

    /**
     * 保存绩效评定表配置
     */
    @ResponseBody
    @RequiresPermissions(value = ["achieve:achieveJudge:add", "achieve:achieveJudge:edit"], logical = Logical.OR)
    @RequestMapping(value = ["save"])
    @Throws(Exception::class)
    open fun save(achieveJudge: AchieveJudge, model: Model): AjaxJson {
        val j = AjaxJson()
        /**
         * 后台hibernate-validation插件校验
         */
        val errMsg = beanValidator(achieveJudge)
        if (StringUtils.isNotBlank(errMsg)) {
            j.isSuccess = false
            j.msg = errMsg
            return j
        }
        val map = HashMap<String, Any>()
        map["id"] = achieveJudge.id!!
        map["name"] = achieveJudge.name!!
        val exitData = achieveJudgeService.isExitData(map)
        if (exitData) {
            j.isSuccess = false
            j.msg = "分组名称已经存在"
            return j
        }
        //新增或编辑表单保存
        achieveJudgeService.save(achieveJudge)//保存
        j.isSuccess = true
        j.msg = "保存绩效评定表配置成功"
        return j
    }

    /**
     * 删除绩效评定表配置
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveJudge:del")
    @RequestMapping(value = ["delete"])
    open fun delete(achieveJudge: AchieveJudge): AjaxJson {
        val j = AjaxJson()
        achieveJudgeService.delete(achieveJudge)
        j.msg = "删除绩效评定表配置成功"
        return j
    }

    /**
     * 批量删除绩效评定表配置
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveJudge:del")
    @RequestMapping(value = ["deleteAll"])
    open fun deleteAll(ids: String): AjaxJson {
        val j = AjaxJson()
        val idArray = ids.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (idArray.contains(Const.AchieveJudge.TYPE_ZERO)) {
            val get = achieveJudgeService.get(Const.AchieveJudge.TYPE_ZERO)
            j.isSuccess = false
            j.msg = get.name + "不能删除"
            return j
        }
        for (id in idArray) {
            achieveJudgeService.delete(achieveJudgeService.get(id))
        }
        j.msg = "删除绩效评定表配置成功"
        return j
    }

}