package org.apache.jmeter.protocol.web.config;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.protocol.web.util.BrowserFactory;
import org.apache.jmeter.protocol.web.util.ProxyFactory;
import org.openqa.selenium.Proxy;

public class WebBrowserProxyConfig extends ConfigTestElement {
		
	private static final String FTP_PROXY = "WebBrowserProxyConfig.ftpProxy";

	private static final String HTTPS_PROXY = "WebBrowserProxyConfig.httpsProxy";

	private static final String HTTP_PROXY = "WebBrowserProxyConfig.httpProxy";

	private static final String PAC_URL = "WebBrowserProxyConfig.pacUrl";

	private static final String PROXY_SETTINGS = "WebBrowserProxyConfig.proxySettings";
	
	private static final long serialVersionUID = -7689856154939113519L;
	
	public void setProxySettings(String proxySettings) {
        setProperty(PROXY_SETTINGS, proxySettings);
    }

    public String getProxySettings() {
        return getPropertyAsString(PROXY_SETTINGS);
    }
    
    public String getPacUrl() {
        return getPropertyAsString(PAC_URL);
    }

    public void setPacUrl(String pacUrl) {
        setProperty(PAC_URL, pacUrl);
    }

    public String getHttpProxy() {
        return getPropertyAsString(HTTP_PROXY);
    }

    public void setHttpProxy(String httpProxy) {
        setProperty(HTTP_PROXY, httpProxy);
    }

    public String getHttpsProxy() {
        return getPropertyAsString(HTTPS_PROXY);
    }

    public void setHttpsProxy(String httpsProxy) {
        setProperty(HTTPS_PROXY, httpsProxy);
    }

    public String getFtpProxy() {
        return getPropertyAsString(FTP_PROXY);
    }

    public void setFtpProxy(String ftpProxy) {
        setProperty(FTP_PROXY, ftpProxy);
    }

	public void testStarted() {
        Proxy proxy = null;
        if(WebBrowserConfigBeanInfo.PROXY_PAC.equals(getProxySettings())) {
            proxy = ProxyFactory.getInstance().getUrlProxy(getPacUrl());
        }
        else if(WebBrowserConfigBeanInfo.PROXY_MANUAL.equals(getProxySettings())) {
            proxy = ProxyFactory.getInstance().getManualProxy(getHttpProxy(), getHttpsProxy(), getFtpProxy());
        }
        else if(WebBrowserConfigBeanInfo.PROXY_DIRECT.equals(getProxySettings())) {
            proxy = ProxyFactory.getInstance().getDirectProxy();
        }
        else {
            proxy = ProxyFactory.getInstance().getAutodetectProxy();
        }
        BrowserFactory.getInstance().setProxy(proxy);
	}

}
