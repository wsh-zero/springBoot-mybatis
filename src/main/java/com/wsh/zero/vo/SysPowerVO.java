package com.wsh.zero.vo;

import com.wsh.zero.vo.base.BaseVO;
import lombok.Data;

/**
 * 传输到页面的实体类
 */
@Data
public class SysPowerVO extends BaseVO {
    private String id;
    private String powerName;
    private String name;
    private String powerPath;
    private String parent;
    private Integer powerType = 1;

    public String getName() {
        return this.powerName;
    }
}