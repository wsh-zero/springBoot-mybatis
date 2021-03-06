/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.enums.SystemCode;
import com.jeeplus.modules.personnel.manage.entity.StaffStatus;
import com.jeeplus.modules.personnel.manager.entity.Contact;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.service.ContactService;
import com.jeeplus.modules.personnel.manager.service.StaffService;
import com.jeeplus.modules.personnel.plan.entity.Station;
import com.jeeplus.modules.personnel.plan.service.StationService;
import com.jeeplus.modules.personnel.vo.UserApp;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.json.PrintJSON;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.mapper.JsonMapper;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.jeeplus.modules.sys.utils.UserUtils;

import java.util.LinkedHashMap;

/**
 * 表单验证（包含验证码）过滤类
 * @author jeeplus
 * @version 2017-5-19
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password==null){
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
		String captcha = getCaptcha(request);
		boolean mobile = isMobileLogin(request);
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMobileLoginParam() {
		return mobileLoginParam;
	}
	
	protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }
	
	public String getMessageParam() {
		return messageParam;
	}
	
	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception {
		Principal p = UserUtils.getPrincipal();
		if (p != null && !p.isMobileLogin()){
			 WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
		}else{

		    UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
		    ContactService contactService = SpringContextHolder.getBean(ContactService.class);
			StaffService staffService = SpringContextHolder.getBean(StaffService.class);
			StationService stationService = SpringContextHolder.getBean(StationService.class);

			//super.issueSuccessRedirect(request, response);//手机登录
			AjaxJson j = new AjaxJson();
			try {
				j.setSystemCode(SystemCode.SUCCESS);
				j.setMsg("登录成功!");
//			j.put("username", p.getLoginName());
//			j.put("name", p.getName());
				j.put("mobileLogin", p.isMobileLogin());
				User u = userMapper.get(p.getId());
				UserApp user = new UserApp();
				user.setCompany(u.getCompany().getName());
				user.setOffice(u.getOffice().getName());
				user.setMobile(u.getMobile());
				user.setPhoto(u.getPhoto());
				user.setLoginName(u.getLoginName());
				user.setName(u.getName());
				user.setId(u.getId());

				if (u.getStaff() != null) {
					Staff staff = staffService.get(u.getStaff().getId());
					if (staff.getStation() != null) {
						Station station = stationService.get(staff.getStation().getId());
						if (station != null) {
							user.setStation(station.getName());
						}
						Contact contact = contactService.getName(u.getId());
						if (contact != null) {
							user.setEmail(u.getEmail());
							user.setQq(contact.getQq());
							user.setWeChat(contact.getWeChat());
						}
					}
					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put("JSESSIONID", p.getSessionid());
					map.put("user", user);
					j.setBody(map);
				}
			}catch (Exception e){
				j.setSuccess(false);
				j.setSystemCode(SystemCode.ERROR_7);
				j.setMsg("用户数据异常");

			}
//			j.put("JSESSIONID", p.getSessionid());
			PrintJSON.write((HttpServletResponse)response, j.getJsonStr());
		}
	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request, ServletResponse response) {
		String className = e.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)){
			message = "用户或密码错误, 请重试.";
		}
		else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		}
		else{
			message = "系统出现点问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
		}
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
	}
	
}