package org.apache.jmeter.protocol.web.config;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.protocol.web.util.BrowserFactory;
import org.apache.jmeter.protocol.web.util.ProxyFactory;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestListener;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.openqa.selenium.Proxy;

public class WebBrowserConfig extends ConfigTestElement implements TestBean, TestListener, LoopIterationListener {
    private static final Logger LOGGER = LoggingManager.getLoggerForClass();

    private transient String cacheSettings;
    private transient String proxySettings;
    private transient String pacUrl;
    private transient String httpProxy;
    private transient String httpsProxy;
    private transient String ftpProxy;

    private final ProxyFactory proxyFactory;

    public WebBrowserConfig() {
        this(new ProxyFactory());
    }

    WebBrowserConfig(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
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
    public void testIterationStart(LoopIterationEvent event) {
    }

    @Override
    public void testStarted() {
        Proxy proxy = null;
        if(WebBrowserConfigBeanInfo.PROXY_PAC.equals(proxySettings)) {
            proxy = proxyFactory.getUrlProxy(pacUrl);
        }
        else if(WebBrowserConfigBeanInfo.PROXY_MANUAL.equals(proxySettings)) {
            proxy = proxyFactory.getManualProxy(httpProxy, httpsProxy, ftpProxy);
        }
        else if(WebBrowserConfigBeanInfo.PROXY_DIRECT.equals(proxySettings)) {
            proxy = proxyFactory.getDirectProxy();
        }
        else {
            proxy = proxyFactory.getAutodetectProxy();
        }
        BrowserFactory.getInstance().setProxy(proxy);
    }

    @Override
    public void testStarted(String host) {
        testStarted();
    }

    @Override
    public void testEnded() {
    }

    @Override
    public void testEnded(String host) {
        testEnded();
    }

    @Override
    public void iterationStart(LoopIterationEvent iterEvent) {
        if(WebBrowserConfigBeanInfo.CLEAR_ALL.equals(cacheSettings)) {
            BrowserFactory.getInstance().clearBrowser();
        } else if(WebBrowserConfigBeanInfo.CLEAR_COOKIES.equals(cacheSettings)) {
            BrowserFactory.getInstance().getBrowser().manage().deleteAllCookies();
        }
    }
}
