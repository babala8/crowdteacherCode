package com.atguigu.crowd.constant;

public class CrowdConstant {
	
	public static final String MESSAGE_LOGIN_FAILED = "抱歉！账号密码错误！请重新输入！";
	public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "抱歉！这个账号已经被使用了！";
	public static final String MESSAGE_ACCESS_FORBIDEN = "请登录以后再访问！";
	public static final String MESSAGE_STRING_INVALIDATE = "字符串不合法！请不要传入空字符串！";
	public static final String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "系统错误：登录账号不唯一！";
	public static final String MESSAGE_ACCESS_DENIED = "抱歉！您不能访问这个资源！";
	
	public static final String ATTR_NAME_EXCEPTION = "exception";
	public static final String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
	public static final String ATTR_NAME_LOGIN_MEMBER = "loginMember";
	public static final String ATTR_NAME_PAGE_INFO = "pageInfo";

    public static final String MESSAGE_LOGIN_ACCT_NOT_REGIST = "请您先注册后，再进行访问！";
	public static final String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX_";
    public static final String ATTR_NAME_MESSAGE = "message";
	public static final String MESSAGE_CODE_NOT_EXISTS = "验证码已过期！请检查手机号并重新发送！";
	public static final String MESSAGE_CODE_INVALIED = "验证码不正确！";
    public static final String ATTR_NAME_TEMPLE_PROJECT = "templeProject";
	public static final String MESSAGE_TEMPLE_PROJECT_MISSING = "templeProject在redis中不存在！";
    public static final String ATTR_NAME_PORTAL_DATA = "portal_data";
    public static final String ATTR_NAME_TEMPLE_ORDER_PROJECT = "orderProjectVO";
}
