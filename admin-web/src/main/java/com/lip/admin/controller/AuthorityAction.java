/**
 * 
 */
package com.lip.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lip.admin.common.base.BaseAction;
import com.lip.admin.common.util.AuditLogUtils;
import com.lip.admin.model.PermissionItem;
import com.lip.admin.service.AuthorityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lip.admin.common.base.SearchResult;
import com.lip.admin.common.util.AuditLogConstants;
import com.lip.admin.model.RoleItem;
import com.lip.admin.model.TreeModel;
import com.lip.core.utils.PKGenarator;

/**
 * @author lip
 * @date 2015年12月29日
 */
@Controller
@RequestMapping("/authority")
public class AuthorityAction extends BaseAction {
    /**
     * @date 2015年12月31日
     */
    private static final long serialVersionUID = 1L;
    
    @Resource
    private AuthorityService authorityService;
    
    
    
    
    //根据父节点查询子节点权限列表
    @RequestMapping(value = "/permission/getPermissionsByPid")
    public @ResponseBody List<TreeModel> getPermissionListByPid(String pid){
        List<PermissionItem> list = authorityService.getPermissionListByPid(pid);
        List<TreeModel> returnList = new ArrayList<TreeModel>();
        for (PermissionItem pm : list) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(pm.getId());
            treeModel.setText(pm.getLabel());
            if (authorityService.getPermissionListByPid(pm.getId()).size()>0) {
                ////如果该菜单下有子菜单，则标记为折叠
                treeModel.setState("closed");
            }
            treeModel.setAttributes(pm);
            returnList.add(treeModel);
        }
        return returnList;
    }
    
    /**
     * 排序权限列表
     * @date 2015年12月29日
     * @param targetId
     * @param sourceId
     * @param point
     * @return
     */
    @RequestMapping(value = "/permission/orderBy", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyOrderByPermission(String targetId,String sourceId,String point){
        int num = authorityService.modifyOrderByPermission(targetId,sourceId,point);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
        }else{
            resultMap.put(SUCCESS, false);
        }
        AuditLogUtils.log(AuditLogConstants.MODEL_RESOURCE, AuditLogConstants.TYPE_ORDER,
                AuditLogConstants.RESULT_SUCCESS,"菜单资源重排序成功");
        return resultMap;
    }
    
  //添加权限
    @RequestMapping(value = "/permission/save", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> savePermission(PermissionItem PermissionItem){
        String id = PKGenarator.getId();
        PermissionItem.setId(id);
        int num = authorityService.savePermission(PermissionItem);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            resultMap.put("id", id);
            AuditLogUtils.log(AuditLogConstants.MODEL_RESOURCE, AuditLogConstants.TYPE_ADD, 
                    AuditLogConstants.RESULT_SUCCESS,"权限："+id+"添加成功");
        }else{
            resultMap.put(SUCCESS, false);
            AuditLogUtils.log(AuditLogConstants.MODEL_RESOURCE, AuditLogConstants.TYPE_ADD, 
                    AuditLogConstants.RESULT_FAIL,"权限："+id+"添加失败");
        }
        return resultMap;
    }
    
    
    //修改权限
    @RequestMapping(value = "/permission/modify", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyPermission(PermissionItem PermissionItem){
        int num = authorityService.modifyPermission(PermissionItem);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            resultMap.put("id", PermissionItem.getId());
            AuditLogUtils.log(AuditLogConstants.MODEL_RESOURCE, AuditLogConstants.TYPE_MODIFY, 
                    AuditLogConstants.RESULT_SUCCESS,"权限"+PermissionItem.getId()+"修改成功");
        }else{
            resultMap.put(SUCCESS, false);
            AuditLogUtils.log(AuditLogConstants.MODEL_RESOURCE, AuditLogConstants.TYPE_MODIFY, 
                    AuditLogConstants.RESULT_FAIL,"权限"+PermissionItem.getId()+"修改失败");
        }
        return resultMap;
    }
    
    
    //删除权限
    @RequestMapping(value = "/permission/remove", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removePermission(String id){
        int num = authorityService.removePermission(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num > 0) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_RESOURCE, AuditLogConstants.TYPE_DELETE, 
                    AuditLogConstants.RESULT_SUCCESS,"权限："+id+"删除成功");
        }else{
            resultMap.put(SUCCESS, false);
            AuditLogUtils.log(AuditLogConstants.MODEL_RESOURCE, AuditLogConstants.TYPE_DELETE, 
                    AuditLogConstants.RESULT_FAIL,"权限："+id+"删除失败");
        }
        return resultMap;
    }
    
    
    
    
  //添加角色
    @RequestMapping(value = "/role/save", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveRole(RoleItem RoleItem){
        int num = authorityService.saveRole(RoleItem);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_ROLE, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS, "角色："+RoleItem.getName()+",添加成功。");
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    //修改角色
    @RequestMapping(value = "/role/modify", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyRole(RoleItem RoleItem){
        int num = authorityService.modifyRole(RoleItem);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_ROLE, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS,"角色："+RoleItem.getName()+",修改成功。");
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    //删除角色
    @RequestMapping(value = "/role/remove", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeRole(String id){
        int num = authorityService.removeRole(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_ROLE, AuditLogConstants.TYPE_DELETE, AuditLogConstants.RESULT_SUCCESS);
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    //角色列表，分页查询
    @SuppressWarnings({ "unchecked"})
    @RequestMapping(value = "/role/getList", method = RequestMethod.POST)
    public @ResponseBody SearchResult<RoleItem> getRoleList(RoleItem RoleItem){
        List<RoleItem> list = authorityService.getRoleList(RoleItem);
        ///将查询结果封装为easyui自带的分页模式
        return (SearchResult<RoleItem>) toSearchResult(list);
    }
    
    @RequestMapping(value = "/role/getById", method = RequestMethod.POST)
    public @ResponseBody RoleItem getRoleById(String id){
        RoleItem RoleItem = new RoleItem();
        RoleItem.setId(id);
        List<RoleItem> list = authorityService.getRoleList(RoleItem);
        if (list.size()>0) {
            return list.get(0);
        }
        return null;
    }
    
    //根据父节点查询子节点权限列表
    @RequestMapping(value = "/role/getRolesByPid", method = RequestMethod.POST)
    public @ResponseBody List<TreeModel> getRoleListByPid(String pid){
        RoleItem RoleItem = new RoleItem();
        RoleItem.setPid(pid);
        List<RoleItem> list = authorityService.getRoleList(RoleItem);
        List<TreeModel> returnList = new ArrayList<TreeModel>();
        for (RoleItem rm : list) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(rm.getId());
            treeModel.setText(rm.getName());
            treeModel.setState("closed");
            treeModel.setAttributes(rm);
            returnList.add(treeModel);
        }
        return returnList;
    }
    
    //给角色添加权限
    @RequestMapping(value = "/role/addPermission", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> savePermission4Role(String roleId,String permissionId,String permissionChecked){
         int num = authorityService.savePermission4Role(roleId,permissionId,permissionChecked);
         Map<String, Object> resultMap = new HashMap<String, Object>();
         if (num > 0) {
             resultMap.put(SUCCESS, true);
             AuditLogUtils.log(AuditLogConstants.MODEL_ROLE, AuditLogConstants.TYPE_ADDPERMISSION, AuditLogConstants.RESULT_SUCCESS,"角色Id："+roleId+",成功添加了权限。");
         }else{
             resultMap.put(SUCCESS, false);
         }
         return resultMap;
    }
    
    //删除角色的权限
    @RequestMapping(value = "/role/removePermission", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removePermission4Role(String roleId){
         int num = authorityService.removePermission4Role(roleId);
         Map<String, Object> resultMap = new HashMap<String, Object>();
         if (num > 0) {
             resultMap.put(SUCCESS, true);
             AuditLogUtils.log(AuditLogConstants.MODEL_RESOURCE, AuditLogConstants.TYPE_DELETE, 
                     AuditLogConstants.RESULT_SUCCESS,"删除角色："+roleId+"成功");
         }else{
             resultMap.put(SUCCESS, false);
         }
         return resultMap;
    }
    
    //查询角色下的权限
    @RequestMapping(value = "/role/getPermission", method = RequestMethod.POST)
    public @ResponseBody List<TreeModel> getPermission4Role(String roleId,String pid){
        PermissionItem PermissionItem = new PermissionItem();
        PermissionItem.setPid(pid);
        List<PermissionItem> list = authorityService.getPermission4Role(roleId);
        //有权限的Id
        List<String> permissionIds =new ArrayList<String>();
        for (PermissionItem p : list) {
            permissionIds.add(p.getId());
        }
        //所有权限
        List<PermissionItem> permissionAll = authorityService.getPermissionList(PermissionItem);
        List<TreeModel> returnList = new ArrayList<TreeModel>();
        for (PermissionItem permission : permissionAll) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(permission.getId());
            treeModel.setText(permission.getLabel());
            if (authorityService.getPermissionListByPid(permission.getId()).size()>0) {
                treeModel.setState("closed");////closed表示该节点下面有子节点，此时继续加载子节点
            }
            treeModel.setAttributes(permission);
            if (permissionIds.contains(permission.getId())) {
                treeModel.setChecked(true);////checked表示该节点及子节点全部被选中，否则false
            }
            returnList.add(treeModel);
        }
        
        return returnList;
    }
    
    //给用户添加角色
    @RequestMapping(value = "/user/addRole", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveRole4User(String userId,String roleId){
         int num = authorityService.saveRole4User(userId,roleId);
         Map<String, Object> resultMap = new HashMap<String, Object>();
         if (num > 0) {
             resultMap.put(SUCCESS, true);
             AuditLogUtils.log(AuditLogConstants.MODEL_RESOURCE, AuditLogConstants.TYPE_ADD, 
                     AuditLogConstants.RESULT_SUCCESS,"为用户添加角色成功，用户："+userId+",角色："+roleId);
         }else{
             resultMap.put(SUCCESS, false);
         }
         return resultMap;
    }
    //删除用户下的角色
    @RequestMapping(value = "/user/removeRole", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeRole4User(String id){
         int num = authorityService.removeRole4User(id);
         Map<String, Object> resultMap = new HashMap<String, Object>();
         if (num > 0) {
             resultMap.put(SUCCESS, true);
             AuditLogUtils.log(AuditLogConstants.MODEL_RESOURCE, AuditLogConstants.TYPE_DELETE, 
                     AuditLogConstants.RESULT_SUCCESS,"删除用户："+id+"的角色");
         }else{
             resultMap.put(SUCCESS, false);
         }
         return resultMap;
    }
    //用户下的角色列表
    @RequestMapping(value = "/user/getRole", method = RequestMethod.POST)
    public @ResponseBody List<RoleItem> getRole4User(String userId){
        List<RoleItem> list = authorityService.getRole4User(userId);
        return list;
    }
    
    
    
    //查询角色下的用户
    @RequestMapping(value = "/user/getUserByRole", method = RequestMethod.POST)
    public @ResponseBody List<Map<String,Object>> getUserByRole(String roleCode){
        List<Map<String,Object>> list = authorityService.getUserByRole(roleCode);
        return list;
    }
    
    
    
    
}
