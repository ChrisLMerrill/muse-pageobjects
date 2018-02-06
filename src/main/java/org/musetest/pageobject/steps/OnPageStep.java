package org.musetest.pageobject.steps;

import org.musetest.core.*;
import org.musetest.core.context.*;
import org.musetest.core.resource.*;
import org.musetest.core.step.*;
import org.musetest.core.step.descriptor.*;
import org.musetest.core.values.descriptor.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@MuseTypeId("on-page")
@MuseStepName("On page")
@MuseStepIcon("glyph:FontAwesome:FILE_TEXT_ALT")
@MuseStepTypeGroup("Page Objects")
@MuseInlineEditString("On page: {pageid}")
@MuseStepShortDescription("Perform actions on a page")
@MuseStepLongDescription("Resolve the Page source to a string and find that page in the project. Child steps operate on the page.")
@MuseSubsourceDescriptor(displayName = "Page", description = "Page to navigate to", type = SubsourceDescriptor.Type.Named, name = OnPageStep.PAGE_PARAM)
@MuseStepDescriptorImplementation(OnPageDescriptor.class)
@SuppressWarnings("unused")  // instantiated by reflection
public class OnPageStep extends BasicCompoundStep
	{
	public OnPageStep(StepConfiguration configuration, MuseProject project) throws MuseInstantiationException
		{
		super(configuration, project);
		_page = getValueSource(configuration, PAGE_PARAM, true, project);
		_project = project;
		}

	@Override
	protected void beforeChildrenExecuted(StepExecutionContext context) throws MuseExecutionError
		{
/*
		String pageid = getValue(_page, context, false, String.class);
		// get the page
		ResourceToken token = _project.getResourceStorage().findResource(pageid);
		if (token == null)
			throw new StepConfigurationError(String.format("There is no resource in the project with id=%s. Expected to find a Web Page.", pageid));
		MuseResource resource = token.getResource();
		if (!(resource instanceof WebPage))
			throw new StepConfigurationError(String.format("%s is a %s. Expected a Web Page.", pageid, resource.getType().getName()));
		WebPage page = (WebPage) _project.getResourceStorage().findResource(pageid).getResource();
*/

		// TODO verify we are on the right page
		}

	private final MuseValueSource _page;
	private final MuseProject _project;

	public final static String PAGE_PARAM = "pageid";
	public final static String TYPE_ID = OnPageStep.class.getAnnotation(MuseTypeId.class).value();
	}


