package com.lip.core.model;

public class SysConfigItem {
    private String id;
    private String displayName;
    private String code;
    private String description;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @date 2016年1月8日
     * @param sysConfigItem
     * @return
     */
    public int modifySysConfig(SysConfigItem sysConfigItem) {
        // TODO Auto-generated method stub
        return 0;
    }
}
