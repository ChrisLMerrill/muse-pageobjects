package org.museautomation.pageobject;

import org.museautomation.pageobject.events.*;
import org.museautomation.core.util.*;
import org.museautomation.core.values.*;

import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageAction
	{
	public String getFunction()
		{
		return _function_id;
		}

	public void setFunction(String function_id)
		{
		String old_function_id = _function_id;
		_function_id = function_id;
		notifyListeners(new FunctionChangedEvent(this, old_function_id, function_id));
		}

	public String getDestinationPage()
		{
		return _destination_page_id;
		}

	public void setDestinationPage(String destination_page_id)
		{
		_destination_page_id = destination_page_id;
		}

	public ContainsNamedSources defaultParameters()
		{
		return _param_defaults;
		}

	public Map<String, ValueSourceConfiguration> getDefaultParameters()
		{
		return _param_defaults.getSourceMap();
		}

	public void setDefaultParameters(Map<String, ValueSourceConfiguration> default_parameters)
		{
		_param_defaults.setSourceMap(default_parameters);
		}

	public Set<String> exposedParameters()
		{
		return _exposed_params;
		}

	public Set<String> getExposedParameters()
		{
		return _exposed_params;
		}

	public void setExposedParameters(Set<String> exposed_parameters)
		{
		_exposed_params = exposed_parameters;
		}

	public void addChangeListener(ChangeEventListener listener)
		{
		_listeners.add(listener);
		}

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
	public boolean equals(Object obj)
		{
		if (!(obj instanceof PageAction))
			return false;

		PageAction other = (PageAction) obj;
		return Objects.equals(_function_id, other._function_id)
			&& Objects.equals(_destination_page_id, other._destination_page_id)
			&& Objects.equals(_exposed_params, other._exposed_params);
		}

	private String _function_id;
	private String _destination_page_id;
	private NamedSourcesContainer _param_defaults = new NamedSourcesContainer();
	private Set<String> _exposed_params = new HashSet<>();

	private Set<ChangeEventListener> _listeners = new HashSet<>();
	}
