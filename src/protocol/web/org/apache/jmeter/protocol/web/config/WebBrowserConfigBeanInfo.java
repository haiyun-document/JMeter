package org.apache.jmeter.protocol.web.config;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.beans.PropertyDescriptor;

public class WebBrowserConfigBeanInfo extends BeanInfoSupport {
    private static final Logger LOGGER = LoggingManager.getLoggerForClass();
    private static final String CACHE_SETTINGS = "cacheSettings";
    private static final String ITERATION_LOOP_SETTINGS = "iterationLoopSettings";
    static final String DO_NOT_CLEAR = "retain its cookies and cache";
    static final String CLEAR_COOKIES = "clear its cookies";
    static final String CLEAR_ALL = "clear all cookies and cache (creates a new browser)";
    static final String RUN_SETTINGS = "runSettings";
    static final String PROXY_SETTINGS = "proxySettings";
    static final String PAC_URL = "pacUrl";
    static final String HTTP_PROXY = "httpProxy";
    static final String HTTPS_PROXY = "httpsProxy";
    static final String FTP_PROXY = "ftpProxy";
    static final String PROXY_AUTO_DETECT = "be automatically configured";
    static final String PROXY_DIRECT = "not be used";
    static final String PROXY_PAC = "be configured using a PAC URL (below)";
    static final String PROXY_MANUAL = "be manually configured (below)";

    public WebBrowserConfigBeanInfo() {
        super(WebBrowserConfig.class);

        PropertyDescriptor p = null;

        // configuration per test run (ie. configured only once)
        createPropertyGroup(RUN_SETTINGS, new String[] {PROXY_SETTINGS, PAC_URL, HTTP_PROXY, HTTPS_PROXY, FTP_PROXY});
        // configuration per iteration (ie. at the start of each loop)
        createPropertyGroup(ITERATION_LOOP_SETTINGS, new String[] {CACHE_SETTINGS});

        // proxy configuration
        p = property(PROXY_SETTINGS);
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, PROXY_AUTO_DETECT);
        p.setValue(NOT_OTHER, Boolean.TRUE);
        p.setValue(NOT_EXPRESSION, Boolean.TRUE);
        p.setValue(TAGS, new String[] {PROXY_AUTO_DETECT, PROXY_DIRECT, PROXY_PAC, PROXY_MANUAL});
        p = property(PAC_URL);
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, "");
        p.setValue(NOT_EXPRESSION, Boolean.TRUE);
        p = property(HTTP_PROXY);
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, "");
        p.setValue(NOT_EXPRESSION, Boolean.TRUE);
        p = property(HTTPS_PROXY);
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, "");
        p.setValue(NOT_EXPRESSION, Boolean.TRUE);
        p = property(FTP_PROXY);
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, "");
        p.setValue(NOT_EXPRESSION, Boolean.TRUE);

        // cache configuration
        p = property(CACHE_SETTINGS);
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, DO_NOT_CLEAR);
        p.setValue(NOT_OTHER, Boolean.TRUE);
        p.setValue(NOT_EXPRESSION, Boolean.TRUE);
        p.setValue(TAGS, new String[] {DO_NOT_CLEAR, CLEAR_COOKIES, CLEAR_ALL});
    }
}
