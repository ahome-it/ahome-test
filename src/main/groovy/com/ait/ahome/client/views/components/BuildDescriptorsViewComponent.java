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
import com.ait.ahome.client.ui.components.LMPanel;
import com.ait.tooling.gwtdata.client.rpc.JSONCommandCallback;
import com.ait.tooling.nativetools.client.NObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class BuildDescriptorsViewComponent extends AbstractViewComponent
{
    private static final long serialVersionUID = -2145509615066280125L;

    private final LMPanel     m_main           = new LMPanel();

    private final LMPanel     m_json           = new LMPanel();

    public BuildDescriptorsViewComponent()
    {
        m_main.setAutoScroll(true);

        m_main.setId("BuildDescriptors");

        m_json.setAutoScroll(true);

        m_main.add(m_json);

        Scheduler.get().scheduleFinally(new ScheduledCommand()
        {
            @Override
            public void execute()
            {
                RPC.get().call("GetBuildDescriptorsCommand", new JSONCommandCallback()
                {
                    @Override
                    public void onSuccess(final NObject result)
                    {
                        if (null != result)
                        {
                            final StringBuilder builder = new StringBuilder();

                            builder.append("<pre style='color:#0020AD'>");

                            builder.append("/*\n");

                            builder.append(" *\tThis is a JSON representation of the list of Build Descriptors ");

                            builder.append(new Date().toString());

                            builder.append("\n */\n\n");

                            builder.append(result.toJSONString("\t"));

                            builder.append("</pre>");

                            m_json.setHtml(builder.toString());
                        }
                        else
                        {
                            m_json.setHtml("<pre style='color:#0020AD'>ERROR<pre>");
                        }
                    }
                });
            }
        });
        getWorkingContainer().set(m_main);
    }
}
