package com.jeeplus.modules.tools.utils;

import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wangwei on 2019/1/14.
 */
@Service
@Transactional(readOnly = false)
public class AreaUtil {

    @Autowired
    private AreaService areaService;
    public String name = "";

    public AreaUtil() {};

    public String getParent(String id) {
        if (id.equals("1")){
            name  = name.substring(0,name.length() - 1);
            return name;
        }else {
            Area area = areaService.get(id);
            name =area.getName()+"/"+ name ;
            String areaId = areaService.getparent(id);
            getParent(areaId);
        }
       return name;
    };

};

