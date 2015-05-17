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

package com.ait.ahome.client.views.components;

import com.ait.ahome.client.RPC;
import com.ait.ahome.client.ui.components.LMButton;
import com.ait.ahome.client.ui.components.LMContainer;
import com.ait.ahome.client.ui.components.LMSimple;
import com.ait.tooling.gwtdata.client.rpc.JSONCommandCallback;
import com.ait.tooling.nativetools.client.NArray;
import com.ait.tooling.nativetools.client.NObject;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.ait.toolkit.sencha.ext.client.layout.VBoxLayout;

public class BuildDescriptorsViewComponent extends AbstractViewComponent
{
    private final LMButton    m_call = new LMButton("Build Descriptors");

    private final LMContainer m_main = new LMContainer(new VBoxLayout());

    public BuildDescriptorsViewComponent()
    {
        m_call.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                RPC.get().call("GetBuildDescriptorsCommand", new JSONCommandCallback()
                {
                    @Override
                    public void onSuccess(final NObject result)
                    {
                        m_main.clear();

                        if (null != result)
                        {
                            final NArray list = result.getAsArray("list");

                            if (null != list)
                            {
                                final int size = list.size();

                                for (int i = 0; i < size; i++)
                                {
                                    final NObject each = list.getAsObject(i);

                                    if (null != each)
                                    {
                                        m_main.add(new LMSimple(i + " ) " + each.toJSONString()));
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        m_call.setWidth(120);

        getToolBarContainer().add(m_call);

        getWorkingContainer().add(m_main);
    }
}
