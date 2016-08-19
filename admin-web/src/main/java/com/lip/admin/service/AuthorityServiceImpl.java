/**
 * 
 */
package com.lip.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.lip.admin.dao.RolePermissionDao;
import com.lip.admin.dao.UserRoleDao;
import com.lip.admin.model.PermissionItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lip.admin.dao.PermissionDao;
import com.lip.admin.dao.RoleDao;
import com.lip.admin.model.RoleItem;
import com.lip.core.utils.PKGenarator;
import com.google.common.base.Stopwatch;

/**
 * 权限管理模块service
 * @author lip
 * @date 2015年12月29日
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
    private Logger logger = LoggerFactory.getLogger(AuthorityServiceImpl.class);
    
    //权限
    @Resource
    private PermissionDao permissionDao;
    //角色
    @Resource
    private RoleDao roleDao;
    ///角色权限
    @Resource
    private RolePermissionDao rolePermissionDao;
    ///用户角色
    @Resource
    private UserRoleDao userRoleDao;
    /**
     * {@inheritDoc}
     */
    @Override
    public List<PermissionItem> getPermissionListByPid(String pid) {
        PermissionItem item = new PermissionItem();
        item.setPid(pid);
        return permissionDao.getPermissionList(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int modifyPermission(PermissionItem PermissionItem) {
        return permissionDao.modifyPermission(PermissionItem);
    }

    /**
     * {@inheritDoc}
     * @date 2015年12月29日
     * @param PermissionItem
     * @return
     */
    @Override
    public int savePermission(PermissionItem PermissionItem) {
        if ("menu".equals(PermissionItem.getPermissionType())) {
            ///添加菜单
            int maxSeqNum = permissionDao.getMaxSeqNumByPid(PermissionItem.getPid());
            permissionDao.modifyOrderByPermission("top",maxSeqNum+1, null);
            
            PermissionItem.setSeqNum(maxSeqNum+1);
        }else if ("operate".equals(PermissionItem.getPermissionType())) {
            //添加操作
            PermissionItem.setSeqNum(null);
        }
        
        return permissionDao.savePermission(PermissionItem);
    }

    /**
     * {@inheritDoc}
     * @date 2015年12月29日
     * @param targetId
     * @param sourceId
     * @param point
     * @return
     */
    @Override
    public int modifyOrderByPermission(String targetId, String sourceId, String point) {
        PermissionItem targetPermission = permissionDao.getPermissionById(targetId);
        PermissionItem sourcePermission = permissionDao.getPermissionById(sourceId);
        int targetSeqNum=targetPermission.getSeqNum();
        int sourceSeqNum=sourcePermission.getSeqNum();
        //向上移动
        if ("top".equals(point)) {
            //修改targetNode 和 sourceNode 中间的节点序号
            if (sourceSeqNum<targetSeqNum) {
                permissionDao.modifyOrderByPermission("bottom",sourceSeqNum,targetSeqNum);
            }else{
                permissionDao.modifyOrderByPermission("top",targetSeqNum,sourceSeqNum);
            }
        }else if ("bottom".equals(point)) {
            //修改targetNode 和 sourceNode 中间的节点序号
            if (targetSeqNum<sourceSeqNum) {
                permissionDao.modifyOrderByPermission("top",targetSeqNum,sourceSeqNum);
            }else{
                permissionDao.modifyOrderByPermission("bottom",sourceSeqNum,targetSeqNum);
            }
        }
        //将targetNode的序号修改为sourceNode的序号
        sourcePermission.setSeqNum(targetSeqNum);
        permissionDao.modifyPermission(sourcePermission);
        return 0;
    }

    /**
     * {@inheritDoc}
     * @date 2015年12月29日
     * @param id
     * @return
     */
    @Override
    public int removePermission(String id) {
      //判断是否是父节点，如果是父节点，就删除父节点和其下的所有子节点
        PermissionItem permission = permissionDao.getPermissionById(id);
        id=getChildList4Permission(id);
        int removeCount = permissionDao.removePermissionBatch(id.split(","));
        //更新删除节点以后的顺序值
        permissionDao.modifyOrderByPermission("bottom",permission.getSeqNum(),null);
        return removeCount;
    }
    /**
     * 递归调用获取所有子节点
     * @date 2015年12月29日
     */
    private String getChildList4Permission(String id){
        List<PermissionItem> list = this.getPermissionListByPid(id);
        for (PermissionItem permission : list) {
            id+=","+getChildList4Permission(permission.getId());
        }
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleItem> getRoleList(RoleItem roleItem) {
        return roleDao.getRoleList(roleItem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int saveRole(RoleItem roleItem) {
        roleItem.setId(PKGenarator.getId());
        return roleDao.saveRole(roleItem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int modifyRole(RoleItem roleItem) {
        return roleDao.modifyRole(roleItem);
    }

    /**
     * {@inheritDoc}
     * @date 2015年12月30日
     * @param id
     * @return
     */
    @Override
    public int removeRole(String id) {
        //先删除角色下的所有权限
        rolePermissionDao.removePermission4Role(id);
        return roleDao.removeRole(id);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleItem> getRole4User(String userId) {
        return roleDao.getRole4User(userId);
    }

    
    /**
     * {@inheritDoc}
     * @date 2015年12月30日
     * @param roleId
     * @param permissionId
     * @param permissionChecked
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    public int savePermission4Role(String roleId, String permissionId, String permissionChecked) {
      
        //删除角色下的所有的权限
        Stopwatch split = Stopwatch.createStarted();
        rolePermissionDao.removePermission4Role(roleId);
        
        List<PermissionItem> list = permissionDao.getAllPermission();
        //得到所有的permission对应的id——pid键值对
        HashMap<String,String> allPermissions =new HashMap<>(list.size());
        for (PermissionItem p : list) {
            allPermissions.put(p.getId(), p.getPid());
        }
        
        Set<String> idSet = new HashSet<String>();
        ///获取勾选的id
        String permissionIds[] = permissionId.split(",");
        String permissionCheckeds[] = permissionChecked.split(",");
        ArrayList<String> checkedPermissions =new ArrayList<>(permissionIds.length);
        for (int i = 0; i < permissionCheckeds.length; i++) {////转为数组存储，方便查找某id是否存在
            if(permissionCheckeds[i].equals("true")){
                ///checked的将其子节点也加进来（具体加载过程在后续的id判断中……）
                checkedPermissions.add(permissionIds[i]);
            }else{
                ///未checked的直接添加到role_permission中，
                idSet.add(permissionIds[i]);
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for(String id:allPermissions.keySet()){
            String pid = id;
            while((!"1".equals(pid)||!"0".equals(pid))&&!checkedPermissions.contains(pid)&&null!=pid){
                ////递归获取该id对应的permission的pid，直到为1或者在rolePermissions中
                pid = allPermissions.get(pid);
            }
            ////找到根节点permissionid后，如果是当前role下的,则加入最终的permissionid里面，否则不加
            if(checkedPermissions.contains(pid)){
                sb.append(",").append(id);
            }
        }
        ///需要去除重复的id?
        String[] ids = permissionId.split(",");
        int num = rolePermissionDao.savePermission4Role(roleId,ids);
        logger.info("Processed 编辑权限 in {} seconds",split.elapsed(TimeUnit.SECONDS));
        return num;
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int removePermission4Role(String roleId) {
        return rolePermissionDao.removePermission4Role(roleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PermissionItem> getPermission4Role(String roleId) {
        return permissionDao.getPermission4Role(roleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PermissionItem> getPermissionList(PermissionItem permissionItem) {
        return permissionDao.getPermissionList(permissionItem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int saveRole4User(String userId, String roleId) {
      //先删除用户的所有角色
        int removeNum = userRoleDao.removeRole4UserByuserId(userId);
        
        return userRoleDao.saveRole4User(userId,roleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int removeRole4User(String id) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * {@inheritDoc}
     * @date 2015年12月30日
     */
    @Override
    public List<Map<String, Object>> getUserByRole(String roleCode) {
        return this.userRoleDao.getUserByRoleId(roleCode);
    }
  
}
