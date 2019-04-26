package com.jeeplus.modules.enums;

/**
 * 系统编码
 *
 */
public enum SystemCode {
    SUCCESS("000000", "成功"),
    ERROR_1("000001", "token失效"),
    ERROR_2("000002", "token获取用户信息失败"),
    ERROR_5("000005","操作失败"),
    ERROR_6("000006","业务处理失败"),
    ERROR_7("000007","参数丢失"),
    ERROT_8("000008","导出异常"),
    ERROR_9("000009","传入数据异常"),
    ERROR_10("000010","结束时间要大于开始时间"),
    ERROR_11("000011","拼命查找数据中,请等等!"),
    /**
     * 01XXXX用户模块
     */
    // 用户登陆模块code 0101XX
    LOGIN_ERROR_01("010101","账户或密码错误"),
    LOGIN_ERROR_02("010102","登陆失败"),
    REGISTER_001("010201","两次密码不相同"),
    REGISTER_002("010202","验证码已过期"),
    REGISTER_003("010203","验证码不正确"),
    REGISTER_004("010204","注册失败"),
    /*

    /**
     * 修改密码模块code 0301XX
     */
    PASSWORD_001("0301001","密码错误"),
    PASSWORD_002("0301002","此手机号不是员工帐号"),

    /**
     * 修改用户信息
     */
     EDIT_ERROR_001("020201","用户信息缺失");





    /* 业务级end */


    // 团队&业务常量
    public static final String domainBusiness = "0000015";
    private String errorCode;
    private String msg;

    private SystemCode(String code, String msg) {
        this.errorCode = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.errorCode;
    }

    public String getAllCode() {
        return domainBusiness + this.errorCode;
    }

    public String getMsg() {
        return this.msg;
    }

    public static SystemCode getEnumsLogin(String code) {
        SystemCode[] array = SystemCode.values();
        for (SystemCode enumsLogin : array) {
            if (enumsLogin.getCode().equals(code)) {
                return enumsLogin;
            }
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
