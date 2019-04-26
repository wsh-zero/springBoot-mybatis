package com.jeeplus.common.taskhandler.handler;

import com.jeeplus.common.taskhandler.annotation.CommonHandler;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.core.persistence.ActEntity;
import com.jeeplus.modules.act.entity.Act;
import com.jeeplus.modules.personnel.manager.service.StaffService;
import com.jeeplus.modules.sys.entity.User;

/**
 * 直系领导处理者
 * @author chentao
 * @version 2019-04-02
 */
@CommonHandler
public class LeaderTaskHandler implements TaskHandler {

    @Override
    public String execute(ActEntity<?> actEntity) {

        Act act = actEntity.getAct();
        StaffService staffService = SpringContextHolder.getBean(StaffService.class);
        User user = staffService.getUser(actEntity.getCreateBy().getId());
        return user.getLoginName();
    }
}