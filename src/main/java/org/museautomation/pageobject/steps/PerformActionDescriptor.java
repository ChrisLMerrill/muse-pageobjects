package org.museautomation.pageobject.steps;

import org.museautomation.core.*;
import org.museautomation.core.step.*;
import org.museautomation.core.step.descriptor.*;
import org.museautomation.core.values.*;
import org.museautomation.core.values.strings.*;

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
