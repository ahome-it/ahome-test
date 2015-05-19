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
import com.ait.ahome.client.ui.components.LMLabel;
import com.ait.ahome.client.ui.components.LMPanel;
import com.ait.tooling.gwtdata.client.rpc.JSONCommandCallback;
import com.ait.tooling.nativetools.client.NObject;
import com.ait.tooling.nativetools.client.util.Client;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.google.gwt.user.client.ui.HTML;

public class LastEventViewComponent extends AbstractViewComponent
{
    private final LMButton m_sets = new LMButton("Set Event");

    private final LMButton m_gets = new LMButton("Get Event");

    private final LMLabel  m_labl = new LMLabel("Status: None");

    private final LMPanel  m_main = new LMPanel();

    private LMPanel        m_json = null;

    public LastEventViewComponent()
    {
        m_main.setAutoScroll(true);

        m_main.setId("LastEventView");

        m_gets.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                m_sets.disable();

                m_gets.disable();

                m_labl.setText("Status: GetLastEventCommand.send()");

                RPC.get().call("GetLastEventCommand", new JSONCommandCallback()
                {
                    @Override
                    public void onSuccess(final NObject result)
                    {
                        m_sets.enable();

                        m_gets.enable();

                        m_labl.setText("Status: GetLastEventCommand.done()");

                        if (null != m_json)
                        {
                            m_main.removeAll(true);

                            m_json = null;
                        }
                        if (null != result)
                        {
                            m_json = new LMPanel();

                            m_json.setAutoScroll(true);

                            final StringBuilder builder = new StringBuilder();

                            builder.append("<pre style='color:#0020AD'>");

                            builder.append("/*\n");

                            builder.append(" *\tThis is a JSON representation of the last PubSub Event ");

                            builder.append(new Date().toString());

                            builder.append("\n */\n\n");

                            builder.append(result.toJSONString("\t"));

                            builder.append("</pre>");

                            m_json.add(new HTML(builder.toString()));

                            m_main.add(m_json);

                            m_main.update();

                            getWorkingContainer().update();
                        }
                    }
                });
            }
        });
        m_gets.setWidth(120);

        m_sets.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                m_sets.disable();

                m_gets.disable();

                m_labl.setText("Status: SetLastEventCommand.send()");

                NObject send = new NObject();

                send.put("time", (new Date()).toString());

                send.put("uuid", Client.get().getClientUUID());

                send.put("rand", Math.random() * 100.0);

                RPC.get().call("SetLastEventCommand", send, new JSONCommandCallback()
                {
                    @Override
                    public void onSuccess(final NObject result)
                    {
                        m_gets.enable();

                        m_sets.enable();

                        m_labl.setText("Status: SetLastEventCommand.done()");
                    }
                });
            }
        });
        m_sets.setWidth(120);

        m_labl.setWidth(400);

        getToolBarContainer().add(m_gets);

        getToolBarContainer().add(m_sets);

        getToolBarContainer().add(m_labl);

        getWorkingContainer().set(m_main);
    }
}
