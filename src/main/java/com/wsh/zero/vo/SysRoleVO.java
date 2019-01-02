package com.wsh.zero.vo;

import com.wsh.zero.vo.base.BaseVO;
import lombok.Data;

import java.util.List;

/**
 * 传输到页面的实体类
 */
@Data
public class SysRoleVO extends BaseVO {
    private String id;
    private String roleName;
    private List<SysPowerVO> powerList;
}