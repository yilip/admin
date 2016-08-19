package com.lip.core.service;


import com.lip.core.model.SysConfigItem;

import java.util.List;

public interface SysConfigService {

    SysConfigItem fetchSysConfig(String displayName);

    /**
     * @date 2016年1月8日
     * @param id
     * @return
     */
    int removeSysConfig(String id);

    /**
     * @date 2016年1月8日
     * @return
     */
    int modifySysConfig(SysConfigItem sysConfigItem);

    /**
     * @date 2016年1月8日
     * @return
     */
    int saveSysConfig(SysConfigItem sysConfigItem);

    /**
     * @date 2016年1月8日
     * @param sysConfigItem
     * @return
     */
    List<SysConfigItem> getSysConfigList(SysConfigItem sysConfigItem);

}
