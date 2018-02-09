package org.musetest.pageobject.steps;

import org.junit.*;
import org.musetest.builtins.step.*;
import org.musetest.core.*;
import org.musetest.core.context.*;
import org.musetest.core.mocks.*;
import org.musetest.core.project.*;
import org.musetest.core.resource.*;
import org.musetest.core.step.*;
import org.musetest.core.steptest.*;
import org.musetest.core.values.*;
import org.musetest.core.variables.*;
import org.musetest.pageobject.*;
import org.musetest.selenium.*;
import org.musetest.selenium.mocks.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class StartAtPageStepTests
	{
	@Test
	public void pageNotFound() throws MuseInstantiationException
		{
		_step_config.replaceSource(StartAtPageStep.PAGE_PARAM, ValueSourceConfiguration.forValue("notfound"));

		MockStepExecutionContext context = new MockStepExecutionContext();
		MuseStep step = _step_config.createStep(_project);

		try
			{
			step.execute(context);
			Assert.assertTrue("should have thrown an exception", false);
			}
		catch (MuseExecutionError e)
			{
			// GOOD
			}
		}

	@Test
	public void urlNotProvided() throws MuseInstantiationException
		{
		MuseStep step = _step_config.createStep(_project);

		try
			{
			step.execute(new MockStepExecutionContext());
			Assert.assertTrue("should have thrown an exception", false);
			}
		catch (MuseExecutionError e)
			{
			// GOOD
			}
		}

	@Test
	public void referencedWrongResourceType() throws MuseInstantiationException
		{
	    VariableList list = new VariableList();
	    list.setId("listid");
	    MuseStep step = _step_config.createStep(_project);
		_step_config.replaceSource(StartAtPageStep.PAGE_PARAM, ValueSourceConfiguration.forValue("listid"));

        try
            {
            step.execute(new MockStepExecutionContext());
            Assert.assertTrue("should have thrown an exception", false);
            }
        catch (MuseExecutionError e)
            {
            // GOOD
            }
	    }

	@Test
	public void navigate() throws MuseExecutionError
		{
		_page.parameters().addSource(WebPage.URL_PARAM, ValueSourceConfiguration.forValue(URL));
		MuseMockDriver driver = new MuseMockDriver();
		DefaultSteppedTestExecutionContext test_context = new DefaultSteppedTestExecutionContext(_project, new SteppedTest(_step_config));
		StepExecutionContext step_context = new SingleStepExecutionContext(test_context, _step_config, false);
		BrowserStepExecutionContext.putDriver(driver, step_context);

		MuseStep step = _step_config.createStep(_project);
		step.execute(step_context);

		Assert.assertEquals("browser didn't navigate", URL, driver.getCurrentUrl());
		}

	@Test
	public void descriptorShortDescription()
	    {
	    Assert.assertTrue(new StartAtPageDescriptor(_project).getShortDescription(_step_config).contains(PAGE_ID));
	    Assert.assertTrue(_project.getStepDescriptors().get(_step_config).getShortDescription(_step_config).contains(PAGE_ID));
	    }

	@Before
	public void setup() throws IOException
		{
		_page.setId(PAGE_ID);
		_project.getResourceStorage().addResource(_page);

		_step_config.setType(StartAtPageStep.TYPE_ID);
		_step_config.addSource(StartAtPageStep.PAGE_PARAM, ValueSourceConfiguration.forValue(PAGE_ID));
		_step_config.addChild(new StepConfiguration(LogMessage.TYPE_ID));
		}

	private final MuseProject _project = new SimpleProject();
	private final StepConfiguration _step_config = new StepConfiguration();
	private final WebPage _page = new WebPage();

	private final static String PAGE_ID = "page1";
	private final static String URL = "http://a.nice.url/";
	}


