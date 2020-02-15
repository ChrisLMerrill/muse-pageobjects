package org.museautomation.pageobject;

import org.museautomation.core.*;
import org.museautomation.core.resource.generic.*;
import org.museautomation.core.resource.types.*;
import org.museautomation.core.util.*;
import org.museautomation.core.values.descriptor.*;

import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@MuseTypeId("page")
@MuseSubsourceDescriptor(displayName = "Page URL", description = "The URL for directly accessing the page", type = SubsourceDescriptor.Type.Named, name = WebPage.URL_PARAM, optional = true)
public class WebPage extends GenericResourceConfiguration
    {
    @SuppressWarnings("unused,WeakerAccess")  // required for Json de/serialization
    public Map<String, PageElement> getElements()
        {
        return _elements.getElementMap();
        }

    @SuppressWarnings("unused")  // required for Json de/serialization
    public void setElements(Map<String, PageElement> elements)
        {
        _elements.setElementMap(elements);
        }

    public PageElements elements()
	    {
	    return _elements;
	    }

    public PageActions actions()
	    {
	    return _actions;
	    }

    public PageConditions conditions()
	    {
	    return _conditions;
	    }

    @SuppressWarnings("unused,WeakerAccess")  // required for Json de/serialization
    public Map<String, PageAction> getActions()
        {
        return Collections.unmodifiableMap(_actions.getMap());
        }

    @SuppressWarnings("unused")  // required for Json de/serialization
    public void setActions(Map<String, PageAction> actions)
        {
        _actions.setMap(actions);
        }

    @SuppressWarnings("unused,WeakerAccess")  // required for Json de/serialization
    public Map<String, PageCondition> getConditions()
        {
        return Collections.unmodifiableMap(_conditions.getMap());
        }

    @SuppressWarnings("unused")  // required for Json de/serialization
    public void setConditions(Map<String, PageCondition> conditions)
        {
        _conditions.setMap(conditions);
        }

    @SuppressWarnings("unused")  // used in UI
    public void addChangeListener(ChangeEventListener listener)
	    {
	    _listeners.add(listener);
	    }

    @SuppressWarnings("unused")  // used in UI
    public void removeChangeListener(ChangeEventListener listener)
	    {
	    _listeners.remove(listener);
	    }

    private void notifyListeners(ChangeEvent event)
	    {
	    for (ChangeEventListener listener : _listeners)
	    	listener.changeEventRaised(event);
	    }

    @Override
    public ResourceType getType()
        {
        return new WebPageResourceType();
        }

    private PageElements _elements = new PageElements(this::notifyListeners);
    private PageActions _actions = new PageActions(this, this::notifyListeners);
    private PageConditions _conditions = new PageConditions(this::notifyListeners);

    private transient Set<ChangeEventListener> _listeners = new HashSet<>();

    public final static String URL_PARAM = "url";

    @SuppressWarnings("WeakerAccess")  // discovered and instantiated by reflection (see class ResourceTypes)
    public static class WebPageResourceType extends ResourceType
        {
        public WebPageResourceType()
            {
            super(WebPage.class.getAnnotation(MuseTypeId.class).value(), "Web Page", WebPage.class);
            }

        @Override
        public ResourceDescriptor getDescriptor()
	        {
	        return new DefaultResourceDescriptor(this, "Represents a page loaded in the browser.");
	        }
        }
    }