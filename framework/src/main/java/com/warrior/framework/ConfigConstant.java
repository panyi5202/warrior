package com.warrior.framework;

/**
 * 配置项常量
 * @author panyi on 18-7-17.
 */
public interface ConfigConstant {
    /**
     * 配置文件名称
     */
    String CONFIG_FILE = "warrior.properties";

    String JDBC_DRIVER = "warrior.framework.jdbc.driver";
    String JDBC_URL = "warrior.framework.jdbc.url";
    String JDBC_USERNAME = "warrior.framework.jdbc.username";
    String JDBC_PASSWORD = "warrior.framework.jdbc.password";

    String APP_BASE_PACKAGE = "warrior.framework.app.base_package";
    String APP_JSP_PATH = "warrior.framework.app.jsp_path";
    String APP_STATIC_PATH = "warrior.framework.app.static_path";

}
