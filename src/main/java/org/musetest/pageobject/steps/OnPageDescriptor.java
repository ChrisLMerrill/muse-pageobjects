package org.musetest.pageobject.steps;

import org.musetest.core.*;
import org.musetest.core.context.*;
import org.musetest.core.step.*;
import org.musetest.core.step.descriptor.*;
import org.musetest.core.values.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class OnPageDescriptor extends AnnotatedStepDescriptor
	{
	@SuppressWarnings("WeakerAccess")  // instantiated by reflection
	public OnPageDescriptor(MuseProject project)
		{
		super(OnPageStep.class, project);
		}

	@Override
	public String getShortDescription(StepConfiguration step)
		{
		String name = lookupPageId(getProject(), step, OnPageStep.PAGE_PARAM);
		return "On Page: " + name;
		}

	static String lookupPageId(MuseProject project, StepConfiguration step, String page_param)
		{
		String page_id = "?";
		try
			{
			final MuseValueSource source = BaseValueSource.getValueSource(step, page_param, true, project);
			page_id = BaseValueSource.getValue(source, new BaseExecutionContext(project), false, String.class);
			}
		catch (Exception e)
			{
			// continue
			}
		return page_id;
		}
	}


