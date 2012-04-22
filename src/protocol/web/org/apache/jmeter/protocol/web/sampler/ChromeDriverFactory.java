package org.apache.jmeter.protocol.web.sampler;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ChromeDriverFactory {
	
	private ChromeDriverService service;
	
	public ChromeDriverFactory() {
		service = new ChromeDriverService.Builder()
        .usingChromeDriverExecutable(new File("/Users/cpl/Downloads/chromedriver_mac"))
        .usingAnyFreePort()
        .build();
	}
	
	public void startup() throws Exception {
		service.start();
	}
	
	public void shutdown() throws Exception {
		service.stop();
	}
	
	public WebDriver newBrowser() {
		return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
	}
}
