package com.jeeplus.common.websocket.service.notify;

import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * author: chentao
 */
@Service
@Transactional
public class CommonNotifyServiceImpl implements CommonNotifyService {

    @Autowired
    private OaNotifyService oaNotifyService;

    @Override
    public void sendNotify(OaNotify notify) {

        oaNotifyService.save(notify);
        SystemInfoSocketHandler handler = new SystemInfoSocketHandler();
        for (OaNotifyRecord record : notify.getOaNotifyRecordList()) {
            handler.sendMessageToUser(record.getUser().getLoginName(), notify.getTitle());
        }
    }
}
