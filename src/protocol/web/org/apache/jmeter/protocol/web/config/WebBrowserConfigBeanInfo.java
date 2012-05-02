package org.apache.jmeter.protocol.web.config;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.beans.PropertyDescriptor;

public class WebBrowserConfigBeanInfo extends BeanInfoSupport {
    private static final Logger LOGGER = LoggingManager.getLoggerForClass();
    public static final String CACHE_SETTINGS = "cacheSettings";
    public static final String ITERATION_LOOP_SETTINGS = "iterationLoopSettings";
    public static final String DO_NOT_CLEAR = "retain its cookies and cache";
    public static final String CLEAR_ALL = "clear all cookies and cache (creates a new browser)";

    static {

    }

    public WebBrowserConfigBeanInfo() {
        super(WebBrowserConfig.class);

        createPropertyGroup(ITERATION_LOOP_SETTINGS, new String[] {CACHE_SETTINGS});

        PropertyDescriptor p = property(CACHE_SETTINGS);
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, DO_NOT_CLEAR);
        p.setValue(NOT_OTHER, Boolean.TRUE);
        p.setValue(NOT_EXPRESSION, Boolean.TRUE);
        p.setValue(TAGS, new String[] {DO_NOT_CLEAR, CLEAR_ALL});
    }
}
