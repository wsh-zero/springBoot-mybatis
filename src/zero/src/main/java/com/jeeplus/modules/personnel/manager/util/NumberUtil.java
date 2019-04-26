package com.jeeplus.modules.personnel.manager.util;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.personnel.manager.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public  class NumberUtil {

    //员工编号
    public static String STALLCODE="ZHSTYG";
    //合同编号
    public static String AGREECODE="ZHSTHT";
    //保密协议编号
    public static String SECRECYCODE="ZHSTBM";
    //岗位编号
    public static String STATIONCODE="ZHSTGW";

    @Autowired
    private StaffMapper staffMapper;


    public String getMaxnumber(){
        Integer currmaxcode=staffMapper.getMaxcode();

        if(currmaxcode==null || currmaxcode<=0){
            return "0001";
        }else{
            DecimalFormat countFormat = new DecimalFormat("0000");
            return countFormat.format(currmaxcode+1);
        }

    }


}
