package org.musetest.pageobject;

import org.junit.*;
import org.musetest.core.util.*;
import org.musetest.pageobject.events.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageActionsTests
	{
	@Test
	public void addAndRemoveAction()
	    {
	    final PageAction action = new PageAction();
	    Assert.assertEquals(0, _actions.getMap().size());

	    final String action1_id = "action1";
	    _actions.addAction(action1_id, action);
	    PageActionAddedEvent add_event = (PageActionAddedEvent) _listener._event;
	    Assert.assertEquals(action1_id, add_event.getId());
	    Assert.assertTrue(action == add_event.getAction());
	    Assert.assertTrue(_page == add_event.getTarget());
	    Assert.assertEquals(1, _actions.getMap().size());

	    _actions.removeAction(action1_id);
	    PageActionRemovedEvent remove_event = (PageActionRemovedEvent) _listener._event;
	    Assert.assertEquals(action1_id, remove_event.getId());
	    Assert.assertTrue(action == remove_event.getAction());
	    Assert.assertTrue(_page == remove_event.getTarget());
	    Assert.assertEquals(0, _actions.getMap().size());
	    }

	@Test
	public void renameAction()
	    {
	    final PageAction action = new PageAction();
	    final String action1_id = "action1";
	    _actions.addAction(action1_id, action);

	    final String new_action_id = "new_action_id";
	    boolean renamed = _actions.renameAction(action1_id, new_action_id);
	    Assert.assertTrue(renamed);
	    PageActionRenamedEvent event = (PageActionRenamedEvent) _listener._event;
	    Assert.assertEquals(new_action_id, event.getNewId());
	    Assert.assertEquals(action1_id, event.getOldId());
	    Assert.assertTrue(_page == event.getTarget());
	    Assert.assertTrue(action == _actions.getAction(new_action_id));
	    Assert.assertEquals(1, _actions.getMap().size());
	    }

	class TestChangeListener implements ChangeEventListener
		{
		@Override
		public void changeEventRaised(ChangeEvent e)
			{
			_event = e;
			}
		public ChangeEvent _event;
		}

	WebPage _page = new WebPage();
	TestChangeListener _listener = new TestChangeListener();
	private PageActions _actions = new PageActions(_page, _listener);
	}
