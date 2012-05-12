package org.apache.jmeter.protocol.web.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.Vector;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BrowserFactory.class)
public class BrowserFactoryTest {
    /**
     * Used to store the browsers created by {#BrowserCreator}.
     */
    private final List<WebDriver> browsers = new Vector<WebDriver>();

    /**
     * The run method will access a browser from the factory and add it to the {#browsers} list.
     */
    private class BrowserCreator implements Runnable {

        @Override
        public void run() {
            browsers.add(BrowserFactoryTest.this.factory.getBrowser());
        }
    };

    private BrowserFactory factory;

    @Before
    public void initFactory() {
        this.factory = BrowserFactory.getInstance();
    }

    @After
    public void clearBrowsers() {
        factory.clearBrowser();
        for(WebDriver browser: browsers) {
            browser.quit();
        }
        browsers.clear();
    }

    @Test
    public void shouldReturnTheSameBrowserWhenSubsequentGetBrowserIsInvoked() throws Exception {
        FirefoxDriver mockBrowser = mock(FirefoxDriver.class);
        whenNew(FirefoxDriver.class).withNoArguments().thenReturn(mockBrowser);

        WebDriver firstBrowser = factory.getBrowser();
        WebDriver secondBrowser = factory.getBrowser();
        assertThat(firstBrowser, is(sameInstance(secondBrowser)));

        verifyNew(FirefoxDriver.class);
    }

    @Test
    public void shouldReturnNewBrowserWhenClearBrowserIsInvoked() throws Exception {
        FirefoxDriver firstBrowser = mock(FirefoxDriver.class);
        FirefoxDriver secondBrowser = mock(FirefoxDriver.class);
        whenNew(FirefoxDriver.class).withNoArguments().thenReturn(firstBrowser, secondBrowser);

        WebDriver beforeReset = factory.getBrowser();
        factory.clearBrowser();
        WebDriver afterReset = factory.getBrowser();

        assertThat(afterReset, is(not(sameInstance(beforeReset))));

        verifyNew(FirefoxDriver.class);
    }

    @Test
    public void shouldReturnDifferentBrowserWhenCalledFromSeparateThreads() throws Exception {
        FirefoxDriver firstBrowser = mock(FirefoxDriver.class);
        FirefoxDriver secondBrowser = mock(FirefoxDriver.class);
        whenNew(FirefoxDriver.class).withNoArguments().thenReturn(firstBrowser, secondBrowser);

        Thread firstThread = new Thread(this.new BrowserCreator());
        Thread secondThread = new Thread(this.new BrowserCreator());

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();

        assertThat(browsers.size(), is(2));
        assertThat(browsers.get(0), is(not(sameInstance(browsers.get(1)))));

        verifyNew(FirefoxDriver.class);
    }

}
