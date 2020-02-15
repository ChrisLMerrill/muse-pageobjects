package org.museautomation.pageobject.events;

import org.museautomation.pageobject.*;
import org.museautomation.core.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageActionAddedEvent extends ChangeEvent
	{
	public PageActionAddedEvent(WebPage page, PageAction action, String id)
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
