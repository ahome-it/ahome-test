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

package com.ait.ahome.client.views;

import com.ait.ahome.client.ui.components.LMPanel;
import com.ait.ahome.client.views.components.WelcomeViewComponent;
import com.ait.tooling.nativetools.client.collection.NFastStringMap;
import com.ait.toolkit.sencha.ext.client.core.Component;

public final class ViewFactoryInstance implements IViewNames
{
    private static final ViewFactoryInstance   INSTANCE    = new ViewFactoryInstance();

    private final NFastStringMap<IViewFactory> m_factories = new NFastStringMap<IViewFactory>();

    public final static ViewFactoryInstance get()
    {
        return INSTANCE;
    }

    private ViewFactoryInstance()
    {
        put(WELCOME, new IViewFactory()
        {
            @Override
            public void make(IViewFactoryCallback callback)
            {
                callback.accept(new WelcomeViewComponent());
            }
        });
    }

    protected final void put(String link, IViewFactory fact)
    {
        m_factories.put(link, fact);
    }

    public final void make(String link, IViewFactoryCallback callback)
    {
        IViewFactory factory = m_factories.get(link);

        if (null != factory)
        {
            factory.make(callback);
        }
        else
        {
            callback.accept(new ErrorView());
        }
    }

    public final boolean isDefined(String link)
    {
        return m_factories.isDefined(link);
    }

    private static final class ErrorView extends LMPanel implements IViewComponent
    {
        @Override
        public Component asViewComponent()
        {
            return this;
        }

        @Override
        public boolean isActive()
        {
            return false;
        }

        @Override
        public boolean setActive(boolean active)
        {
            return false;
        }
    }
}
