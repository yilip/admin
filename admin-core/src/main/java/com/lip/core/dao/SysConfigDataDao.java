package com.lip.core.dao;


import com.lip.core.model.SysConfigItem;

import java.util.List;

public interface SysConfigDataDao {

    List<SysConfigItem> fetchAllConfig();

    /**
     * @date 2016年1月8日
     * @param id
     * @return
     */
    int removeSysConfig(String id);

    /**
     * @date 2016年1月8日
     * @param sysConfigItem
     * @return
     */
    int saveSysConfig(SysConfigItem sysConfigItem);

    /**
     * @date 2016年1月8日
     * @return
     */
    List<SysConfigItem> getSysConfigList(SysConfigItem item);

    /**
     * @date 2016年1月8日
     * @param sysConfigItem
     * @return
     */
    int modifySysConfig(SysConfigItem sysConfigItem);

}
