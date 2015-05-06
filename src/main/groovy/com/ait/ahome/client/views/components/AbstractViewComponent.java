/*
   Copyright (c) 2014,2015 Ahome' Innovation Technologies. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.ait.ahome.client.views.components;

import com.ait.ahome.client.ui.components.LMContainer;
import com.ait.ahome.client.ui.components.LMToolBar;
import com.ait.ahome.client.views.IViewComponent;
import com.ait.toolkit.sencha.ext.client.core.Component;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.layout.Layout;

public abstract class AbstractViewComponent extends LMContainer implements IViewComponent
{
    private boolean           m_active = false;

    private final LMToolBar   m_tool   = new LMToolBar();

    private final LMContainer m_work   = new LMContainer();

    public AbstractViewComponent()
    {
        setAutoScroll(false);

        setRegion(BorderRegion.CENTER);

        setLayout(Layout.FIT);

        LMContainer main = new LMContainer(Layout.BORDER);

        m_tool.setRegion(BorderRegion.NORTH);

        m_tool.setHeight(30);

        main.add(m_tool);

        m_work.setRegion(BorderRegion.CENTER);

        main.add(m_work);

        add(main);
    }

    @Override
    public Component asViewComponent()
    {
        return this;
    }

    @Override
    public boolean isActive()
    {
        return m_active;
    }

    @Override
    public boolean setActive(final boolean active)
    {
        if (active != m_active)
        {
            m_active = active;

            return true;
        }
        return false;
    }

    public LMToolBar getToolBarContainer()
    {
        return m_tool;
    }

    public LMContainer getWorkingContainer()
    {
        return m_work;
    }
}
