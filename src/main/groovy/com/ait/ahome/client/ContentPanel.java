/*
 * Copyright (c) 2014,2015 Ahome' Innovation Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ait.ahome.client;

import java.util.HashMap;

import com.ait.ahome.client.ui.components.LMPanel;
import com.ait.ahome.client.views.IViewComponent;
import com.ait.ahome.client.views.IViewFactoryCallback;
import com.ait.ahome.client.views.IViewNames;
import com.ait.ahome.client.views.ViewFactoryInstance;
import com.ait.tooling.common.api.java.util.StringOps;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

public class ContentPanel extends LMPanel implements IViewNames
{
    private String                          m_link = null;

    private IViewComponent                  m_last = null;

    private HashMap<String, IViewComponent> m_list = new HashMap<String, IViewComponent>();

    public ContentPanel()
    {
        setAutoScroll(false);

        setTitle("&nbsp;");

        setRegion(BorderRegion.CENTER);

        History.addValueChangeHandler(new ValueChangeHandler<String>()
        {
            @Override
            public void onValueChange(ValueChangeEvent<String> event)
            {
                String link = StringOps.toTrimOrNull(event.getValue());

                if (null != link)
                {
                    if (false == link.equals(m_link))
                    {
                        if (ViewFactoryInstance.get().isDefined(link))
                        {
                            doProcessLink(link);
                        }
                    }
                }
            }
        });
    }

    public final void run()
    {
        String name = StringOps.toTrimOrNull(History.getToken());

        if ((null != name) && (ViewFactoryInstance.get().isDefined(name)))
        {
            History.fireCurrentHistoryState();
        }
        else
        {
            History.newItem(WELCOME);
        }
    }

    private final void doProcessLink(String link)
    {
        m_link = link;

        IViewComponent component = m_list.get(m_link);

        if (null != component)
        {
            doReplaceView(component);
        }
        else
        {
            ViewFactoryInstance.get().make(link, new IViewFactoryCallback()
            {
                @Override
                public void accept(IViewComponent component)
                {
                    doReplaceView(component);
                }
            });
        }
    }

    private final void doReplaceView(IViewComponent component)
    {
        if (null != m_last)
        {
            m_last.setActive(false);

            remove(m_last.asViewComponent());
        }
        add(component.asViewComponent());

        doLayout();

        m_last = component;

        m_last.setActive(true);
    }
}
