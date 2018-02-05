package org.musetest.pageobject.steps;

import org.musetest.core.*;
import org.musetest.core.context.*;
import org.musetest.core.resource.*;
import org.musetest.core.step.*;
import org.musetest.core.step.descriptor.*;
import org.musetest.core.steptest.*;
import org.musetest.core.values.*;
import org.musetest.core.values.descriptor.*;
import org.musetest.pageobject.*;
import org.musetest.selenium.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@MuseTypeId("start-at-page")
@MuseStepName("Start at page")
@MuseStepIcon("glyph:FontAwesome:FILE_TEXT_ALT")
@MuseStepTypeGroup("Page Objects")
@MuseInlineEditString("Start at page: {pageid}")
@MuseStepShortDescription("Start at a page by navigating to its URL")
@MuseStepLongDescription("Resolve the Page source to a string and find that page in the project. Lookup the page's URL parameter, resolve it and then go to that URL in the browser.")
@MuseSubsourceDescriptor(displayName = "Page", description = "Page to navigate to", type = SubsourceDescriptor.Type.Named, name = StartAtPageStep.PAGE_PARAM)
@MuseStepDescriptorImplementation(StartAtPageDescriptor.class)
public class StartAtPageStep extends BasicCompoundStep
	{
	public StartAtPageStep(StepConfiguration configuration, MuseProject project) throws MuseInstantiationException
		{
		super(configuration, project);
		_page = getValueSource(configuration, PAGE_PARAM, true, project);
		_project = project;
		}

	@Override
	protected void beforeChildrenExecuted(StepExecutionContext context) throws MuseExecutionError
		{
		// get the page
		String pageid = getValue(_page, context, false, String.class);
		ResourceToken token = _project.getResourceStorage().findResource(pageid);
		if (token == null)
			throw new StepConfigurationError(String.format("There is no resource in the project with id=%s. Expected to find a Web Page.", pageid));
		MuseResource resource = token.getResource();
		if (!(resource instanceof WebPage))
			throw new StepConfigurationError(String.format("%s is a %s. Expected a Web Page.", pageid, resource.getType().getName()));
		WebPage page = (WebPage) _project.getResourceStorage().findResource(pageid).getResource();

		// resolve the URL
		ValueSourceConfiguration url_config = page.parameters().getSource(WebPage.URL_PARAM);
		if (url_config == null)
			throw new StepConfigurationError(String.format("Page %s does not have a URL specified. Cannot Start at this page.", pageid));
		MuseValueSource url_source = url_config.createSource(_project);
		String url = BaseValueSource.getValue(url_source, context, false, String.class);
		BrowserStepExecutionContext.getDriver(context).navigate().to(url);

		// TODO verify we are on the right page
		}

	private final MuseValueSource _page;
	private final MuseProject _project;

	public final static String PAGE_PARAM = "pageid";
	public final static String TYPE_ID = StartAtPageStep.class.getAnnotation(MuseTypeId.class).value();
	}


