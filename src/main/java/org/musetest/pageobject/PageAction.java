package org.musetest.pageobject;

import org.musetest.core.values.*;

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
		_function_id = function_id;
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
	}
