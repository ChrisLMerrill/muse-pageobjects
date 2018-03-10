package org.musetest.pageobject.events;

import org.musetest.core.util.*;
import org.musetest.pageobject.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageConditionRenamedEvent extends ChangeEvent
	{
	public PageConditionRenamedEvent(PageConditions target, String old_id, String new_id)
		{
		super(target);
		_old_id = old_id;
		_new_id = new_id;
		}

	@Override
	public PageConditions getTarget()
		{
		return (PageConditions) super.getTarget();
		}

	public String getOldId()
		{
		return _old_id;
		}

	public String getNewId()
		{
		return _new_id;
		}

	private String _old_id;
	private String _new_id;
	}
