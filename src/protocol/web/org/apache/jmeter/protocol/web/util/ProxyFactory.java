package org.apache.jmeter.protocol.web.util;

import org.openqa.selenium.Proxy;

/**
 * This is used to create {@Proxy} objects to be used when constructing {@WebDriver} instances.
 */
public class ProxyFactory {

    /**
     * This returns a {@see Proxy} with HTTP, HTTPS and FTP hosts and ports configured as specified.
     *
     * @param httpProxy is the http proxy host and port, eg. proxy-host.com:3128
     * @param httpsProxy is the https proxy host and port, eg. proxy-host.com:3128
     * @param ftpProxy is the ftp proxy host and port, eg. proxy-host.com:3128
     *
     * @returns a proxy object with the hosts manually specified.
     */
    public Proxy getManualProxy(String httpProxy, String httpsProxy, String ftpProxy) {
        final Proxy proxy = new Proxy();
        proxy.setHttpProxy(httpProxy);
        proxy.setSslProxy(httpsProxy);
        proxy.setFtpProxy(ftpProxy);
        proxy.setProxyType(Proxy.ProxyType.MANUAL);
        return proxy;
    }

    /**
     * This will not use a proxy and expects a direct connection to the internet.
     *
     * @returns a proxy object that does not use proxies.
     */
    public Proxy getDirectProxy() {
        final Proxy proxy = new Proxy();
        proxy.setProxyType(Proxy.ProxyType.DIRECT);
        return proxy;
    }

    /**
     * If the proxy can be configured using a PAC file at a URL, set this value to the location of this PAC file.
     *
     * @param pacUrl is the full path, eg. http://proxy-host.com/proxy.pac
     *
     * @returns a proxy object with its proxies configured automatically using a PAC file.
     */
    public Proxy getUrlProxy(String pacUrl) {
        final Proxy proxy = new Proxy();
        proxy.setProxyAutoconfigUrl(pacUrl);
        proxy.setProxyType(Proxy.ProxyType.PAC);
        return proxy;
    }

    /**
     * This is the default proxy configuration, which will attempt to autodetect the proxy settings for the network.
     *
     * @returns a proxy object which will try to automatically detect the proxy settings.
     */
    public Proxy getAutodetectProxy() {
        final Proxy proxy = new Proxy();
        proxy.setAutodetect(true);
        return proxy;
    }
}
