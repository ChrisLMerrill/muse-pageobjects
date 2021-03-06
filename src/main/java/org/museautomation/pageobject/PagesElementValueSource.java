package org.museautomation.pageobject;

import org.museautomation.builtins.value.*;
import org.museautomation.core.*;
import org.museautomation.core.resource.*;
import org.museautomation.core.values.*;
import org.museautomation.core.values.descriptor.*;

import java.util.*;

/**
 * Christopher L Merrill (see LICENSE.txt for license details)
 */
@MuseTypeId("page-element")
@MuseValueSourceName("Element by page/element lookup")
@MuseValueSourceShortDescription("Locates a Selenium WebElement from the page/element specified by the subsource")
@MuseValueSourceTypeGroup("Element.Locate")
@MuseStringExpressionSupportImplementation(PagesElementValueSource.StringExpressionSupport.class)
@MuseSubsourceDescriptor(displayName = "Page id", description = "The id of the page (in the project) to lookup the element in", type = SubsourceDescriptor.Type.Named, name = PagesElementValueSource.PAGE_PARAM_ID)
@MuseSubsourceDescriptor(displayName = "Element id", description = "The id of the element to lookup in the page", type = SubsourceDescriptor.Type.Named, name = PagesElementValueSource.ELEMENT_PARAM_ID)
public class PagesElementValueSource extends BaseValueSource
    {
    public PagesElementValueSource(ValueSourceConfiguration config, MuseProject project) throws MuseInstantiationException
        {
        super(config, project);
        upgrade(config);
        _page_source = config.getSource(PagesElementValueSource.PAGE_PARAM_ID).createSource(project);
        _element_source = config.getSource(PagesElementValueSource.ELEMENT_PARAM_ID).createSource(project);
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

        // get the element-id
        value = _element_source.resolveValue(context);
        if (!(value instanceof String))
            throw new ValueSourceResolutionError(String.format("The 'element' parameter must resolve to a String. Instead, it was %s (a %s)", value, value.getClass().getSimpleName()));
        String element_id = (String) value;

        // find the PageElement
        PageElement element = page.getElements().get(element_id);
        if (element == null)
	        throw new ValueSourceResolutionError(String.format("Element '%s' not found on page '%s'", element_id, page_id));

        ValueSourceConfiguration element_locator_config = element.getLocator();
        if (element_locator_config == null)
            throw new ValueSourceResolutionError(String.format("No locator configured for page-element '%s.%s'", page_id, element_id));

        try
            {
            return element_locator_config.createSource(project).resolveValue(context);
            }
        catch (MuseInstantiationException e)
            {
            throw new ValueSourceResolutionError("Unable to resolve page element source - unable to instantiate the specified locator due to: " + e.getMessage(), e);
            }
        }

    private static void upgrade(ValueSourceConfiguration config)
        {
        ValueSourceConfiguration subsource = config.getSource();
        if (subsource != null
            && subsource.getType().equals(StringValueSource.TYPE_ID)
            && subsource.getValue() != null
            && subsource.getValue().toString().contains("."))
            {
            StringTokenizer tokenizer = new StringTokenizer(subsource.getValue().toString(), ".");
            config.addSource(PagesElementValueSource.PAGE_PARAM_ID, ValueSourceConfiguration.forValue(tokenizer.nextToken()));
            config.addSource(PagesElementValueSource.ELEMENT_PARAM_ID, ValueSourceConfiguration.forValue(tokenizer.nextToken()));
            config.setSource(null);
            }
        }

    private MuseValueSource _page_source;
    private MuseValueSource _element_source;

    public final static String TYPE_ID = PagesElementValueSource.class.getAnnotation(MuseTypeId.class).value();
    public final static String PAGE_PARAM_ID = "page";
    public final static String ELEMENT_PARAM_ID = "element";

    @SuppressWarnings("WeakerAccess")  // needs public static access to be discovered and instantiated via reflection
    public static class StringExpressionSupport extends BaseValueSourceStringExpressionSupport
        {
        @Override
        public ValueSourceConfiguration fromElementLookupExpression(List<ValueSourceConfiguration> arguments, MuseProject project)
            {
            ValueSourceConfiguration page_source = arguments.get(0);
            ValueSourceConfiguration element_source = arguments.get(1);
            ValueSourceConfiguration source = ValueSourceConfiguration.forType(PagesElementValueSource.TYPE_ID);
            source.addSource(PagesElementValueSource.PAGE_PARAM_ID, page_source);
            source.addSource(PagesElementValueSource.ELEMENT_PARAM_ID, element_source);
            return source;
            }

        @Override
        public String toString(ValueSourceConfiguration config, StringExpressionContext context, int depth)
	        {
            if (config.getType().equals(PagesElementValueSource.TYPE_ID))
                {
                PagesElementValueSource.upgrade(config);
                StringBuilder builder = new StringBuilder();
                if (depth > 0)
                    builder.append("(");

                String page;
                ValueSourceConfiguration page_source = config.getSource(PagesElementValueSource.PAGE_PARAM_ID);
                if (page_source.getType().equals(StringValueSource.TYPE_ID))
                    page = page_source.getValue().toString();
                else
                    page = context.getProject().getValueSourceStringExpressionSupporters().toString(page_source, context, depth + 1);

                String element;
                ValueSourceConfiguration element_source = config.getSource(PagesElementValueSource.ELEMENT_PARAM_ID);
                if (element_source.getType().equals(StringValueSource.TYPE_ID))
                    element = element_source.getValue().toString();
                else
                    element = context.getProject().getValueSourceStringExpressionSupporters().toString(element_source, context, depth + 1);

                builder.append(String.format("<%s.%s>", page, element));
                if (depth > 0)
                    builder.append(")");
                return builder.toString();
                }
            return null;
            }
        }
    }