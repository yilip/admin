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

import com.lip.admin.common.security.model.UserSecurityModel;
import com.lip.core.dao.BaseDao;

/**
 * 
 * Copyright (C)
 * 
 * Description: 用户安全dao
 * 
 * @author lip
 * @version 1.0
 * 
 */
public interface IUserSecurityDao extends BaseDao<UserSecurityModel> {
    /**
     * 
     * Description: 根据用户与用户类型查询
     * 
     * @param
     * @return UserSecurityModel
     * @throws
     * @Author lip Create Date: 2014-12-13 下午6:57:55
     */
    public UserSecurityModel findByUserId(String userId, String userType);

    /**
     * Description: 修改用户安全表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author lip
     * Create Date: 2015-2-2 下午9:41:02
     */
    public int modifyUserSecurity(UserSecurityModel userSecurity);

    /**
     * Description: 添加用户安全信息
     *
     * @param 
     * @return int
     * @throws 
     * @Author lip
     * Create Date: 2015-2-7 下午12:12:06
     */
    public int saveUserSecurity(UserSecurityModel userSecurity);
}