package org.musetest.pageobject;

import org.musetest.core.*;
import org.musetest.core.resource.generic.*;
import org.musetest.core.resource.types.*;
import org.musetest.core.util.*;
import org.musetest.core.values.*;
import org.musetest.core.values.descriptor.*;
import org.musetest.core.values.events.*;

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
        return Collections.unmodifiableMap(_elements);
        }

    @SuppressWarnings("unused")  // required for Json de/serialization
    public void setElements(Map<String, PageElement> elements)
        {
        _elements = elements;
        }

    public void addElement(String id, PageElement element)
        {
        if (_elements == null)
            _elements = new HashMap<>();
        _elements.put(id, element);
        }

    @SuppressWarnings("unused") // used by UI
    public ContainsNamedSources namedElementLocators()
        {
        return new NamedElementLocators();
        }

    @SuppressWarnings("unused,WeakerAccess")  // required for Json de/serialization
    public Map<String, PageAction> getActions()
        {
        return Collections.unmodifiableMap(_actions);
        }

    @SuppressWarnings("unused")  // required for Json de/serialization
    public void setActions(Map<String, PageAction> actions)
        {
        _actions = actions;
        }

    public void addAction(String id, PageAction action)
        {
        if (_actions == null)
	        _actions = new HashMap<>();
        _actions.put(id, action);
        }

    @Override
    public ResourceType getType()
        {
        return new WebPageResourceType();
        }

    private Map<String, PageElement> _elements = new HashMap<>();
    private Map<String, PageAction> _actions = new HashMap<>();

    public final static String URL_PARAM = "url";

    /**
     * Provides a facade for the element locator sources in the page. This facade makes the element/locator
     * pairs editable with a ValueSourceMapEditor.
     */
    private class NamedElementLocators implements ContainsNamedSources
        {
        @Override
        public ValueSourceConfiguration getSource(String name)
            {
            PageElement element = _elements.get(name);
            if (element == null)
                return null;
            return element.getLocator();
            }

        @Override
        public void addSource(String name, ValueSourceConfiguration source)
            {
            PageElement element = _elements.get(name);
            if (element != null)
                throw new IllegalArgumentException(String.format("Element %s already exists in page. Cannot add it.", name));
            element = new PageElement();
            element.setLocator(source);
            _elements.put(name, element);
            raiseEvent(new NamedSourceAddedEvent(this, name, source));
            }

        @Override
        public ValueSourceConfiguration removeSource(String name)
            {
            PageElement element = _elements.remove(name);
            if (element == null)
                throw new IllegalArgumentException(String.format("Element %s does not exist in the page. Cannot remove it.", name));
            ValueSourceConfiguration source = element.getLocator();
            raiseEvent(new NamedSourceRemovedEvent(this, name, source));
            return source;
            }

        @Override
        public boolean renameSource(String old_name, String new_name)
            {
            if (_elements.get(new_name) != null)
                throw new IllegalArgumentException(String.format("Element %s already exists in page. Cannot rename %s to %s", new_name, old_name, new_name));
            PageElement element = _elements.remove(old_name);
            if (element == null)
                throw new IllegalArgumentException(String.format("Element %s does not exist in page. Cannot rename it", old_name));

            _elements.put(new_name, element);
            raiseEvent(new NamedSourceRenamedEvent(this, new_name, old_name, element.getLocator()));
            return true;
            }

        @Override
        public ValueSourceConfiguration replaceSource(String name, ValueSourceConfiguration new_source)
            {
            PageElement element = _elements.get(name);
            if (element == null)
                throw new IllegalArgumentException(String.format("Element %s does not exist in page. Cannot replace it", name));

            ValueSourceConfiguration old_source = element.getLocator();
            element.setLocator(new_source);
            raiseEvent(new NamedSourceReplacedEvent(this, name, old_source, new_source));
            return old_source;
            }

        @Override
        public Set<String> getSourceNames()
            {
            return _elements.keySet();
            }

        @Override
        public void addChangeListener(ChangeEventListener listener)
            {
            if (_listeners.contains(listener))
                return;
            _listeners.add(listener);
            }

        @Override
        public boolean removeChangeListener(ChangeEventListener listener)
            {
            return _listeners.remove(listener);
            }

        private void raiseEvent(ChangeEvent event)
            {
            for (ChangeEventListener listener : _listeners)
                listener.changeEventRaised(event);
            }

        List<ChangeEventListener> _listeners = new ArrayList<>();
        }

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