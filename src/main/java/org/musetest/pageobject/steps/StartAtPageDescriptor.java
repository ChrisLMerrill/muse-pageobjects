package org.musetest.pageobject.steps;

import org.musetest.core.*;
import org.musetest.core.context.*;
import org.musetest.core.resource.*;
import org.musetest.core.step.*;
import org.musetest.core.step.descriptor.*;
import org.musetest.core.values.*;
import org.musetest.pageobject.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class StartAtPageDescriptor extends AnnotatedStepDescriptor
	{
	@SuppressWarnings("WeakerAccess")  // instantiated by reflection
	public StartAtPageDescriptor(MuseProject project)
		{
		super(StartAtPageStep.class, project);
		}

	@Override
	public String getShortDescription(StepConfiguration step)
		{
		String name = "?";
		try
			{
			final MuseValueSource source = BaseValueSource.getValueSource(step, StartAtPageStep.PAGE_PARAM, true, _project);
			String page_id = BaseValueSource.getValue(source, new BaseExecutionContext(_project), false, String.class);
			final ResourceToken token = getProject().getResourceStorage().findResource(page_id);
			name = page_id;
			if (token != null)
				{
				MuseResource resource = token.getResource();
				if (resource instanceof WebPage)
					{
					WebPage page = (WebPage) resource;
					name = page.metadata().getMetadataField(WebPage.PAGE_NAME_META).toString();
					}
				}
			}
		catch (Exception e)
			{
			// continue
			}

		return "Start at Page: " + name;
		}
	}


