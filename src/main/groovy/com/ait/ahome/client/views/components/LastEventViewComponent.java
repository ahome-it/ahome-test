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
import java.util.LinkedHashMap;

import com.ait.ahome.client.RPC;
import com.ait.ahome.client.ui.components.LMButton;
import com.ait.ahome.client.ui.components.LMComboBox;
import com.ait.ahome.client.ui.components.LMLabel;
import com.ait.ahome.client.ui.components.LMPanel;
import com.ait.tooling.gwtdata.client.rpc.JSONCommandCallback;
import com.ait.tooling.nativetools.client.NObject;
import com.ait.tooling.nativetools.client.util.Client;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeEvent;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeHandler;

public class LastEventViewComponent extends AbstractViewComponent
{
    private static final long serialVersionUID = 955856801401728068L;

    private int            m_loop = 1;

    private final LMButton m_sets = new LMButton("Set Event");

    private final LMButton m_gets = new LMButton("Get Event");

    private final LMLabel  m_labl = new LMLabel("Status: None");

    private final LMPanel  m_main = new LMPanel();

    private final LMPanel  m_json = new LMPanel();

    public LastEventViewComponent()
    {
        LinkedHashMap<String, String> pick = new LinkedHashMap<String, String>();

        for (int i = 1; i <= 10000000; i *= 10)
        {
            pick.put(i + "", i + "");
        }
        LMComboBox cbox = new LMComboBox(pick);

        cbox.addChangeHandler(new ChangeHandler()
        {
            @Override
            public void onChange(ChangeEvent event)
            {
                m_loop = Integer.parseInt(event.getNewValue());
            }
        });
        getToolBarContainer().add(cbox);

        m_main.setAutoScroll(true);

        m_main.setId("LastEventView");

        m_json.setAutoScroll(true);

        m_main.add(m_json);

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

                        if (null != result)
                        {
                            final StringBuilder builder = new StringBuilder();

                            builder.append("<pre style='color:#0020AD'>");

                            builder.append("/*\n");

                            builder.append(" *\tThis is a JSON representation of the last PubSub Event ");

                            builder.append(new Date().toString());

                            builder.append("\n */\n\n");

                            builder.append(result.toJSONString("\t"));

                            builder.append("</pre>");

                            m_json.setHtml(builder.toString());
                        }
                        else
                        {
                            m_json.setHtml("<pre style='color:#0020AD'>ERROR<pre>");

                            m_labl.setText("Status: GetLastEventCommand.null()");
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

                m_labl.setText("Status: SendEventsCommand.send()");

                NObject send = new NObject();

                send.put("time", (new Date()).toString());

                send.put("uuid", Client.get().getClientUUID());

                send.put("rand", Math.random() * 100.0);

                send.put("loop", m_loop);

                RPC.get().call("SendEventsCommand", send, new JSONCommandCallback()
                {
                    @Override
                    public void onSuccess(final NObject result)
                    {
                        m_gets.enable();

                        m_sets.enable();

                        m_labl.setText("Status: SendEventsCommand.done()");

                        if (null != result)
                        {
                            final StringBuilder builder = new StringBuilder();

                            builder.append("<pre style='color:#0020AD'>");

                            builder.append("/*\n");

                            builder.append(" *\tThis is a JSON representation of the Current PubSub Event ");

                            builder.append(new Date().toString());

                            builder.append("\n */\n\n");

                            builder.append(result.toJSONString("\t"));

                            builder.append("</pre>");

                            m_json.setHtml(builder.toString());
                        }
                        else
                        {
                            m_json.setHtml("<pre style='color:#0020AD'>ERROR<pre>");

                            m_labl.setText("Status: SendEventsCommand.null()");
                        }
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
