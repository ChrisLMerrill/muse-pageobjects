package org.musetest.pageobject;

import org.musetest.core.util.*;
import org.musetest.pageobject.events.*;
import org.musetest.pageobject.helpers.*;

import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageConditions
	{
	PageConditions(ChangeEventListener listener)
		{
		_listeners.addListener(listener);
		}

	public boolean add(String id, PageCondition condition)
		{
		if (_map.keySet().contains(id))
			return false;
		_map.put(id, condition);
		listeners().raiseEvent(new PageConditionAddedEvent(this, id, condition));
		return true;
		}

	public PageCondition remove(String id)
		{
		final PageCondition removed = _map.remove(id);
		if (removed != null)
			listeners().raiseEvent(new PageConditionRemovedEvent(this, id, removed));
		return removed;
		}

	public boolean rename(String old_id, String new_id)
		{
		if (!_map.containsKey(old_id) || _map.containsKey(new_id))
			return false;

		_map.put(new_id, _map.remove(old_id));
		listeners().raiseEvent(new PageConditionRenamedEvent(this, old_id, new_id));

		return true;
		}

	public PageCondition get(String id)
		{
		return _map.get(id);
		}

	public Map<String, PageCondition> getMap()
		{
		return _map;
		}

	public void setMap(Map<String, PageCondition> map)
		{
		_map = map;
		}

	public ChangeListeners listeners()
		{
		return _listeners;
		}

	private Map<String, PageCondition> _map = new HashMap<>();
	private ChangeListeners _listeners = new ChangeListeners();
	}
