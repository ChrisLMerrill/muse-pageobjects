package org.musetest.pageobject.events;

import org.musetest.core.util.*;
import org.musetest.pageobject.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageConditionRemovedEvent extends ChangeEvent
	{
	public PageConditionRemovedEvent(PageConditions target, String id, PageCondition condition)
		{
		super(target);
		_id = id;
		_condition = condition;
		}

	@Override
	public PageConditions getTarget()
		{
		return (PageConditions) super.getTarget();
		}

	public String getId()
		{
		return _id;
		}

	public PageCondition getCondition()
		{
		return _condition;
		}

	private String _id;
	private PageCondition _condition;
	}
