<%@page import="com.lip.admin.common.security.util.MyRequestContextHolder" %>
<%@page import="com.lip.admin.common.security.AdminUserDetails" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
AdminUserDetails adminUser = MyRequestContextHolder.getCurrentUser();
%>
<style>
.x-form-item .error{display:block;}
.clearfix .div{display:block;}
</style>
<script type="text/javascript">
var adminId = '<%=adminUser.getAdminId() %>';
var postCode = '<%=adminUser.getPostCode()%>';
</script>
<script type="text/javascript" src="<%=path%>/js/layout/homepage.js"></script>
<style>
  .outDiv{width: 1400px;overflow: hidden;}
</style>
    <div class="outDiv">
            <div class="personInfo" style="float:left; width:336px;overflow:hidden;text-overflow:ellipsis;margin-left:80px;margin-top:20px">
                <div class="title tit1"><span>个人信息</span></div>
                <ul class="ul1">
                    <li>用户名<span><%=adminUser.getAdminId() %></span></li>
                    <li>真实姓名<span><%=adminUser.getRealName() %></span></li>
                    <li>部门<span><%=adminUser.getDeptText() %></span></li>
                    <li>状态<span><%=adminUser.getStatusName() %></span></li>
                    <li>员工号<span><%=adminUser.getEmployeeNo() %></span></li>
                    <li>邮箱<span id="emailLi"><%=adminUser.getEmail() %></span></li>
                    <li>手机号<span id="phoneLi"><%=adminUser.getMobile() %></span></li>
                    <li>职位<span title="<%=adminUser.getPost() %>" style="width:190px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;" ><%=adminUser.getPost() %></span></li>
                </ul>
                <a href="javascript:void(0);" class="a_1" id="emailBtn">修改邮箱</a><a id="phoneBtn" href="javascript:void(0);" class="a_2">修改手机号</a><a id="passBtn" href="javascript:void(0);" class="a_3">修改密码</a>
            </div>
            
            <div class="personInfo"  style="float:left; width:800px;margin-left:40px;margin:20px">
                <div class="title tit3"><span>最新操作日志</span></div>
                <div class="ul2">
                    <table id="operateLog"></table>
                </div>
            </div>
    </div>
    
    
    
    <div id="homePageDiv">
        
        <input id="userId" type="hidden" value="<%=adminUser.getId() %>"/>
    
        <div id="emailWin" class="add">
            <form id="emailForm" method="post">
                <div class="x-form-item">
                    <label class="x-form-item-label">原邮箱:</label>
                    <div class="x-form-element">
                        <input type="text" id="oldEmail" disabled="disabled" value="<%=adminUser.getEmail() %>"/>
                    </div>
                </div>
                <div class="x-form-item">
                    <label class="x-form-item-label">邮箱验证码:</label>
                    <div class="x-form-element authCode">
                        <span class="yzm"><input type="text" id="emailOldVerifyCode" name="emailOldVerifyCode" class="authInput" maxlength="6" placeholder="邮箱验证码"/><input type="button" class="authBtn" value="获取验证码" onclick="getOldEmailAuthCode()"/></span>
                    </div>
                    <div class="errorMsg"></div>
                </div>
                <div class="x-form-item">
                    <label class="x-form-item-label">新邮箱:</label>
                    <div class="x-form-element">
                        <input type="text" id="newEmail" name="newEmail" placeholder="新邮箱"/>
                    </div>
                    <div class="errorMsg"></div>
                </div>
                <div class="x-form-item">
                    <label class="x-form-item-label">邮箱验证码:</label>
                    <div class="x-form-element authCode">
                        <span class="yzm"><input type="text" id="emailNewVerifyCode" name="emailNewVerifyCode" class="authInput" maxlength="6" placeholder="邮箱验证码"/><input type="button" class="authBtn" value="获取验证码" onclick="getNewEmailAuthCode()"/></span>
                    </div>
                    <div class="errorMsg"></div>
                </div>
                
                <div class="psb">
                    <a id="emailSaveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="emailCloseBtn" class="easyui-linkbutton searchBtn">取消</a>
                </div>
            </form>
        </div>
        <div id="phoneWin" class="add">
            <form id="phoneForm" method="post">
                <div class="x-form-item">
                    <label class="x-form-item-label">原手机号:</label>
                    <div class="x-form-element">
                        <input type="text" id="oldMobile" disabled="disabled" value="<%=adminUser.getMobile() %>"/>
                    </div>
                    <div class="errorMsg"></div>
                </div>
                <div class="x-form-item">
                    <label class="x-form-item-label">短信验证码:</label>
                    <div class="x-form-element authCode">
                        <span class="yzm"><input type="text" id="oldVerifyCode" name="oldVerifyCode" class="authInput" placeholder="手机验证码"/><input type="button" class="authBtn" value="获取验证码" onclick="getOldAuthCode();"/></span>
                    </div>
                    <div class="errorMsg"></div>
                </div>
                <div class="x-form-item">
                    <label class="x-form-item-label">新手机号:</label>
                    <div class="x-form-element">
                        <input type="text" id="newMobile" name="newMobile" placeholder="新手机号"/>
                    </div>
                    <div class="errorMsg"></div>
                </div>
                <div class="x-form-item">
                    <label class="x-form-item-label">短信验证码:</label>
                    <div class="x-form-element authCode">
                        <span class="yzm"><input type="text" id="newVerifyCode" name="newVerifyCode" class="authInput" placeholder="手机验证码"/><input type="button" class="authBtn" value="获取验证码" onclick="getNewAuthCode();"/></span>
                    </div>
                    <div class="errorMsg"></div>
                </div>
                
                <div class="psb">
                    <a id="phoneSaveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="closeBtn" class="easyui-linkbutton searchBtn">取消</a>
                </div>
            </form>
        </div>
        <div id="passWin" class="add">
            <form id="passForm" method="post">
                <div class="x-form-item">
                    <label class="x-form-item-label">用户名:</label>
                    <div class="x-form-element">
                        <label><%=adminUser.getAdminId() %></label>
                    </div>
                </div>
                <div class="x-form-item">
                    <label class="x-form-item-label">员工号:</label>
                    <div class="x-form-element">
                        <label><%=adminUser.getEmployeeNo() %></label>
                    </div>
                </div>
                <div class="x-form-item">
                    <label class="x-form-item-label">旧密码:</label>
                    <div class="x-form-element">
                        <input type="password" id="oldPassword" name="oldPassword" placeholder="旧密码"/>
                    </div>
                </div>
                <div class="x-form-item">
                    <label class="x-form-item-label">新密码:</label>
                    <div class="x-form-element">
                        <input type="password" id="newPassword" name="newPassword" placeholder="新密码"/>
                    </div>
                </div>
                <div class="x-form-item">
                    <label class="x-form-item-label">确认密码:</label>
                    <div class="x-form-element">
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="重复密码"/>
                    </div>
                </div>
                
                <div class="psb">
                    <a id="pwdSaveBtn" class="easyui-linkbutton searchBtn">保存</a><a id="pwdCloseBtn" class="easyui-linkbutton searchBtn">取消</a>
                </div>
            </form>
        </div>
    </div>
        
        

