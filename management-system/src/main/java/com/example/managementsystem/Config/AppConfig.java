package com.example.managementsystem.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app")
@Component
public class AppConfig {

    /**
     * 应用名称
     */
    private String appName;
    /**
     * 是否允许跨域
     */
    private Boolean allowCrossDomainAccess;
    /**
     * 是否允许生成API文档
     */
    private Boolean allowGenerateApi;

    /**
     * 客服电话
     */
    private String customerServicesMobile;

    /**
     * 环境：dev:开发;test:测试;prod:生产
     */
    private String env;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Boolean getAllowCrossDomainAccess() {
        return allowCrossDomainAccess;
    }

    public void setAllowCrossDomainAccess(Boolean allowCrossDomainAccess) {
        this.allowCrossDomainAccess = allowCrossDomainAccess;
    }

    public Boolean getAllowGenerateApi() {
        return allowGenerateApi;
    }

    public void setAllowGenerateApi(Boolean allowGenerateApi) {
        this.allowGenerateApi = allowGenerateApi;
    }

    public String getCustomerServicesMobile() {
        return customerServicesMobile;
    }

    public void setCustomerServicesMobile(String customerServicesMobile) {
        this.customerServicesMobile = customerServicesMobile;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

}
