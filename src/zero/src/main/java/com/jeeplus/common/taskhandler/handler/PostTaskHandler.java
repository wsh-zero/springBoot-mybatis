package com.jeeplus.common.taskhandler.handler;

import com.jeeplus.common.taskhandler.utils.UserNameUtils;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.core.persistence.ActEntity;
import com.jeeplus.modules.personnel.manager.service.StaffService;
import com.jeeplus.modules.sys.entity.User;
import java.util.List;

/**
 * 岗位选择器
 * @author chentao
 * @version 2019-04-02
 */
public class PostTaskHandler implements TaskHandler {

    private String postName;

    public PostTaskHandler(String postName) {
        this.postName = postName;
    }

    @Override
    public String execute(ActEntity<?> actEntity) {

        StaffService staffService = SpringContextHolder.getBean(StaffService.class);
        List<User> users = staffService.getStaffByStation(postName);
        return UserNameUtils.concatUserNames(users);
    }
}
