package org.apache.jmeter.protocol.web.sampler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserFactory {
    /**
     * Each thread will reference their WebDriver (browser) instance via this ThreadLocal instance.  This is
     * initialised in the {@see #threadStarted()} and quit & unset in {@see #threadFinished()}.
     */
    private static final ThreadLocal<WebDriver> BROWSERS = new ThreadLocal<WebDriver>();

    private static final BrowserFactory INSTANCE = new BrowserFactory();

    public static BrowserFactory getInstance() {
        return INSTANCE;
    }

    private BrowserFactory() {}

    public WebDriver getBrowser() {
        if(BROWSERS.get() == null) {
            BROWSERS.set(new FirefoxDriver());
        }

        return BROWSERS.get();
    }

    public void clearBrowser() {
        if(BROWSERS.get() != null) {
            BROWSERS.get().quit();
            BROWSERS.remove();
        }
    }
}
