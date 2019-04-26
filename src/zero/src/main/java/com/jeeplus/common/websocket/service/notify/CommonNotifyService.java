package com.jeeplus.common.websocket.service.notify;

import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import java.util.*;

/**
 * @author chentao
 */
public interface CommonNotifyService {

    /**
     * 发送通知
     */
    void sendNotify(OaNotify notify);

    /**
     * 公共通知消息构建器
     */
    class CommonNotifyBuilder {
        /**
         * 接收人
         */
        String[] toUsers;
        /**
         * 标题
         */
        String title;
        /**
         * 内容
         */
        String content;
        /**
         * 处理链接
         */
        String handleUrl;

        public CommonNotifyBuilder toUsers(String... users) {
            toUsers = users;
            return this;
        }

        public CommonNotifyBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CommonNotifyBuilder content(String content) {
            this.content = content;
            return this;
        }

        public CommonNotifyBuilder handleUrl(String handleUrl) {
            this.handleUrl = handleUrl;
            return this;
        }

        public OaNotify build() {
            User user = UserUtils.getUser();
            Date date = new Date();
            OaNotify notify = new OaNotify();
            notify.setId(UUID.randomUUID().toString());
            notify.setTitle(this.title);
            notify.setType("1");
            notify.setContent(this.content);
            notify.setFiles(null);
            notify.setStatus("1");
            notify.setReadNum("0");
            notify.setUnReadNum(String.valueOf(toUsers != null ? toUsers.length + 1 : 0));
            notify.setCreateBy(user);
            notify.setCreateDate(date);
            notify.setUpdateBy(user);
            notify.setDelFlag("0");
            notify.setIsNewRecord(true);
            notify.setHandleUrl(this.handleUrl);

            List<OaNotifyRecord> records = new ArrayList<>(toUsers.length);
            for (int i = 0; i < toUsers.length; i ++) {

                OaNotifyRecord record = new OaNotifyRecord();
                record.setId(UUID.randomUUID().toString());
                record.setOaNotify(notify);
                record.setUser(UserUtils.get(toUsers[i]));
                record.setReadFlag("0");
                record.setIsNewRecord(true);
                records.add(record);
            }
            notify.setOaNotifyRecordList(records);
            return notify;
        }
    }
}
