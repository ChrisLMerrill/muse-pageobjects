package org.musetest.pageobject.steps;

import org.junit.*;
import org.musetest.builtins.step.*;
import org.musetest.builtins.value.*;
import org.musetest.core.*;
import org.musetest.core.context.*;
import org.musetest.core.events.*;
import org.musetest.core.events.matching.*;
import org.musetest.core.project.*;
import org.musetest.core.step.*;
import org.musetest.core.steptest.*;
import org.musetest.core.values.*;
import org.musetest.pageobject.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PerformActionStepTests
	{
	@Test
	public void performAction() throws IOException, MuseExecutionError
		{
	    MuseProject project = new SimpleProject();

		// create a page in the project
	    WebPage page = new WebPage();
	    page.setId("page1");
	    project.getResourceStorage().addResource(page);

		// create a function in the project
	    Function function = new Function();
	    function.setId("function1");
	    StepConfiguration step = new StepConfiguration(LogMessage.TYPE_ID);
	    ValueSourceConfiguration message_source = ValueSourceConfiguration.forType(AdditionSource.TYPE_ID);
		message_source.addSource(0, ValueSourceConfiguration.forValue("message1"));
		message_source.addSource(1, ValueSourceConfiguration.forTypeWithValue(VariableValueSource.TYPE_ID, "default_param"));
		message_source.addSource(2, ValueSourceConfiguration.forTypeWithValue(VariableValueSource.TYPE_ID, "exposed-param"));
		step.addSource(LogMessage.MESSAGE_PARAM, message_source);
	    function.setStep(step);
	    project.getResourceStorage().addResource(function);

		// configure action on page to call function
		PageAction action = new PageAction();
		action.setFunction("function1");
		page.addAction("action1", action);
		action.defaultParameters().addSource("default_param", ValueSourceConfiguration.forValue("default-param-value"));

	    // create a test with a PerformAction step that calls the function
		SteppedTest test = new SteppedTest();
		StepConfiguration test_step = new StepConfiguration(PerformActionStep.TYPE_ID);
		test_step.addSource(PerformActionStep.PAGE_PARAM, ValueSourceConfiguration.forValue("page1"));
		test_step.addSource(PerformActionStep.ACTION_PARAM, ValueSourceConfiguration.forValue("action1"));
		test_step.addSource("exposed-param", ValueSourceConfiguration.forValue("exposed-param-value"));
		test.setStep(test_step);

		TestExecutionContext context = new DefaultSteppedTestExecutionContext(project, test);
		EventLogger logger = (EventLogger) new EventLoggerConfiguration.EventLoggerType().create().createPlugin();
		context.addPlugin(logger);
		context.initializePlugins();
		Boolean ran = test.execute(context);
		Assert.assertTrue(ran);

	    // step looks up the right function based on the config and invokes it
		final List<MuseEvent> events = logger.getData().findEvents(new EventTypeMatcher(MessageEventType.TYPE_ID));
		Assert.assertEquals(1, events.size());

		MuseEvent event = events.get(0);
		String message = event.getAttribute(MuseEvent.DESCRIPTION).toString();
		Assert.assertTrue(message.contains("message1"));

		// step passes default values to the function
		Assert.assertTrue(message.contains("default-param-value"));

	   	// step passes configured values to the function
		Assert.assertTrue(message.contains("exposed-param-value"));
	    }

	}


