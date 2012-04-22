package org.apache.jmeter.protocol.web.sampler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ChromeDriverFactoryTest {

	private static ChromeDriverFactory browserFactory;
	
	@BeforeClass
	public static void initialise() throws Exception {
		browserFactory = new ChromeDriverFactory();
		browserFactory.startup();
	}
	
	@AfterClass
	public static void finalise() throws Exception {
		browserFactory.shutdown();
	}
	
	
	@Test
	public void shouldBeAbleToCreateBrowser() throws Exception {
		WebDriver browser = null;
		try {
			browser = browserFactory.newBrowser();
			assertThat(browser, is(not(nullValue())));
		}
		finally {
			if(browser != null) {
				browser.close();
			}
		}
	}
}
