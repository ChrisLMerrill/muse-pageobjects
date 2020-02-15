package org.museautomation.pageobject.events;

import org.museautomation.pageobject.*;
import org.museautomation.core.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageActionRenamedEvent extends ChangeEvent
	{
	public PageActionRenamedEvent(WebPage page, PageAction action, String old_id, String new_id)
		{
		super(page);
		_page = page;
		_action = action;
		_old_id = old_id;
		_new_id = new_id;
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

	public String getOldId()
		{
		return _old_id;
		}

	public String getNewId()
		{
		return _new_id;
		}

	private WebPage _page;
	private PageAction _action;
	private String _old_id;
	private String _new_id;
	}