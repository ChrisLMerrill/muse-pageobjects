package org.museautomation.pageobject.events;

import org.museautomation.pageobject.*;
import org.museautomation.core.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class FunctionChangedEvent extends ChangeEvent
	{
	public FunctionChangedEvent(PageAction action, String old_function_id, String new_function_id)
		{
		super(action);
		_old_function_id = old_function_id;
		_new_function_id = new_function_id;
		}

	public String getOldFunctionId()
		{
		return _old_function_id;
		}

	public String getNewFunctionId()
		{
		return _new_function_id;
		}

	private final String _old_function_id;
	private final String _new_function_id;
	}
