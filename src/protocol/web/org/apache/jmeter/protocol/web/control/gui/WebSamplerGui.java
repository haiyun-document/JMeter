package org.apache.jmeter.protocol.web.control.gui;

import org.apache.jmeter.protocol.web.sampler.WebSampler;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

public class WebSamplerGui extends AbstractSamplerGui {

	private static final long serialVersionUID = -3484685528176139410L;

	@Override
	public String getLabelResource() {
		return "web";
	}

	@Override
	public TestElement createTestElement() {
		WebSampler sampler = new WebSampler();
		configureTestElement(sampler);
		return sampler;
	}

	@Override
	public void modifyTestElement(TestElement element) {

	}

}
