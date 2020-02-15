package org.museautomation.pageobject.steps;

import org.museautomation.core.*;
import org.museautomation.core.step.*;
import org.museautomation.core.step.descriptor.*;

import static org.museautomation.pageobject.steps.OnPageDescriptor.*;

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
		String name = lookupPageId(getProject(), step, StartAtPageStep.PAGE_PARAM);
		return "Start at Page: " + name;
		}
	}


