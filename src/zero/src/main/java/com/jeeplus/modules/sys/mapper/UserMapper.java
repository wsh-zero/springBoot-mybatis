/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper;

import java.util.List;

import com.jeeplus.modules.asset.getstore.entity.Getstoreasset;
import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.User;
import org.springframework.security.access.method.P;

/**
 * 用户MAPPER接口
 * @author jeeplus
 * @version 2017-05-16
 */
@MyBatisMapper
public interface UserMapper extends BaseMapper<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	/**
	 * 修改姓名根据员工id
	 * @param user
     */
	void updateByStaff(User user);

	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	/**
	 * 插入好友
	 */
	public int insertFriend(@Param("id") String id, @Param("userId") String userId, @Param("friendId") String friendId);
	
	/**
	 * 查找好友
	 */
	public User findFriend(@Param("userId") String userId, @Param("friendId") String friendId);
	/**
	 * 删除好友
	 */
	public void deleteFriend(@Param("userId") String userId, @Param("friendId") String friendId);
	
	/**
	 * 
	 * 获取我的好友列表
	 * 
	 */
	public List<User> findFriends(User currentUser);
	
	/**
	 * 
	 * 查询用户-->用来添加到常用联系人
	 * 
	 */
	public List<User> searchUsers(User user);
	
	/**
	 * 
	 */
	
	public List<User>  findListByOffice(User user);


	public User  getUsers(@Param("id") String id);

	/**
	 * 根据staff获取user
	 */
	User getUserByStaff(@Param("staff") String staff);

	/**
	 * 根据id获取staff
	 */
	String getStaff(@Param("id") String id);

	int count (@Param("staff") String staff);

	User find(@Param("staff") String staff);
}
