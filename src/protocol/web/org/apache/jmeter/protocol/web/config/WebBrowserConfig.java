package org.apache.jmeter.protocol.web.config;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.protocol.web.sampler.BrowserFactory;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class WebBrowserConfig extends ConfigTestElement implements TestBean, LoopIterationListener {
    private static final Logger LOGGER = LoggingManager.getLoggerForClass();

    private transient String cacheSettings;

    public WebBrowserConfig() {
        LOGGER.info("constructed web config");
    }

    public String getCacheSettings() {
        return cacheSettings;
    }

    public void setCacheSettings(String cacheSettings) {
        this.cacheSettings = cacheSettings;
    }

    @Override
    public void iterationStart(LoopIterationEvent iterEvent) {
        if(WebBrowserConfigBeanInfo.CLEAR_ALL.equals(cacheSettings)) {
            LOGGER.info("resetting browser!");
            BrowserFactory.getInstance().clearBrowser();
        }
    }
}
