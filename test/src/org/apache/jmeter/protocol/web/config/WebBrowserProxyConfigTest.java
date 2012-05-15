package org.apache.jmeter.protocol.web.config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.jmeter.protocol.web.util.BrowserFactory;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Proxy.ProxyType;

public class WebBrowserProxyConfigTest {
    private WebBrowserProxyConfig proxyConfig;
    private BrowserFactory browserFactory;

    @Before
	public void setUp() {
		proxyConfig = new WebBrowserProxyConfig();
		browserFactory = BrowserFactory.getInstance();
	}
	
	@Test
    public void shouldBeAbleToReadSamePropertiesFromConfigAfterDeserialisation() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArray);

        final String proxySetting = "proxy setting value";
        final String pacUrl = "pac url value";
        final String httpProxy = "http proxy value";
        final String httpsProxy = "https proxy value";
        final String ftpProxy = "ftp proxy value";
        
        proxyConfig.setProxySettings(proxySetting);
        proxyConfig.setPacUrl(pacUrl);
        proxyConfig.setHttpProxy(httpProxy);
        proxyConfig.setHttpsProxy(httpsProxy);
        proxyConfig.setFtpProxy(ftpProxy);

        outputStream.writeObject(proxyConfig);
        outputStream.flush();

        ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(byteArray.toByteArray()));
        WebBrowserProxyConfig deserialisedProxyConfig = (WebBrowserProxyConfig)inputStream.readObject();

        assertThat(deserialisedProxyConfig.getProxySettings(), is(proxySetting));
        assertThat(deserialisedProxyConfig.getPacUrl(), is(pacUrl));
        assertThat(deserialisedProxyConfig.getHttpProxy(), is(httpProxy));
        assertThat(deserialisedProxyConfig.getHttpsProxy(), is(httpsProxy));
        assertThat(deserialisedProxyConfig.getFtpProxy(), is(ftpProxy));
    }
	
	@Test
	public void shouldSetPacUrlProxyOnBrowserFactoryWhenPacUrlIsSpecified() throws Exception {
		proxyConfig.setProxySettings(WebBrowserProxyConfigBeanInfo.PROXY_PAC);
		proxyConfig.setPacUrl("some url");
		proxyConfig.testStarted();
		assertThat(browserFactory.getProxy().getProxyType(), is(ProxyType.PAC));
	}
	
	@Test
	public void shouldSetManualProxyOnBrowserFactoryWhenManualIsSpecified() throws Exception {
		proxyConfig.setProxySettings(WebBrowserProxyConfigBeanInfo.PROXY_MANUAL);
		proxyConfig.setHttpProxy("host:port");
		proxyConfig.setHttpsProxy("host:port");
		proxyConfig.setFtpProxy("host:port");
		proxyConfig.testStarted();
		assertThat(browserFactory.getProxy().getProxyType(), is(ProxyType.MANUAL));
	}
	
	@Test
	public void shouldSetDirectProxyOnBrowserFactoryWhenDirectIsSpecified() throws Exception {
		proxyConfig.setProxySettings(WebBrowserProxyConfigBeanInfo.PROXY_DIRECT);
		proxyConfig.testStarted();
		assertThat(browserFactory.getProxy().getProxyType(), is(ProxyType.DIRECT));
	}
	
	@Test
	public void shouldSetAutodetectProxyOnBrowserFactoryWhenAutodetectIsSpecified() throws Exception {
		proxyConfig.setProxySettings(WebBrowserProxyConfigBeanInfo.PROXY_AUTO_DETECT);
		proxyConfig.testStarted();
		assertThat(browserFactory.getProxy().getProxyType(), is(ProxyType.AUTODETECT));
	}
	
	@Test
	public void shouldFallbackToAutodetectProxy() throws Exception {
		proxyConfig.testStarted();
		assertThat(browserFactory.getProxy().getProxyType(), is(ProxyType.AUTODETECT));
	}
}
