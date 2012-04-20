package org.apache.jmeter.protocol.web.sampler;

import java.io.PrintStream;
import java.util.Properties;

import org.apache.bsf.BSFEngine;
import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.ThreadListener;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * A Sampler that makes HTTP requests using a real browser (via. Selenium/WebDriver).  It currently 
 * provides a scripting mechanism via. Javascript to control the browser instance.
 */
public class WebSampler extends AbstractSampler implements ThreadListener {

    private static final String SCRIPT = "WebSampler.script";

	private static final Logger LOGGER = LoggingManager.getLoggerForClass();
    
	private static final long serialVersionUID = 234L;
    
    private transient WebDriver browser;
    
	@Override
	public SampleResult sample(Entry e) {
        LOGGER.debug("sampling web");

        // BSF Code copied liberally from BSFSampler
        final BSFEngine bsfEngine;
        final BSFManager mgr = new BSFManager();
        
        final SampleResult res = new SampleResult();
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
            initManager(mgr);
            bsfEngine = mgr.loadScriptingEngine("javascript");
            
            res.sampleStart();
            bsfEngine.exec("script", 0, 0, getScript());
            res.sampleEnd();

            res.setResponseData(browser.getPageSource().getBytes());
            LOGGER.debug("Page title is: " + browser.getTitle());
        } catch (Exception ex) {
            res.setResponseMessage(ex.toString());
            res.setResponseCode("000");
            if(ex.getMessage() != null) {
                res.setResponseData(ex.getMessage().getBytes());
            }
            res.setSuccessful(false);
        }

        return res;
	}

	private void initManager(BSFManager mgr) throws BSFException{
        final String label = getName();

        // Use actual class name for log
        mgr.declareBean("log", LOGGER, Logger.class); // $NON-NLS-1$
        mgr.declareBean("Label",label, String.class); // $NON-NLS-1$
        // Add variables for access to context and variables
        JMeterContext jmctx = JMeterContextService.getContext();
        JMeterVariables vars = jmctx.getVariables();
        Properties props = JMeterUtils.getJMeterProperties();

        mgr.declareBean("ctx", jmctx, jmctx.getClass()); // $NON-NLS-1$
        mgr.declareBean("vars", vars, vars.getClass()); // $NON-NLS-1$
        mgr.declareBean("props", props, props.getClass()); // $NON-NLS-1$
        mgr.declareBean("browser", browser, WebDriver.class);
        // For use in debugging:
        mgr.declareBean("OUT", System.out, PrintStream.class); // $NON-NLS-1$

        // Most subclasses will need these:
        Sampler sampler = jmctx.getCurrentSampler();
        mgr.declareBean("sampler", sampler, Sampler.class);
        SampleResult prev = jmctx.getPreviousResult();
        mgr.declareBean("prev", prev, SampleResult.class);
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

	public String getScript() {
		return getPropertyAsString(SCRIPT);
	}
	
	public void setScript(String script) {
		setProperty(SCRIPT, script);
	}
}
