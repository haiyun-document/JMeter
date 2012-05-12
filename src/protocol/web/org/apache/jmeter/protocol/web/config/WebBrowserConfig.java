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
    private transient String proxySettings;
    private transient String pacUrl;
    private transient String httpProxy;
    private transient String httpsProxy;
    private transient String ftpProxy;

    public WebBrowserConfig() {
        LOGGER.info("constructed web config");
    }

    public String getCacheSettings() {
        return cacheSettings;
    }

    public void setCacheSettings(String cacheSettings) {
        this.cacheSettings = cacheSettings;
    }

    public String getProxySettings() {
        return proxySettings;
    }

    public void setProxySettings(String proxySettings) {
        this.proxySettings = proxySettings;
    }

    public String getPacUrl() {
        return pacUrl;
    }

    public void setPacUrl(String pacUrl) {
        this.pacUrl = pacUrl;
    }

    public String getHttpProxy() {
        return httpProxy;
    }

    public void setHttpProxy(String httpProxy) {
        this.httpProxy = httpProxy;
    }

    public String getHttpsProxy() {
        return httpsProxy;
    }

    public void setHttpsProxy(String httpsProxy) {
        this.httpsProxy = httpsProxy;
    }

    public String getFtpProxy() {
        return ftpProxy;
    }

    public void setFtpProxy(String ftpProxy) {
        this.ftpProxy = ftpProxy;
    }

    @Override
    public void iterationStart(LoopIterationEvent iterEvent) {
        if(WebBrowserConfigBeanInfo.CLEAR_ALL.equals(cacheSettings)) {
            LOGGER.info("resetting browser");
            BrowserFactory.getInstance().clearBrowser();
        } else if(WebBrowserConfigBeanInfo.CLEAR_COOKIES.equals(cacheSettings)) {
            LOGGER.info("clearing cookies");
            BrowserFactory.getInstance().getBrowser().manage().deleteAllCookies();
        }
    }

}
