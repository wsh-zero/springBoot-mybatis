package com.jeeplus.common.taskhandler.handler;

import com.jeeplus.common.taskhandler.utils.UserNameUtils;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.core.persistence.ActEntity;
import com.jeeplus.modules.personnel.plan.mapper.OrgMapper;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.OfficeMapper;
import com.jeeplus.modules.sys.mapper.UserMapper;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.utils.UserUtils;
import java.util.List;

/**
 * 部门处理者
 * @author chentao
 * @version 2019-04-03
 */
public class OrgaTaskHandler implements TaskHandler {

    /**
     * 部门名称
     */
    private String orgaName;
    private UserMapper userMapper;

    public OrgaTaskHandler(String name) {
        this.orgaName = name;
    }

    @Override
    public String execute(ActEntity<?> actEntity) {

        UserMapper mapper = SpringContextHolder.getBean(UserMapper.class);
        OfficeMapper officeMapper = SpringContextHolder.getBean(OfficeMapper.class);
        User user = new User();
        Office office = new Office();
        office.setId(officeMapper.getIdByName(orgaName));
        user.setOffice(office);
        List<User> users = mapper.findListByOffice(user);
        return UserNameUtils.concatUserNames(users);
    }
}
