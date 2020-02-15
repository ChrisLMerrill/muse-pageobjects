package org.museautomation.pageobject;

import com.fasterxml.jackson.databind.*;
import org.junit.*;
import org.museautomation.pageobject.events.*;
import org.museautomation.core.util.*;
import org.museautomation.core.values.*;

import java.io.*;
import java.util.concurrent.atomic.*;
import java.util.regex.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageActionTests
	{
	@Test
	public void serialization() throws IOException
		{
		// create action
	    PageAction action = new PageAction();
	    action.setFunction("functionId");
	    action.setDestinationPage("pageId");
	    action.exposedParameters().add("exposed");
	    action.defaultParameters().addSource("default", ValueSourceConfiguration.forValue("def_value"));

	    // serialize to json
	    ObjectMapper mapper = new ObjectMapper();
	    ByteArrayOutputStream outstream = new ByteArrayOutputStream();
	    mapper.writeValue(outstream, action);
	    String json = outstream.toString();
	    outstream.close();

	    // test contents in json
		Assert.assertEquals(1, countOccurrences(json, "functionId"));
		Assert.assertEquals(1, countOccurrences(json, "pageId"));
		Assert.assertEquals(1, countOccurrences(json, "exposedParam"));
		Assert.assertEquals(1, countOccurrences(json, "defaultParam"));
		Assert.assertEquals(1, countOccurrences(json, "def_value"));

		// deserialize
		PageAction copy = mapper.readValue(json.getBytes(), PageAction.class);

		// test equality
		Assert.assertEquals(action, copy);
	    }

	private int countOccurrences(String source, String search)
		{
		Pattern p = Pattern.compile(search);
		Matcher m = p.matcher(source);
		int count = 0;
		while (m.find())
			count++;
		return count;
		}

	@Test
	public void equality()
	    {
	    PageAction action1 = new PageAction();
	    PageAction action2 = new PageAction();

	    Assert.assertEquals(action1, action2);

	    action1.setFunction("f1");
	    Assert.assertNotEquals(action1, action2);
	    action2.setFunction("other");
	    Assert.assertNotEquals(action1, action2);
	    action2.setFunction("f1");
	    Assert.assertEquals(action1, action2);

	    action1.setDestinationPage("p1");
	    Assert.assertNotEquals(action1, action2);
	    action2.setDestinationPage("other");
	    Assert.assertNotEquals(action1, action2);
	    action2.setDestinationPage("p1");
	    Assert.assertEquals(action1, action2);

	    action1.exposedParameters().add("e1");
	    Assert.assertNotEquals(action1, action2);
	    action2.exposedParameters().add("other");
	    Assert.assertNotEquals(action1, action2);
	    action2.exposedParameters().clear();
	    action2.exposedParameters().add("e1");
	    Assert.assertEquals(action1, action2);
	    }

	@Test
	public void functionChangeEvent()
	    {
	    PageAction action = new PageAction();
	    AtomicReference<ChangeEvent> event_holder = new AtomicReference<>();
	    action.addChangeListener(event_holder::set);

	    action.setFunction("f1");
	    FunctionChangedEvent event = (FunctionChangedEvent) event_holder.get();
	    Assert.assertTrue(action == event.getTarget());
	    Assert.assertEquals(null, event.getOldFunctionId());
	    Assert.assertEquals("f1", event.getNewFunctionId());

	    action.setFunction("f2");
	    event = (FunctionChangedEvent) event_holder.get();
	    Assert.assertEquals("f1", event.getOldFunctionId());
	    Assert.assertEquals("f2", event.getNewFunctionId());
	    }

	// get/set from WebPage --> in WebPage tests
	}


