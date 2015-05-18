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

import java.util.Date;

import com.ait.ahome.client.RPC;
import com.ait.ahome.client.ui.components.LMButton;
import com.ait.ahome.client.ui.components.LMPanel;
import com.ait.tooling.gwtdata.client.rpc.JSONCommandCallback;
import com.ait.tooling.nativetools.client.NObject;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.google.gwt.user.client.ui.HTML;

public class BuildDescriptorsViewComponent extends AbstractViewComponent
{
    private final LMButton m_call = new LMButton("Build Descriptors");

    private final LMPanel  m_main = new LMPanel();

    private LMPanel        m_json = null;

    public BuildDescriptorsViewComponent()
    {
        m_main.setAutoScroll(true);

        m_main.setId("BuildDescriptors");

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
                        if (null != m_json)
                        {
                            m_main.remove(m_json, true);

                            m_json = null;
                        }
                        if (null != result)
                        {
                            m_json = new LMPanel();

                            m_json.setAutoScroll(true);

                            final StringBuilder builder = new StringBuilder();

                            builder.append("<pre>");

                            builder.append("/*\n");

                            builder.append(" *\tThis is a JSON representation of the list of Build Descriptors ");
                            
                            builder.append(new Date().toString());

                            builder.append("\n */\n\n");

                            builder.append(result.toJSONString("\t"));

                            builder.append("</pre>");

                            m_json.add(new HTML(builder.toString()));

                            m_main.add(m_json);
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
