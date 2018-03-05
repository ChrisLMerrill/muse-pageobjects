package org.musetest.pageobject;

import org.musetest.core.util.*;
import org.musetest.core.values.*;
import org.musetest.core.values.events.*;

import java.util.*;

/**
 * Holds the PageElements for a page.
 * <p>
 * Also provides a facade for the element locator sources in the page. This facade makes the element/locator
 * pairs editable with a ValueSourceMapEditor.
 *
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageElements implements ContainsNamedSources
	{
	public PageElements(ChangeEventListener listener)
		{
		_listeners.add(listener);
		}

	public Map<String, PageElement> getElementMap()
		{
		return _elements;
		}

	public void setElementMap(Map<String, PageElement> elements)
		{
		_elements = elements;
		}

	public void addElement(String id, PageElement element)
		{
		if (_elements == null)
			_elements = new HashMap<>();
		_elements.put(id, element);
		}

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

	private Map<String, PageElement> _elements = new HashMap<>();
	}
