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

package com.ait.ahome.client.views;

import java.util.Objects;

import com.ait.ahome.client.ui.components.LMPanel;
import com.ait.ahome.client.views.components.BuildDescriptorsViewComponent;
import com.ait.ahome.client.views.components.WelcomeViewComponent;
import com.ait.tooling.common.api.java.util.StringOps;
import com.ait.tooling.nativetools.client.collection.NFastStringMap;
import com.ait.toolkit.sencha.ext.client.core.Component;

public final class ViewFactoryInstance implements IViewNames
{
    private static ViewFactoryInstance         INSTANCE;

    private final NFastStringMap<IViewFactory> m_factories = new NFastStringMap<IViewFactory>();

    public static final ViewFactoryInstance get()
    {
        if (null == INSTANCE)
        {
            INSTANCE = new ViewFactoryInstance();
        }
        return INSTANCE;
    }

    private ViewFactoryInstance()
    {
        put(WELCOME, new IViewFactory()
        {
            @Override
            public void make(final IViewFactoryCallback callback)
            {
                callback.accept(new WelcomeViewComponent());
            }
        });
        put(BUILD_DESCRIPTORS, new IViewFactory()
        {
            @Override
            public void make(final IViewFactoryCallback callback)
            {
                callback.accept(new BuildDescriptorsViewComponent());
            }
        });
    }

    protected final void put(final String link, final IViewFactory fact)
    {
        m_factories.put(StringOps.requireTrimOrNull(link), Objects.requireNonNull(fact));
    }

    public final void make(final String link, final IViewFactoryCallback callback)
    {
        Objects.requireNonNull(callback);

        final String name = StringOps.toTrimOrNull(link);

        if (null != name)
        {
            final IViewFactory factory = m_factories.get(name);

            if (null != factory)
            {
                factory.make(callback);

                return;
            }
        }
        callback.accept(new ErrorView());
    }

    public final boolean isDefined(final String link)
    {
        final String name = StringOps.toTrimOrNull(link);

        if (null != name)
        {
            return m_factories.isDefined(name);
        }
        return false;
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
