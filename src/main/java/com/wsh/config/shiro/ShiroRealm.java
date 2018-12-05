package com.wsh.config.shiro;

import com.wsh.zero.entity.SysPowerEntity;
import com.wsh.zero.entity.SysRoleEntity;
import com.wsh.zero.entity.SysUserEntity;
import com.wsh.zero.mapper.SysUserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取登录用户名
        String userName = (String) principals.getPrimaryPrincipal();
        SysUserEntity entity = sysUserMapper.getUserInfoByUserName(userName);
        //用户的角色，及权限进行绑定
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (SysRoleEntity role : entity.getRoleList()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (SysPowerEntity power : role.getPowerList()) {
                //添加权限
                simpleAuthorizationInfo.addStringPermission(power.getPowerPath());
            }
        }
        return simpleAuthorizationInfo;
    }

    //验证用户登录信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (token.getPrincipal() == null) {
            return null;
        }
        String userName = (String) token.getPrincipal();
        //从数据库查询出User信息及用户关联的角色，权限信息，以备权限分配时使用
        SysUserEntity entity = sysUserMapper.getUserInfoByUserName(userName);
        if (null == entity) return null;
        return new SimpleAuthenticationInfo(
                entity.getUserName(), //用户名
                entity.getUserPwd(), //密码
                getName()  //realm name
        );
    }

}
