package org.musetest.pageobject;

import org.musetest.core.*;
import org.musetest.core.values.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@MuseTypeId("page-condition")
public class PageCondition
    {
    public ValueSourceConfiguration getCondition()
        {
        return _condition;
        }

    public void setCondition(ValueSourceConfiguration condition)
        {
        _condition = condition;
        }

    private ValueSourceConfiguration _condition;
    }