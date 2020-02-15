package org.museautomation.pageobject;

import org.museautomation.core.*;
import org.museautomation.core.context.*;
import org.museautomation.core.resource.*;
import org.museautomation.core.step.*;
import org.museautomation.core.values.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageObjects
	{
	public static WebPage locatePage(MuseProject project, StepConfiguration step, String param_name)
		{
		return locatePage(step.getSource(param_name), project, new ProjectExecutionContext(project));
		}

	public static WebPage locatePage(ValueSourceConfiguration page_config, MuseProject project)
		{
		return locatePage(page_config, project, new ProjectExecutionContext(project));
		}

	public static WebPage locatePage(ValueSourceConfiguration page_config, MuseProject project, MuseExecutionContext context)
		{
		final Object page;
		try
			{
			page = BaseValueSource.getValue(page_config.createSource(project), context, false, Object.class);
			}
		catch (ValueSourceResolutionError | MuseInstantiationException e1)
			{
			return null;
			}
		if (page instanceof WebPage)
			return (WebPage) page;
		String page_id = page.toString();
		MuseResource resource = project.getResourceStorage().getResource(page_id);
		if (resource instanceof WebPage)
			return (WebPage) resource;
		return null;
		}
	}
