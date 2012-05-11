package org.apache.jmeter.protocol.web.sampler;

import org.apache.jmeter.protocol.web.util.ProxyFactory;
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
     * Each thread will store its own {@see Proxy} instance.
     */
    private final ThreadLocal<Proxy> PROXIES = new ThreadLocal<Proxy>();

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
            WebDriver browser = null;
            if(PROXIES.get() != null) {
                final DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability(CapabilityType.PROXY, PROXIES.get());
                browser = new FirefoxDriver(capabilities);
            }
            else {
                browser = new FirefoxDriver();
            }
            BROWSERS.set(browser);
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
     * Use this to set the proxy to use when getting/creating new WebDriver instances {#getBrowser} for each thread.  If the browser
     * has already been accessed (ie. created and set) on a thread, first call {#clearBrowser} then call this method
     * to make sure that the proxy will be used when {#getBrowser} is next invoked.
     *
     * @param proxy is the proxy to use when {#getBrowser} is invoked.
     */
    public void setProxy(Proxy proxy) {
        PROXIES.set(proxy);
    }
}
