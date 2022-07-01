package com.sunfujun.tools.io.core.call.file;

/**
 * File path
 * 文件路径对象
 */
public class FilePath {

    /**
     * Root
     */
    private String root ;
    /**
     * Tenant code
     */
    private String tenantCode ;
    /**
     * Path
     */
    private String path ;

    /**
     * Gets root *
     *
     * @return the root
     */
    public String getRoot() {
        return root;
    }

    /**
     * Sets root *
     *
     * @param root root
     */
    public void setRoot(String root) {
        this.root = root;
    }

    /**
     * Gets tenant code *
     *
     * @return the tenant code
     */
    public String getTenantCode() {
        return tenantCode;
    }

    /**
     * Sets tenant code *
     *
     * @param tenantCode tenant code
     */
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    /**
     * Gets path *
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets path *
     *
     * @param path path
     */
    public void setPath(String path) {
        this.path = path;
    }
}
