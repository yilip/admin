/* 
 * Copyright (C)
 *
 * 本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的.
 *
 * ============================================================
 *
 * FileName: UserModel.java 
 *
 * Created: [2014-12-3 10:46:57] by lip
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: fbd 
 * 
 * Description: 
 * 
 * ==========================================================*/
package com.lip.admin.common.dao;

import java.util.List;

import com.lip.admin.common.base.SearchResult;
import com.lip.admin.common.security.model.AdminModel;
import com.lip.core.dao.BaseDao;

/**
 * 
 * Copyright (C)
 * 
 * Description: 管理员
 * 
 * @author lip
 * @version 1.0
 * 
 */
public interface IAdminDao extends BaseDao<AdminModel> {
    
    public List<AdminModel> getAdminList(AdminModel adminModel);

    public List<AdminModel> getAuthorityByUserId(String userId);

    public SearchResult<AdminModel> getAdminPage(AdminModel adminModel);

    public long getAdminCount(AdminModel adminModel);

    /**
     * Description: 重置密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author lip
     * Create Date: 2015-2-6 下午2:38:21
     */
    public int modifyAdminPassword(String id);

    /**
     * Description: 管理员详情
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author lip
     * Create Date: 2015-2-6 下午4:31:52
     */
    public AdminModel getAdminDetail(String id);

    /**
     * Description: 根据Id查询Admin实体
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author lip
     * Create Date: 2015-2-10 下午6:47:47
     */
    public AdminModel getAdminById(String id);

    /**
     * Description: 修改
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author lip
     * Create Date: 2015-2-10 下午6:54:38
     */
    public int modifyAdmin(AdminModel adminModel);

    /**
     * Description: 添加新的管理员
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author lip
     * Create Date: 2015-2-11 上午9:40:15
     */
    public int saveAdmin(AdminModel adminModel);
    
    /**
     * Description: 根据员工号查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author lip
     * Create Date: 2015-3-6 上午10:27:15
     */
    public AdminModel getAdminByEmployeeNo(String employeeNo);
    
    /**
     * Description: 根据手机号查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author lip
     * Create Date: 2015-3-6 上午10:30:19
     */
    public AdminModel getAdminByMobile(String mobile);

    /**
     * Description: 忘记密码，重置密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author lip
     * Create Date: 2015-2-6 下午2:38:21
     */
    public int modifyAdminPassword(String adminId, String password);

    /**
     * Description: 根据邮箱查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author lip
     * Create Date: 2015-3-6 上午10:30:19
     */
    public AdminModel getAdminByEmail(String email);

    /**
     * Description: 根据用户编号查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author lip
     * Create Date: 2015-3-6 上午10:30:19
     */
    public AdminModel getAdminByUserId(String userId);

    /**
     * Description: 根据身份正好查询管理员对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author lip
     * Create Date: 2015-4-7 下午6:08:50
     */
    public AdminModel getAdminByIDNo(String realName,String idCardNo);
}