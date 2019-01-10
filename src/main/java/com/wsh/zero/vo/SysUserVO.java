package com.wsh.zero.vo;

import com.wsh.util.FileUtil;
import com.wsh.zero.vo.base.BaseVO;
import lombok.Data;

import java.util.List;

/**
 * 传输到页面的实体类
 */
@Data
public class SysUserVO extends BaseVO {
    private String id;
    private String userName;
    private String userAmount;
    private String userPwd;
    private String picture;
    private List<SysRoleVO> roleList;

    public String getPicture() {
        return FileUtil.RELATIVE_PATH + picture;
    }
}