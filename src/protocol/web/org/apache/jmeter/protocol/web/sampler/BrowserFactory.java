package org.apache.jmeter.protocol.web.sampler;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * This is responsible for accessing (and unsetting) a WebDriver browser instance per thread.
 */
public class BrowserFactory {
    /**
     * Each thread will reference their WebDriver (browser) instance via this ThreadLocal instance.  This is
     * initialised in the {@see #threadStarted()} and quit & unset in {@see #threadFinished()}.
     */
    private static final ThreadLocal<WebDriver> BROWSERS = new ThreadLocal<WebDriver>();

    /**
     * Each thread will store its own {@see Proxy} instance.  Each thread is initialised with a Proxy that is
     * configured to autodetect.
     */
    private static final ThreadLocal<Proxy> PROXIES = new ThreadLocal<Proxy>() {
        @Override
        protected Proxy initialValue() {
            final Proxy proxy = new Proxy();
            proxy.setAutodetect(true);
            return proxy;
        }
    };

    private static final BrowserFactory INSTANCE = new BrowserFactory();

    public static BrowserFactory getInstance() {
        return INSTANCE;
    }

    private BrowserFactory() {}

    /**
     * Call this method to get a WebDriver (browser) for the current thread.  The returned browser instance will be
     * stored and returned on subsequent calls until {@see #clearBrowser()} is called.
     *
     * @return a thread specific WebDriver instance.
     */
    public WebDriver getBrowser() {
        if(BROWSERS.get() == null) {
            final DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(CapabilityType.PROXY, PROXIES.get());
            BROWSERS.set(new FirefoxDriver(capabilities));
        }

        return BROWSERS.get();
    }

    /**
     * Removes any WebDriver instance associated with the calling thread and quits the running browser instance.
     */
    public void clearBrowser() {
        if(BROWSERS.get() != null) {
            BROWSERS.get().quit();
            BROWSERS.remove();
        }
    }

    /**
     * This allows the HTTP, HTTPS and FTP proxies to be manually configured for each browser instance that will be
     * created by this class.
     *
     * @param httpProxy is the http proxy host and port, eg. proxy-host.com:3128
     * @param httpsProxy is the https proxy host and port, eg. proxy-host.com:3128
     * @param ftpProxy is the ftp proxy host and port, eg. proxy-host.com:3128
     */
    public void setManualProxy(String httpProxy, String httpsProxy, String ftpProxy) {
        final Proxy proxy = new Proxy();
        proxy.setHttpProxy(httpProxy);
        proxy.setSslProxy(httpsProxy);
        proxy.setFtpProxy(ftpProxy);
        proxy.setProxyType(Proxy.ProxyType.MANUAL);
        PROXIES.set(proxy);
    }

    /**
     * This will not use a proxy and expects a direct connection to the internet.
     */
    public void setDirectProxy() {
        final Proxy proxy = new Proxy();
        proxy.setProxyType(Proxy.ProxyType.DIRECT);
        PROXIES.set(proxy);
    }

    /**
     * If the proxy can be configured using a PAC file at a URL, set this value to the location of this PAC file.
     *
     * @param pacUrl is the full path, eg. http://proxy-host.com/proxy.pac
     */
    public void setUrlProxy(String pacUrl) {
        final Proxy proxy = new Proxy();
        proxy.setProxyAutoconfigUrl(pacUrl);
        proxy.setProxyType(Proxy.ProxyType.PAC);
        PROXIES.set(proxy);
    }

    /**
     * This is the default proxy configuration, which will attempt to autodetect the proxy settings for the network.
     */
    public void setAutodetectProxy() {
        final Proxy proxy = new Proxy();
        proxy.setAutodetect(true);
        PROXIES.set(proxy);
    }
}
