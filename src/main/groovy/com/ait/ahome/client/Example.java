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

package com.ait.ahome.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ait.ahome.client.views.IViewNames;
import com.ait.tooling.common.api.java.util.StringOps;
import com.ait.toolkit.data.client.BaseTreeModel;

public final class Example extends BaseTreeModel implements IViewNames
{
    public static final String             TEXT_FIELD = "text";

    public static BaseTreeModel            m_base     = null;

    private static HashMap<String, String> m_link     = new HashMap<String, String>();

    private static HashMap<String, String> m_text     = new HashMap<String, String>();

    private Example(String text, String link)
    {
        setText(text);

        setLeaf(true);

        if (null != (link = StringOps.toTrimOrNull(link)))
        {
            m_link.put(text, link);

            m_text.put(link, text);
        }
    }

    private Example(String text)
    {
        this(text, null);
    }

    private static final void init()
    {
        if (null == m_base)
        {
            m_base = new BaseTreeModel();

            m_base.setChildren(getExamples());
        }
    }

    public static final String getLinkByText(String text)
    {
        init();

        return m_link.get(text);
    }

    public static final String getTextByLink(String link)
    {
        init();

        return m_text.get(link);
    }

    public static final BaseTreeModel getExamplesTreeModel()
    {
        init();

        return m_base;
    }

    private static final List<Example> getExamples()
    {
        List<Example> examples = new ArrayList<Example>();

        examples.add(getOffsiteLinks());

        examples.add(new Example("Welcome", WELCOME));

        return examples;
    }

    private static final Example getOffsiteLinks()
    {
        Example root = new Example("Offsite Links");

        root.setLeaf(false);

        List<Example> examples = new ArrayList<Example>();

        examples.add(new Example("Tooling Common", "https://github.com/ahome-it/ahome-tooling-common"));
        
        examples.add(new Example("Tooling NativeTools", "https://github.com/ahome-it/ahome-tooling-nativetools"));
        
        examples.add(new Example("Tooling GWTData", "https://github.com/ahome-it/ahome-tooling-gwtdata"));
        
        examples.add(new Example("Tooling JSON", "https://github.com/ahome-it/ahome-tooling-json"));
        
        examples.add(new Example("Tooling Server Core", "https://github.com/ahome-it/ahome-tooling-server-core"));
        
        examples.add(new Example("Tooling Server RPC", "https://github.com/ahome-it/ahome-tooling-server-rpc"));
        
        examples.add(new Example("Tooling Server SQL", "https://github.com/ahome-it/ahome-tooling-server-sql"));
        
        examples.add(new Example("Tooling Server MongoDB", "https://github.com/ahome-it/ahome-tooling-server-mongodb"));
        
        examples.add(new Example("Tooling Server Cache", "https://github.com/ahome-it/ahome-tooling-server-cache"));
        
        examples.add(new Example("Tooling Server Hazelcast", "https://github.com/ahome-it/ahome-tooling-server-hazelcast"));
        
        examples.add(new Example("Lienzo Core", "https://github.com/ahome-it/lienzo-core"));
        
        examples.add(new Example("Lienzo Kitchen Sink", "http://www.lienzo-core.com"));

        root.setChildren(examples);

        return root;
    }
}
