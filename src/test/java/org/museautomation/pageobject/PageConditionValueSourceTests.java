package org.museautomation.pageobject;

import org.junit.*;
import org.museautomation.pageobject.steps.*;
import org.museautomation.builtins.step.*;
import org.museautomation.core.*;
import org.museautomation.core.context.*;
import org.museautomation.core.project.*;
import org.museautomation.core.resource.*;
import org.museautomation.core.step.*;
import org.museautomation.core.steptest.*;
import org.museautomation.core.values.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageConditionValueSourceTests
	{
	@Test
	public void evalCondition() throws MuseInstantiationException, ValueSourceResolutionError
		{
	    ValueSourceConfiguration config = ValueSourceConfiguration.forType(PageConditionValueSource.TYPE_ID);
	    config.addSource(PageConditionValueSource.PAGE_PARAM_ID, ValueSourceConfiguration.forValue("page1"));
	    config.addSource(PageConditionValueSource.CONDITION_PARAM_ID, ValueSourceConfiguration.forValue("cond1"));

	    MuseValueSource source = config.createSource(_project);
	    Assert.assertEquals("cond1", source.resolveValue(_context).toString());
	    }

	@Test
	public void pageMissing() throws MuseInstantiationException
		{
	    ValueSourceConfiguration config = ValueSourceConfiguration.forType(PageConditionValueSource.TYPE_ID);
	    config.addSource(PageConditionValueSource.PAGE_PARAM_ID, ValueSourceConfiguration.forValue("pageNotExists"));
	    config.addSource(PageConditionValueSource.CONDITION_PARAM_ID, ValueSourceConfiguration.forValue("cond1"));

	    MuseValueSource source = config.createSource(_project);
		try
			{
			Assert.assertEquals("cond1", source.resolveValue(_context).toString());
			Assert.assertFalse("Should have thrown an exception", true);
			}
		catch (ValueSourceResolutionError e)
			{
			Assert.assertTrue(e.getMessage().contains("pageNotExists"));
			}
		}

	@Test
	public void conditionMissing() throws MuseInstantiationException
		{
	    ValueSourceConfiguration config = ValueSourceConfiguration.forType(PageConditionValueSource.TYPE_ID);
	    config.addSource(PageConditionValueSource.PAGE_PARAM_ID, ValueSourceConfiguration.forValue("page1"));
	    config.addSource(PageConditionValueSource.CONDITION_PARAM_ID, ValueSourceConfiguration.forValue("conditionNotExist"));

	    MuseValueSource source = config.createSource(_project);
		try
			{
			Assert.assertEquals("cond1", source.resolveValue(_context).toString());
			Assert.assertFalse("Should have thrown an exception", true);
			}
		catch (ValueSourceResolutionError e)
			{
			Assert.assertTrue(e.getMessage().contains("conditionNotExist"));
			}
		}

	@Before
	public void setup() throws IOException
		{
		WebPage page = new WebPage();
		final PageCondition condition = new PageCondition();
		condition.setCondition(ValueSourceConfiguration.forValue("cond1"));
		page.conditions().add("cond1", condition);
		page.setId("page1");
		_project.getResourceStorage().addResource(page);

		StepConfiguration verify_step = new StepConfiguration(Verify.TYPE_ID);
		List<StepConfiguration> sub_steps = Collections.singletonList(verify_step);
		// TODO setup parent step context (OnPage)
		// TODO setup step context
		StepsExecutionContext steps_context = new ListOfStepsExecutionContext(new DefaultSteppedTestExecutionContext(_project, new SteppedTest(new StepConfiguration(OnPageStep.TYPE_ID))), sub_steps, false, null);
		_context = new SingleStepExecutionContext(steps_context, verify_step, false);
		}

	private MuseProject _project = new SimpleProject();
	private StepExecutionContext _context;
	}
