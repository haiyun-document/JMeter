package org.apache.jmeter.protocol.web.sampler;

import org.openqa.selenium.By;

public class ByFacade {
	
	public By className(String className) {
		return By.className(className);
	}
	
	public By cssSelector(String cssSelector) {
		return By.cssSelector(cssSelector);
	}
	
	public By id(String id) {
		return By.id(id);
	}
	
	public By linkText(String linkText) {
		return By.linkText(linkText);
	}
	
	public By name(String name) {
		return By.name(name);
	}
	
	public By partialLinkText(String partialText) {
		return By.partialLinkText(partialText);
	}
	
	public By tagName(String tagName) {
		return By.tagName(tagName);
	}
	
	public By xpath(String xpath) {
		return By.xpath(xpath);
	}
}
