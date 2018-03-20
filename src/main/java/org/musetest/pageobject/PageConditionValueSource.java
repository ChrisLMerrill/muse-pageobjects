package org.musetest.pageobject;

import org.musetest.core.*;
import org.musetest.core.resource.*;
import org.musetest.core.values.*;
import org.musetest.core.values.descriptor.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@MuseTypeId("page-condition")
@MuseValueSourceName("Condition by page/condition lookup")
@MuseValueSourceShortDescription("Evaluates a condition from the page/condition specified by the parameters")
@MuseValueSourceTypeGroup("Element")
//@MuseStringExpressionSupportImplementation(PageConditionValueSource.StringExpressionSupport.class)
@MuseSubsourceDescriptor(displayName = "Page id", description = "The id of the page (in the project) to lookup the condition in", type = SubsourceDescriptor.Type.Named, name = PageConditionValueSource.PAGE_PARAM_ID)
@MuseSubsourceDescriptor(displayName = "Condition id", description = "The id of the condition to evaluate on the page", type = SubsourceDescriptor.Type.Named, name = PageConditionValueSource.CONDITION_PARAM_ID)
public class PageConditionValueSource extends BaseValueSource
	{
	public PageConditionValueSource(ValueSourceConfiguration config, MuseProject project) throws MuseInstantiationException
		{
		super(config, project);
		_page_source = config.getSource(PAGE_PARAM_ID).createSource(project);
		_condition_source = config.getSource(CONDITION_PARAM_ID).createSource(project);
		}

	@Override
	public Object resolveValue(MuseExecutionContext context) throws ValueSourceResolutionError
		{
		MuseProject project = context.getProject();

		// get the page-id
		Object value = _page_source.resolveValue(context);
		if (!(value instanceof String))
			throw new ValueSourceResolutionError(String.format("The 'page' parameter must resolve to a String. Instead, it was %s (a %s)", value, value.getClass().getSimpleName()));
		String page_id = (String) value;

		// find the WebPage
		WebPage page = context.getProject().getResourceStorage().getResource(page_id, WebPage.class);
		if (page == null)
			throw new ValueSourceResolutionError(String.format("Page '%s' not found in the project", page_id));

		// get the condition-id
		value = _condition_source.resolveValue(context);
		if (!(value instanceof String))
			throw new ValueSourceResolutionError(String.format("The 'condition' parameter must resolve to a String. Instead, it was %s (a %s)", value, value.getClass().getSimpleName()));
		String condition_id = (String) value;

		// find the PageCondition
		PageCondition condition = page.conditions().get(condition_id);
		if (condition == null)
			throw new ValueSourceResolutionError(String.format("Condition '%s' not found on page '%s'", condition_id, page_id));

		ValueSourceConfiguration condition_config = condition.getCondition();
		if (condition_config == null)
			throw new ValueSourceResolutionError(String.format("No condition configured for page-condition '%s.%s'", page_id, condition_id));

		try
			{
			return condition_config.createSource(project).resolveValue(context);
			}
		catch (MuseInstantiationException e)
			{
			throw new ValueSourceResolutionError("Unable to resolve page condition source - unable to instantiate the specified locator due to: " + e.getMessage(), e);
			}
		}

	private MuseValueSource _page_source;
	private MuseValueSource _condition_source;

	public final static String TYPE_ID = PageConditionValueSource.class.getAnnotation(MuseTypeId.class).value();
	public final static String PAGE_PARAM_ID = "page";
	public final static String CONDITION_PARAM_ID = "condition";
	}
