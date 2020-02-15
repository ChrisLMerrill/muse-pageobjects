package org.museautomation.pageobject.helpers;

import org.museautomation.core.util.*;

import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ChangeListeners
	{
	public void addListener(ChangeEventListener listener)
		{
		_listeners.add(listener);
		}

	public void removeListener(ChangeEventListener listener)
		{
		_listeners.remove(listener);
		}

	public void raiseEvent(ChangeEvent event)
		{
		for (ChangeEventListener listener : _listeners)
			listener.changeEventRaised(event);
		}

	private Set<ChangeEventListener> _listeners = new HashSet<>();
	}
