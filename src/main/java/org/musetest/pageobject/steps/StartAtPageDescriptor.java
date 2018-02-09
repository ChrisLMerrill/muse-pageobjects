package org.musetest.pageobject.steps;

import org.musetest.core.*;
import org.musetest.core.step.*;
import org.musetest.core.step.descriptor.*;

import static org.musetest.pageobject.steps.OnPageDescriptor.*;

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


