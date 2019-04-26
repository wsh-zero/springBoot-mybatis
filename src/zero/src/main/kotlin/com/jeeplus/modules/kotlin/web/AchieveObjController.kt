package com.jeeplus.modules.kotlin.web

import com.google.common.base.Strings
import com.google.common.collect.Lists
import com.jeeplus.common.json.AjaxJson
import com.jeeplus.common.utils.StringUtils
import com.jeeplus.core.persistence.Page
import com.jeeplus.core.web.BaseController
import com.jeeplus.modules.kotlin.dto.MapDto
import com.jeeplus.modules.kotlin.entity.AchieveObj
import com.jeeplus.modules.kotlin.service.AchieveObjService
import com.jeeplus.modules.personnel.plan.service.RankService
import com.jeeplus.modules.sys.service.OfficeService
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
 * 绩效对象配置Controller
 * @author zero
 * @version 2019-04-17
 */
@Controller
@RequestMapping(value = ["\${adminPath}/achieve/achieveObj"])
open class AchieveObjController : BaseController() {

    @Autowired
    lateinit var achieveObjService: AchieveObjService
    @Autowired
    lateinit var officeService: OfficeService
    @Autowired
    lateinit var rankService: RankService

    @ModelAttribute
    open operator fun get(id: String?): AchieveObj {
        var entity: AchieveObj? = null
        if (StringUtils.isNotBlank(id)) {
            entity = achieveObjService.get(id!!)
        }
        if (entity == null) {
            entity = AchieveObj()
        }
        return entity
    }

    /**
     * 绩效对象配置列表页面
     */
    @RequiresPermissions("achieve:achieveObj:list")
    @RequestMapping(value = ["list", ""])
    open fun list(achieveObj: AchieveObj, model: Model): String {
        model.addAttribute("achieveObj", achieveObj)
        return "modules/achieve/achieveObjList"
    }

    /**
     * 绩效对象配置列表数据
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveObj:list")
    @RequestMapping(value = ["data"])
    open fun data(achieveObj: AchieveObj, request: HttpServletRequest, response: HttpServletResponse, model: Model): Map<String, Any> {
        val page = achieveObjService.findPage(Page<AchieveObj>(request, response), achieveObj)
        return getBootstrapData<Any>(page)
    }

    /**
     * 查看，增加，编辑绩效对象配置表单页面
     */
    @RequiresPermissions(value = ["achieve:achieveObj:view", "achieve:achieveObj:add", "achieve:achieveObj:edit"], logical = Logical.OR)
    @RequestMapping(value = ["form"])
    open fun form(achieveObj: AchieveObj, model: Model): String {
        var dept: List<MapDto> = Lists.newArrayList<MapDto>()
        if (!Strings.isNullOrEmpty(achieveObj.rankId)) {
            dept = officeService.getRankDeptByRankId(achieveObj.rankId)
        }
        model.addAttribute("dept", dept)
        model.addAttribute("rank", rankService.idAndName)
        model.addAttribute("achieveObj", achieveObj)
        return "modules/achieve/achieveObjForm"
    }

    /**
     * 保存绩效对象配置
     */
    @ResponseBody
    @RequiresPermissions(value = ["achieve:achieveObj:add", "achieve:achieveObj:edit"], logical = Logical.OR)
    @RequestMapping(value = ["save"])
    @Throws(Exception::class)
    open fun save(achieveObj: AchieveObj, model: Model): AjaxJson {
        val j = AjaxJson()
        /**
         * 后台hibernate-validation插件校验
         */
        val errMsg = beanValidator(achieveObj)
        if (StringUtils.isNotBlank(errMsg)) {
            j.isSuccess = false
            j.msg = errMsg
            return j
        }

        val map = HashMap<String, Any>()
        map["id"] = achieveObj.id!!
        map["name"] = achieveObj.name!!
        val exitData = achieveObjService.isExitData(map)
        if (exitData) {
            j.isSuccess = false
            j.msg = "分类名称已经存在"
            return j
        }
        //新增或编辑表单保存
        achieveObjService.save(achieveObj)//保存
        j.isSuccess = true
        j.msg = "保存绩效对象配置成功"
        return j
    }

    /**
     * 删除绩效对象配置
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveObj:del")
    @RequestMapping(value = ["delete"])
    open fun delete(achieveObj: AchieveObj): AjaxJson {
        val j = AjaxJson()
        achieveObjService.delete(achieveObj)
        j.msg = "删除绩效对象配置成功"
        return j
    }

    /**
     * 批量删除绩效对象配置
     */
    @ResponseBody
    @RequiresPermissions("achieve:achieveObj:del")
    @RequestMapping(value = ["deleteAll"])
    open fun deleteAll(ids: String): AjaxJson {
        val j = AjaxJson()
        val idArray = ids.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (id in idArray) {
            achieveObjService.delete(achieveObjService.get(id))
        }
        j.msg = "删除绩效对象配置成功"
        return j
    }

}