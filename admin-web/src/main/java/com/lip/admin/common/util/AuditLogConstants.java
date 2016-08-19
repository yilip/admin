package com.lip.admin.common.util;

/**
 * 操作日志常量类
 * @author lip
 * @date 2015年12月30日
 */
public class AuditLogConstants {
    
    
    /** 登录操作**/
    public static final String LOGIN = "login";
    public static final String AUDIT = "audit_operate_type";
    /*--------------------------- 执行结果 ---------------------------*/
    /** 成功**/
    public static final String RESULT_SUCCESS = "success";
    /** 失败**/
    public static final String RESULT_FAIL = "fail";
    
    /*--------------------------- 操作类型 ---------------------------*/
    /** 添加 **/
    public static final String TYPE_ADD = "add";
    
    /** 修改 **/
    public static final String TYPE_MODIFY = "modify";
    
    /** 删除 **/
    public static final String TYPE_DELETE = "delete";
    /** 排序 **/
    public static final String TYPE_ORDER = "order";
   
    /** 同意  **/
    public static final String TYPE_APPROVE  = "approve";
    /** 拒绝  **/
    public static final String TYPE_REJECT  = "reject";
    
    /** 编辑权限  **/
    public static final String TYPE_ADDPERMISSION  = "addPermission";
    /** 修改手机号  **/
    public static final String TYPE_MODIFYMOBILE  = "modifyMobile";
    /** 修改手机号  **/
    public static final String TYPE_MODIFYEMAIL  = "modifyEmail";
    /** 重置密码 **/
    public static final String TYPE_RESETPASSWORD = "resetPassword";
    /** 发送邮件  **/
    public static final String TYPE_SENDEMAIL  = "sendEmail";
    
    /** 发送短信 **/
    public static final String TYPE_SENDSMS  = "sendSMS";
    
    /** 查询 （如生成报表）*/
    public static final String TYPE_GET = "get";
    
    
    /*操作模块*/
    /** 权限管理-->> 角色管理 **/
    public static final String MODEL_ROLE= "role";
    /** 系统管理-->> 系统配置 **/
    public static final String MODEL_SYSCONFIG= "sysConfig";
    /** 财务管理-->> 提现审核流程 **/
    public static final String MODEL_WITHDRAWAUDIT= "withdrawaudit";
    //管理员管理
    /** 用户管理 -->> 管理员 **/
    public static final String MODEL_ADMINUSER= "adminUser";
    /** 项目管理 -->> 录入项目 **/
    public static final String MODEL_LOAN= "loan";
   
    
    /** 权限管理-->> 资源管理 **/
    public static final String MODEL_RESOURCE= "resource";
    /** 首页管理 **/
    public static final String MODEL_HOMEPAGE = "homepage";
    /** 用户管理 **/
    public static final String MODEL_USER = "user";
    /** 财务管理 **/
    public static final String MODEL_FINANCE = "finance";
    /** 系统配置 **/
    public static final String MODEL_CONFIG = "config";
    /** 权限管理 **/
    public static final String MODEL_AUTHORITY = "authority";
    /** 审计管理 **/
    public static final String MODEL_AUDIT = "audit";
    /** 渠道管理 **/
    public static final String MODEL_CHANNEL = "channel";
    /** 交易所管理 **/
    public static final String MODEL_EXCHANGE = "exchange";
    
    
    
}