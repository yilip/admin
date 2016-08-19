/**
 * 
 */
package com.lip.admin.common.security;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.lip.admin.common.security.model.AdminModel;
import com.lip.admin.common.service.IAdminService;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**用户详细信息获取
 * @author lip
 * @date 2015年12月28日
 */
public class AdminUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(AdminUserDetailsService.class);
    private IAdminService adminService;
    
    /**
     * @date 2015年12月28日
     * @param username
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        AdminUserDetails userDetails = new AdminUserDetails();
        userDetails.setAdminId(username);
        
        ///加载用户基本信息
        AdminModel adminModel = adminService.getAdminByAdminId(username);
        try {
            PropertyUtils.copyProperties(userDetails, adminModel);
        } catch (IllegalAccessException e) {
            logger.error("用户信息复制到userDetails出错",e);
        } catch (InvocationTargetException e) {
            logger.error("用户信息复制到userDetails出错",e);
        } catch (NoSuchMethodException e) {
            logger.error("用户信息复制到userDetails出错",e);
        }
        //加载权限信息
        List<AdminRoleGrantedAuthority> authorities = this.adminService.getAuthorityByUserId(username);
        if (authorities == null || authorities.size() == 0) {////如果为普通用户
            if (isCommonUserRequest()) {
          AdminRoleGrantedAuthority authority = 
                new AdminRoleGrantedAuthority(AdminRoleGrantedAuthority.ADMIN_ROLE_TYPE_COMMON_USER);
          userDetails.getAuthorities().add(authority);
            } else {
                logger.warn("person authorities is empty, personId is [{}]", username);
            }
        }
        //加载用户权限
        userDetails.getAuthorities().addAll(authorities);
        
        ///这个就是权限系统最后的用户信息
        return userDetails;
    }

    /**
     * 判断是否为普通用户请求
     * @date 2015年12月28日
     * @return
     */
    private boolean isCommonUserRequest() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * @return the adminService
     */
    public IAdminService getAdminService() {
        return adminService;
    }

    /**
     * @param adminService the adminService to set
     */
    public void setAdminService(IAdminService adminService) {
        this.adminService = adminService;
    }

}
