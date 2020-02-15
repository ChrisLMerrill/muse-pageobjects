package org.museautomation.pageobject;

import org.museautomation.pageobject.events.*;
import org.museautomation.core.util.*;

import java.util.*;

/**
 * Container for PageAction objects. Controls access to the underlying map and raises ChangeEvents in response
 * to modifications.
 *
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageActions
	{
	PageActions(WebPage page, ChangeEventListener listener)
		{
		_page = page;
		_listener = listener;
		}

	public void setMap(Map<String, PageAction> map)
		{
		if (_map == null)
			_map = new HashMap<>();
		else
			_map = map;
		}

	public Map<String, PageAction> getMap()
		{
		return _map;
		}

	public void addAction(String id, PageAction action)
		{
		if (_map == null)
			_map = new HashMap<>();
		_map.put(id, action);
		_listener.changeEventRaised(new PageActionAddedEvent(_page, action, id));
		}

	public PageAction removeAction(String id)
		{
		if (_map == null)
			return null;

		final PageAction removed = _map.remove(id);
		if (removed != null)
			_listener.changeEventRaised(new PageActionRemovedEvent(_page, removed, id));
		return removed;
		}

	public boolean renameAction(String old_id, String new_id)
		{
		PageAction action = _map.remove(old_id);
		if (action == null)
			return false;
		_map.put(new_id, action);
		_listener.changeEventRaised(new PageActionRenamedEvent(_page, action, old_id, new_id));
		return true;
		}

	public PageAction getAction(String id)
		{
		return _map.get(id);
		}

	private final WebPage _page;
	private final ChangeEventListener _listener;

	private Map<String, PageAction> _map = new HashMap<>();
	}
