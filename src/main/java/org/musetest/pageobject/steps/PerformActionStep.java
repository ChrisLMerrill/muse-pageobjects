package org.musetest.pageobject.steps;

import org.jetbrains.annotations.*;
import org.musetest.core.*;
import org.musetest.core.context.*;
import org.musetest.core.resource.*;
import org.musetest.core.step.*;
import org.musetest.core.step.descriptor.*;
import org.musetest.core.values.*;
import org.musetest.core.values.descriptor.*;
import org.musetest.pageobject.*;

import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@MuseTypeId("perform-action")
@MuseStepName("Perform Action")
@MuseStepIcon("glyph:FontAwesome:FILE_TEXT_ALT")
@MuseStepTypeGroup("Page Objects")
@MuseInlineEditString("Perform action {actionid}")
@MuseStepShortDescription("Perform an action on a page")
@MuseStepLongDescription("Resolve the Page source to a string and find that page in the project. Resolve the Action source and find that action on the page. Perform the action with the provided parameters.")
@MuseSubsourceDescriptor(displayName = "Page", description = "Id of the page containing the action", type = SubsourceDescriptor.Type.Named, name = PerformActionStep.PAGE_PARAM)
@MuseSubsourceDescriptor(displayName = "Action", description = "Id of the action", type = SubsourceDescriptor.Type.Named, name = PerformActionStep.ACTION_PARAM)
@MuseSubsourceDescriptor(displayName = "Parameters", description = "Parameters to pass to the action", type = SubsourceDescriptor.Type.Map, optional = true)
@MuseStepDescriptorImplementation(PerformActionDescriptor.class)
public class PerformActionStep extends CallFunction
	{
	@SuppressWarnings("unused") // called via reflection
	public PerformActionStep(StepConfiguration config, MuseProject project) throws MuseInstantiationException
		{
		super(config, project);
		_page = getValueSource(config, PAGE_PARAM, true, project);
		_action = getValueSource(config, ACTION_PARAM, true, project);
		}

	@Override
	protected boolean shouldEnter(StepExecutionContext context) throws MuseExecutionError
		{
		boolean enter = super.shouldEnter(context);
		if (!enter)
			return false;

		// lookup page
		String page_id = getValue(_page, context, false, String.class);
		Object resource = context.getProject().getResourceStorage().getResource(page_id);
		if (!(resource instanceof WebPage))
			throw new ValueSourceResolutionError("The Page parameter did not resolve to a WebPage in the project. Instead, it was a " + resource.getClass().getSimpleName());
		WebPage page = (WebPage) resource;

		// lookup action
		String action_id = getValue(_action, context, false, String.class);
		PageAction action = page.getActions().get(action_id);
		if (action == null)
			throw new ValueSourceResolutionError("The Action parameter did not resolve to an Action on the WebPage");

		_steps_id = action.getFunction();

		// resolve the default parameters (sources) to be passed to the function BEFORE the new variable scope is created.
		Map<String, ValueSourceConfiguration> sources = action.getDefaultParameters();
		if (sources != null)
			for (String name : sources.keySet())
				{
				if (name.equals(PAGE_PARAM) || name.equals(ACTION_PARAM))
					continue;
				resolveAndAddParameter(name, sources.get(name), context);
				}
		return true;
		}

	@NotNull
	@Override
	protected String getStepsId(StepExecutionContext context)
		{
		return _steps_id;
		}

	private final MuseValueSource _page;
	private final MuseValueSource _action;
	private String _steps_id;

	public final static String PAGE_PARAM = "pageid";
	public final static String ACTION_PARAM = "actionid";

	public final static String TYPE_ID = PerformActionStep.class.getAnnotation(MuseTypeId.class).value();
	}


