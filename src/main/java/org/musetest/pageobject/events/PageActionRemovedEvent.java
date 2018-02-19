package org.musetest.pageobject.events;

import org.musetest.core.util.*;
import org.musetest.pageobject.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageActionRemovedEvent extends ChangeEvent
	{
	public PageActionRemovedEvent(WebPage page, PageAction action, String id)
		{
		super(page);
		_page = page;
		_action = action;
		_id = id;
		}

	@Override
	public WebPage getTarget()
		{
		return _page;
		}

	public PageAction getAction()
		{
		return _action;
		}

	public String getId()
		{
		return _id;
		}

	private WebPage _page;
	private PageAction _action;
	private String _id;
	}
