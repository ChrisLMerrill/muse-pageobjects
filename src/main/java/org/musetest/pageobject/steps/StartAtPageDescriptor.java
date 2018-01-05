package org.musetest.pageobject.steps;

import org.musetest.core.*;
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
	public StartAtPageDescriptor(MuseProject project)
		{
		super(StartAtPageStep.class, project);
		}

	@Override
	public String getShortDescription(StepConfiguration step)
		{
		ValueSourceConfiguration source = step.getSource(StartAtPageStep.PAGE_PARAM);
		if (source != null && source.getValue() != null)
			{
			final ResourceToken token = getProject().getResourceStorage().findResource(source.getValue().toString());
			if (token != null)
				{
				MuseResource resource = token.getResource();
				if (resource instanceof WebPage)
					{
					WebPage page = (WebPage) resource;
					Object name = page.metadata().getMetadataField(WebPage.PAGE_NAME_META);
					if (name != null)
						return "Start at Page: " + name.toString();
					}
				}
			}
		return super.getShortDescription(step);
		}
	}


