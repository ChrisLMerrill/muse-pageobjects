package org.museautomation.pageobject;

import org.museautomation.core.*;
import org.museautomation.core.values.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@MuseTypeId("element")
public class PageElement
    {
    public ValueSourceConfiguration getLocator()
        {
        return _locator;
        }

    public void setLocator(ValueSourceConfiguration locator)
        {
        _locator = locator;
        }

    private ValueSourceConfiguration _locator;
    }