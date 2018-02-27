package org.musetest.pageobject.steps;

import org.musetest.core.*;
import org.musetest.core.step.*;
import org.musetest.core.step.descriptor.*;
import org.musetest.core.values.*;
import org.musetest.core.values.strings.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PerformActionDescriptor extends AnnotatedStepDescriptor
	{
	public PerformActionDescriptor(MuseProject project)
		{
		super(PerformActionStep.class, project);
		}

	@Override
	public String getShortDescription(StepConfiguration step)
		{
		final ValueSourceConfiguration action_source = step.getSource(PerformActionStep.ACTION_PARAM);
		String name;
		if (action_source.getValue() != null)
			name = action_source.getValue().toString();
		else
			name = getProject().getValueSourceDescriptors().get(action_source).getInstanceDescription(action_source, new StepExpressionContext(getProject(), step));
		return "Perform action: " + name;
		}
	}
