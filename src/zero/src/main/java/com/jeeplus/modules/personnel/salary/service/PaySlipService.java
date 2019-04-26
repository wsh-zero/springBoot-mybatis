/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.gallant.service.GallantapplicationService;
import com.jeeplus.modules.personnel.manager.entity.SalaryCard;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.mapper.SalaryCardMapper;
import com.jeeplus.modules.personnel.plan.entity.GradeBonus;
import com.jeeplus.modules.personnel.plan.entity.TitleBonus;
import com.jeeplus.modules.personnel.plan.mapper.GradeBonusMapper;
import com.jeeplus.modules.personnel.plan.mapper.TitleBonusMapper;
import com.jeeplus.modules.personnel.salary.entity.SallaryManager;
import com.jeeplus.modules.personnel.salary.mapper.SallaryManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.salary.entity.PaySlip;
import com.jeeplus.modules.personnel.salary.mapper.PaySlipMapper;

/**
 * 工资条Service
 *
 * @author ww
 * @version 2019-03-29
 */
@Service
@Transactional(readOnly = true)
public class PaySlipService extends CrudService<PaySlipMapper, PaySlip> {

    @Autowired
    private SalaryCardMapper salaryCardMapper;
    @Autowired
    private SallaryManagerMapper sallaryManagerMapper;
    @Autowired
    private PaySlipMapper paySlipMapper;
    @Autowired
    private GradeBonusMapper gradeBonusMapper;
    @Autowired
    private TitleBonusMapper titleBonusMapper;

    public PaySlip get(String id) {
        return super.get(id);
    }

    public List<PaySlip> findList(PaySlip paySlip) {
        return super.findList(paySlip);
    }

    public Page<PaySlip> findPage(Page<PaySlip> page, PaySlip paySlip) {
        return super.findPage(page, paySlip);
    }

    /**
     * 自动生成工资条
     */
    @Transactional(readOnly = false)
    public void antoSave() {
        PaySlip paySlip = new PaySlip();
        List<SalaryCard> list = salaryCardMapper.getActive();
        for (SalaryCard salaryCard : list) {
            Staff staff = salaryCard.getName();
            SallaryManager sallaryManager = new SallaryManager();
            sallaryManager.setName(staff);
            Double d1 = 0.0;
            Double d2 = 0.0;
            Double d3 = 0.0;
            Double d4 = 0.0;
            Double d5 = 0.0;
            Double d6 = 0.0;
            Double d7 = 0.0;
            Double d8 = 0.0;

            sallaryManager = sallaryManagerMapper.find(sallaryManager);
            if (sallaryManager.getAccumulation() != null) {
                paySlip.setAccumulation(sallaryManager.getAccumulation());
                 d1 = paySlip.getAccumulation();
            }
            if (sallaryManager.getDisplayBonus() != null) {
                paySlip.setDisplayBonus(sallaryManager.getDisplayBonus());
                 d2 = paySlip.getDisplayBonus();
            }
            if (sallaryManager.getName() != null) {
                paySlip.setName(sallaryManager.getName());
            }
            if (sallaryManager.getEducationBonus() != null) {
                paySlip.setEducationBonus(sallaryManager.getEducationBonus());
                 d3 = paySlip.getEducationBonus();
            }
            if (sallaryManager.getQualityBonus() != null) {
                paySlip.setQualityBonus(sallaryManager.getQualityBonus());
                 d4 = paySlip.getQualityBonus();
            }
            if (sallaryManager.getGrade() !=null){
                GradeBonus gradeBonus = gradeBonusMapper.get(sallaryManager.getGrade().getId());
                paySlip.setGradeBonus(gradeBonus.getBonus());
                 d5 = paySlip.getGradeBonus();
            }
            if (sallaryManager.getTitle()!=null){
                TitleBonus titleBonus =  titleBonusMapper.get(sallaryManager.getTitle().getId());
                paySlip.setTitleBonus(titleBonus.getBonus());
                 d6 = paySlip.getTitleBonus();
            }
            if (sallaryManager.getSocialSecurity() != null) {
                paySlip.setSocialSecurity(sallaryManager.getSocialSecurity());
                 d7 = paySlip.getSocialSecurity();
            }
            if (sallaryManager.getPreWage() != null) {
                paySlip.setPreWage(sallaryManager.getPreWage());
                 d8 = paySlip.getPreWage();
            }

            paySlip.setStatus("0");
            paySlip.setPreWage(Double.valueOf(Math.random()*10000+1));
            paySlip.setAchPoint((int)(Math.random()*100+1));
            paySlip.setPunish(Double.valueOf(Math.random()*100+1));
            paySlip.setAttendance(Double.valueOf(Math.random()*100+1));
            paySlip.setTax(0.0);
//            paySlip.setWage(Math.random()*10000+1);
            paySlip.setWage(d8+d1+d7+paySlip.getAttendance()+paySlip.getTax()+paySlip.getPunish());
            paySlip.setAchBonus(Double.valueOf(Math.random()*100+1));


            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DATE,1);
            calendar.set(Calendar.MILLISECOND, 0);
            paySlip.setReleaseTime(calendar.getTime());

//            paySlip.setTotal(Math.random()*10000+1);
            paySlip.setTotal(paySlip.getWage()+d3+d6+d5+paySlip.getAchBonus());
            paySlip.setId(IdGen.uuid());
            paySlipMapper.add(paySlip);
        }

    }

    @Transactional(readOnly = false)
    public void save(PaySlip paySlip) {
        super.save(paySlip);
    }

    @Transactional(readOnly = false)
    public void delete(PaySlip paySlip) {
        super.delete(paySlip);
    }

}