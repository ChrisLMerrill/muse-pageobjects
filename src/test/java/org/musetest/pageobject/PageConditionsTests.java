package org.musetest.pageobject;

import org.junit.*;
import org.musetest.core.util.*;
import org.musetest.pageobject.events.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class PageConditionsTests
	{
	@Test
	public void addAndRemoveConditions()
	    {
	    Assert.assertEquals(0, _conditions.getMap().size());

	    PageCondition condition = new PageCondition();
	    String condition_id = "cond#1";

	    _conditions.add(condition_id, condition);
	    Assert.assertEquals(1, _conditions.getMap().size());
	    PageConditionAddedEvent add_event = (PageConditionAddedEvent) _listener._event;
	    Assert.assertEquals(condition, add_event.getCondition());
	    Assert.assertEquals(condition_id, add_event.getId());
	    Assert.assertEquals(_conditions, add_event.getTarget());

	    PageCondition removed = _conditions.remove(condition_id);
	    Assert.assertTrue(condition == removed);
	    Assert.assertEquals(0, _conditions.getMap().size());
	    PageConditionRemovedEvent remove_event = (PageConditionRemovedEvent) _listener._event;
	    Assert.assertEquals(condition, remove_event.getCondition());
	    Assert.assertEquals(condition_id, remove_event.getId());
	    Assert.assertEquals(_conditions, add_event.getTarget());

	    _listener._event = null;
	    _conditions.listeners().removeListener(_listener);
	    _conditions.add(condition_id, condition);
	    Assert.assertNull(_listener._event);
	    }

	@Test
	public void renameCondition()
	    {
	    PageCondition condition = new PageCondition();
	    _conditions.add("cid1", condition);

	    _conditions.rename("cid1", "cid2");
	    Assert.assertTrue(condition == _conditions.get("cid2"));
	    Assert.assertEquals(1, _conditions.getMap().size());
	    Assert.assertNull(_conditions.get("cid1"));

	    PageConditionRenamedEvent rename_event = (PageConditionRenamedEvent) _listener._event;
	    Assert.assertEquals("cid1", rename_event.getOldId());
	    Assert.assertEquals("cid2", rename_event.getNewId());
	    Assert.assertEquals(_conditions, rename_event.getTarget());
	    }

	@Test
	public void addDuplicate()
	    {
	    boolean success = _conditions.add("id1", new PageCondition());
	    Assert.assertTrue(success);
	    Assert.assertEquals(1, _conditions.getMap().size());

	    success = _conditions.add("id1", new PageCondition());
	    Assert.assertFalse(success);
	    Assert.assertEquals(1, _conditions.getMap().size());
	    }

	@Test
	public void renameNonexistent()
	    {
	    Assert.assertFalse(_conditions.rename("blah", "whocares"));
	    }

	@Test
	public void renameToDuplicate()
	    {
	    _conditions.add("c1", new PageCondition());
	    _conditions.add("c2", new PageCondition());
	    Assert.assertFalse(_conditions.rename("c2", "c1"));
	    }

	@Test
	public void removeNonexistent()
	    {
	    Assert.assertNull(_conditions.remove("blah"));
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

	private TestChangeListener _listener = new TestChangeListener();
	private PageConditions _conditions = new PageConditions(_listener);
	}
