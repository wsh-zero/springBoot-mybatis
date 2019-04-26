package com.jeeplus.modules.personnel.manager.web;//package com.jeeplus.modules.personnel.manager.web;
//
//import com.google.common.collect.Lists;
//import com.jeeplus.common.json.AjaxJson;
//import com.jeeplus.common.utils.DateUtils;
//import com.jeeplus.common.utils.SpringContextHolder;
//import com.jeeplus.common.utils.StringUtils;
//import com.jeeplus.common.utils.excel.ExportExcel;
//import com.jeeplus.common.utils.excel.ImportExcel;
//import com.jeeplus.core.persistence.Page;
//import com.jeeplus.core.web.BaseController;
//import com.jeeplus.modules.personnel.manager.entity.Staff;
//import com.jeeplus.modules.personnel.manager.service.StaffService;
//import com.jeeplus.modules.personnel.plan.entity.Org;
//import com.jeeplus.modules.personnel.plan.mapper.OrgMapper;
//import com.jeeplus.modules.personnel.vo.StaffVo;
//import com.jeeplus.modules.sys.utils.UserUtils;
//import org.apache.shiro.authz.annotation.Logical;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.ConstraintViolationException;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by DRYJKUIL on 2019/2/21.
// */
//@Controller
//@RequestMapping(value = "${adminPath}/personnel/manager/contractInfo")
//public class ContractInfoController extends BaseController{  @Autowired
//        private StaffService staffService;
//
//        @Autowired
//        private OrgMapper orgMapper;
//
//        @ModelAttribute
//        public Staff get(@RequestParam(required=false) String id) {
//            Staff entity = null;
//            if (StringUtils.isNotBlank(id)){
//                entity = staffService.get(id);
//            }
//            if (entity == null){
//                entity = new Staff();
//            }
//            return entity;
//        }
//
//        /**
//         * 合同信息列表页面
//         */
//        @RequiresPermissions("personnel:manager:staff:list")
//        @RequestMapping(value = {"list",""})
//        public String contractInfoList(Staff staff, Model model) {
//            model.addAttribute("staff", staff);
//            return "modules/personnel/manager/contractInfoList";
//        }
//
//
//
//        /**
//         * 合同信息列表数据
//         */
//        @ResponseBody
//        @RequiresPermissions("personnel:manager:staff:list")
//        @RequestMapping(value = "data")
//        public Map<String, Object> contractInfodata(Staff staff, HttpServletRequest request, HttpServletResponse response, Model model) {
//            Page<Staff> page = staffService.findPage(new Page<Staff>(request, response), staff);
//            return getBootstrapData(page);
//        }
//
//        /**
//         * 查看，增加，编辑员工信息表单页面
//         */
//        @RequiresPermissions(value={"personnel:manager:staff:view","personnel:manager:staff:add","personnel:manager:staff:edit"},logical= Logical.OR)
//        @RequestMapping(value = "form/{mode}")
//        public String form(@PathVariable String mode, Staff staff, Model model) {
//            model.addAttribute("staff", staff);
//            model.addAttribute("mode", mode);
//            return "modules/personnel/manager/contractInfoForm";
//        }
//
//        /**
//         * 保存员工信息
//         */
//        @ResponseBody
//        @RequiresPermissions(value={"personnel:manager:staff:add","personnel:manager:staff:edit"},logical=Logical.OR)
//        @RequestMapping(value = "save")
//        public AjaxJson save(Staff staff, Model model) throws Exception{
//            AjaxJson j = new AjaxJson();
//            /**
//             * 后台hibernate-validation插件校验
//             */
//            String errMsg = beanValidator(staff);
//            if (StringUtils.isNotBlank(errMsg)){
//                j.setSuccess(false);
//                j.setMsg(errMsg);
//                return j;
//            }
//            //新增或编辑表单保存
//            if (staff.getIsNewRecord()) {
//                if (staff.getCode() != null) {
//                    Staff s = new Staff();
//                    s.setCode(staff.getCode());
//                    Staff staff1 = staffService.find(s);
//                    if (staff1 != null) {
//                        j.setSuccess(false);
//                        j.setMsg("员工编号重复");
//                        return j;
//                    }
//                }
//                if (staff.getContractCode()!=null){
//                    Staff s = new Staff();
//                    s.setContractCode(staff.getContractCode());
//                    Staff staff1 = staffService.find(s);
//                    if (staff1 != null) {
//                        j.setSuccess(false);
//                        j.setMsg("员工合同编号重复");
//                        return j;
//                    }
//                }
//                if (staff.getSecretCode()!=null){
//                    Staff s = new Staff();
//                    s.setSecretCode(staff.getSecretCode());
//                    Staff staff1 = staffService.find(s);
//                    if (staff1 != null) {
//                        j.setSuccess(false);
//                        j.setMsg("员工保密协议编号重复");
//                        return j;
//                    }
//                }
//                if (staff.getContactType() != null) {
//                    Staff s = new Staff();
//                    s.setContactType(staff.getContactType());
//                    Staff staff1 = staffService.find(s);
//                    if (staff1 != null) {
//                        j.setSuccess(false);
//                        j.setMsg("员工联系方式重复");
//                        return j;
//                    }
//                }
//                if (staff.getIdCard() != null) {
//                    Staff s = new Staff();
//                    s.setIdCard(staff.getIdCard());
//                    Staff staff1 = staffService.find(s);
//                    if (staff1 != null) {
//                        j.setSuccess(false);
//                        j.setMsg("员工身份证号重复");
//                        return j;
//                    }
//                }
//            }else{
//                if (staff.getCode() != null) {
//                    Staff s = new Staff();
//                    s.setCode(staff.getCode());
//                    Staff staff1 = staffService.find(s);
//                    if (staff1 != null && !staff1.getId().equals(staff.getId())) {
//                        j.setSuccess(false);
//                        j.setMsg("员工编号重复");
//                        return j;
//                    }
//                }
//                if (staff.getContractCode() != null) {
//                    Staff s = new Staff();
//                    s.setContractCode(staff.getContractCode());
//                    Staff staff1 = staffService.find(s);
//                    if (staff1 != null && !staff1.getId().equals(staff.getId())) {
//                        j.setSuccess(false);
//                        j.setMsg("员工合同编号重复");
//                        return j;
//                    }
//                }
//                if (staff.getSecretCode() != null) {
//                    Staff s = new Staff();
//                    s.setSecretCode(staff.getSecretCode());
//                    Staff staff1 = staffService.find(s);
//                    if (staff1 != null && !staff1.getId().equals(staff.getId())) {
//                        j.setSuccess(false);
//                        j.setMsg("员工保密协议编号重复");
//                        return j;
//                    }
//                }
//
//                if (staff.getContactType() != null) {
//                    Staff s = new Staff();
//                    s.setContactType(staff.getContactType());
//                    Staff staff1 = staffService.find(s);
//                    if (staff1 != null && !staff1.getId().equals(staff.getId())) {
//                        j.setSuccess(false);
//                        j.setMsg("员工联系方式重复");
//                        return j;
//                    }
//                }
//                if (staff.getIdCard() != null) {
//                    Staff s = new Staff();
//                    s.setIdCard(staff.getIdCard());
//                    Staff staff1 = staffService.find(s);
//                    if (staff1 != null && !staff1.getId().equals(staff.getId())) {
//                        j.setSuccess(false);
//                        j.setMsg("员工身份证号重复");
//                        return j;
//                    }
//                }
//            }
//
//            staffService.save(staff);//保存
//            j.setSuccess(true);
//            j.setMsg("保存员工信息成功");
//            return j;
//        }
//
//        /**
//         * 删除员工信息
//         */
//        @ResponseBody
//        @RequiresPermissions("personnel:manager:staff:del")
//        @RequestMapping(value = "delete")
//        public AjaxJson delete(Staff staff) {
//            AjaxJson j = new AjaxJson();
//            staffService.delete(staff);
//            j.setMsg("删除员工信息成功");
//            return j;
//        }
//
//        /**
//         * 批量删除员工信息
//         */
//        @ResponseBody
//        @RequiresPermissions("personnel:manager:staff:del")
//        @RequestMapping(value = "deleteAll")
//        public AjaxJson deleteAll(String ids) {
//            AjaxJson j = new AjaxJson();
//            String idArray[] =ids.split(",");
//            for(String id : idArray){
//                staffService.delete(staffService.get(id));
//            }
//            j.setMsg("删除员工信息成功");
//            return j;
//        }
//
//        /**
//         * 导出excel文件
//         */
//        @ResponseBody
//        @RequiresPermissions("personnel:manager:staff:export")
//        @RequestMapping(value = "export")
//        public AjaxJson exportFile(Staff staff, HttpServletRequest request, HttpServletResponse response) {
//            AjaxJson j = new AjaxJson();
//            try {
//                String fileName = "员工信息"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
//                Page<Staff> page = staffService.findPage(new Page<Staff>(request, response, -1), staff);
//                new ExportExcel("员工信息", Staff.class).setDataList(page.getList()).write(response, fileName).dispose();
//                j.setSuccess(true);
//                j.setMsg("导出成功！");
//                return j;
//            } catch (Exception e) {
//                j.setSuccess(false);
//                j.setMsg("导出员工信息记录失败！失败信息："+e.getMessage());
//            }
//            return j;
//        }
//
//        /**
//         * 导入Excel数据
//
//         */
//        @ResponseBody
//        @RequiresPermissions("personnel:manager:staff:import")
//        @RequestMapping(value = "import")
//        public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
//            AjaxJson j = new AjaxJson();
//            try {
//                int successNum = 0;
//                int failureNum = 0;
//                StringBuilder failureMsg = new StringBuilder();
//                ImportExcel ei = new ImportExcel(file, 1, 0);
//                List<StaffVo> list = ei.getDataList(StaffVo.class);
//                for (StaffVo vo : list){
//                    try{
//                        Staff staff = new Staff();
//                        Org org = UserUtils.getByOrgName(vo.getName());
//                        System.out.println(org);
//                        staffService.save(staff);
//                        successNum++;
//                    }catch(ConstraintViolationException ex){
//                        failureNum++;
//                    }catch (Exception ex) {
//                        failureNum++;
//                    }
//                }
//                if (failureNum>0){
//                    failureMsg.insert(0, "，失败 "+failureNum+" 条员工信息记录。");
//                }
//                j.setMsg( "已成功导入 "+successNum+" 条员工信息记录"+failureMsg);
//            } catch (Exception e) {
//                j.setSuccess(false);
//                j.setMsg("导入员工信息失败！失败信息："+e.getMessage());
//            }
//            return j;
//        }
//
//        /**
//         * 下载导入员工信息数据模板
//         */
//        @ResponseBody
//        @RequiresPermissions("personnel:manager:staff:import")
//        @RequestMapping(value = "import/template")
//        public AjaxJson importFileTemplate(HttpServletResponse response) {
//            AjaxJson j = new AjaxJson();
//            try {
//                String fileName = "员工信息数据导入模板.xlsx";
//                List<Staff> list = Lists.newArrayList();
//                new ExportExcel("员工信息数据", Staff.class, 1).setDataList(list).write(response, fileName).dispose();
//                return null;
//            } catch (Exception e) {
//                j.setSuccess(false);
//                j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
//            }
//            return j;
//        }
//
//    }
//
