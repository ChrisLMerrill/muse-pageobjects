package org.musetest.pageobject;

import org.junit.*;
import org.musetest.builtins.value.*;
import org.musetest.core.*;
import org.musetest.core.context.*;
import org.musetest.core.mocks.*;
import org.musetest.core.project.*;
import org.musetest.core.step.*;
import org.musetest.core.values.*;
import org.musetest.selenium.*;
import org.musetest.selenium.locators.*;
import org.musetest.selenium.mocks.*;
import org.musetest.selenium.steps.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class Tests
	{
	@Test
	public void locateElementWithPageMap() throws MuseExecutionError, IOException
		{
		MuseProject project = new SimpleProject();

		WebPage page1 = new WebPage();
		page1.setId("page1");
		PageElement element1 = new PageElement();
		final String element_id = "element#1";
		element1.setLocator(ValueSourceConfiguration.forSource(IdElementValueSource.TYPE_ID, ValueSourceConfiguration.forValue(element_id)));
		String page_element_id = "element1";
		page1.addElement(page_element_id, element1);
		project.getResourceStorage().addResource(page1);

		MuseMockDriver driver = new MuseMockDriver();
		final MuseMockElement mock_element1 = new MuseMockElement();
		driver.addIdElement(element_id, mock_element1);

		StepExecutionContext context = new MockStepExecutionContext(project);
		BrowserStepExecutionContext.putDriver(driver, context);

		StepConfiguration click = new StepConfiguration(ClickElement.TYPE_ID);
		ValueSourceConfiguration element_source = ValueSourceConfiguration.forType(PagesElementValueSource.TYPE_ID);
		element_source.addSource(PagesElementValueSource.PAGE_PARAM_ID, ValueSourceConfiguration.forValue(page1.getId()));
		element_source.addSource(PagesElementValueSource.ELEMENT_PARAM_ID, ValueSourceConfiguration.forValue(page_element_id));
		click.addSource(ClickElement.ELEMENT_PARAM, element_source);
		MuseStep step = click.createStep(null);
		step.execute(context);

		Assert.assertTrue(mock_element1.isClicked());
		}

	@Test
	public void pageElementExpressionSupportWithTwoArguments()
		{
		PagesElementValueSource.StringExpressionSupport supporter = new PagesElementValueSource.StringExpressionSupport();
		List<ValueSourceConfiguration> configs = new ArrayList<>();
		configs.add(ValueSourceConfiguration.forValue("page"));
		configs.add(ValueSourceConfiguration.forValue("element"));
		ValueSourceConfiguration config = supporter.fromElementLookupExpression(configs, PROJECT);
		Assert.assertEquals("the page param was not set", "page", config.getSource(PagesElementValueSource.PAGE_PARAM_ID).getValue());
		Assert.assertEquals("the element param was not set", "element", config.getSource(PagesElementValueSource.ELEMENT_PARAM_ID).getValue());

		Assert.assertEquals("not stringified correctly", String.format("<%s.%s>", "page", "element"), supporter.toString(config, PROJECT));
		}

	@Test
	public void pageElementExpressionSupportWithTwoNonStringArguments()
		{
		PagesElementValueSource.StringExpressionSupport supporter = new PagesElementValueSource.StringExpressionSupport();
		List<ValueSourceConfiguration> configs = new ArrayList<>();
		ValueSourceConfiguration page_source = ValueSourceConfiguration.forValue("page_var");
		configs.add(ValueSourceConfiguration.forTypeWithSource(VariableValueSource.TYPE_ID, page_source));
		ValueSourceConfiguration element_source = ValueSourceConfiguration.forValue("element_var");
		configs.add(ValueSourceConfiguration.forTypeWithSource(VariableValueSource.TYPE_ID, element_source));
		ValueSourceConfiguration config = supporter.fromElementLookupExpression(configs, PROJECT);
		Assert.assertEquals("the page param was not set", page_source, config.getSource(PagesElementValueSource.PAGE_PARAM_ID).getSource());
		Assert.assertEquals("the element param was not set", element_source, config.getSource(PagesElementValueSource.ELEMENT_PARAM_ID).getSource());

		Assert.assertEquals("not stringified correctly", String.format("<$\"%s\".$\"%s\">", "page_var", "element_var"), supporter.toString(config, PROJECT));
		}

	private final static MuseProject PROJECT = new SimpleProject();
	}