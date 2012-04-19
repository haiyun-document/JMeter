package org.apache.jmeter.protocol.web.sampler;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.ThreadListener;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * A Sampler that makes HTTP requests using a real browser (via. Selenium/WebDriver).
 */
public class WebSampler extends AbstractSampler implements ThreadListener {

	private static final long serialVersionUID = 234L;
    
    private static final Logger LOGGER = LoggingManager.getLoggerForClass();
    
    private transient WebDriver browser;

	@Override
	public SampleResult sample(Entry e) {
        LOGGER.debug("sampling web");

        SampleResult res = new SampleResult();
        res.setSampleLabel(getName());
        res.setSamplerData(toString());
        res.setDataType(SampleResult.TEXT);
        res.setContentType("text/plain"); // $NON-NLS-1$
        res.setDataEncoding("UTF-8");

        // Assume we will be successful
        res.setSuccessful(true);
        res.setResponseMessageOK();
        res.setResponseCodeOK();

        try {
            res.sampleStart();
            browser.get("http://www.google.com");
            res.sampleEnd();

            res.setResponseData(browser.getPageSource().getBytes());
            LOGGER.debug("Page title is: " + browser.getTitle());
        } catch (Exception ex) {
            res.setResponseMessage(ex.toString());
            res.setResponseCode("000");
            res.setResponseData(ex.getMessage().getBytes());
            res.setSuccessful(false);
        }

        // TODO: process warnings? Set Code and Message to success?
        return res;
	}

	@Override
	public void threadStarted() {
		browser = new FirefoxDriver();
	}

	@Override
	public void threadFinished() {
		if(browser != null) {
			browser.close();
		}
		
	}

}
