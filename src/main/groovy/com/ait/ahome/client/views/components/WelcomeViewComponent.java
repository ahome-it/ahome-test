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

import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.SCALE;
import static com.ait.lienzo.client.core.animation.AnimationTweener.LINEAR;

import com.ait.ahome.client.RPC;
import com.ait.ahome.client.ui.components.LMButton;
import com.ait.ahome.client.ui.style.LMClientBundle;
import com.ait.lienzo.client.core.animation.AnimationCallback;
import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.image.ImageLoader;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.PatternGradient;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.FillRepeat;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;
import com.ait.tooling.gwtdata.client.rpc.JSONCommandCallback;
import com.ait.tooling.nativetools.client.NObject;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.google.gwt.dom.client.ImageElement;

public class WelcomeViewComponent extends AbstractLienzoViewComponent
{
    private final Text     m_banner = getText("Welcome");

    private final LMButton m_mongod = new LMButton("MongoDB");

    private final LMButton m_postsq = new LMButton("PostgreSQL");

    public WelcomeViewComponent()
    {
        m_mongod.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                RPC.get().call("MongoDBCommand", new JSONCommandCallback()
                {
                    @Override
                    public void onSuccess(final NObject result)
                    {
                        m_banner.setText(result.toJSONString());

                        m_banner.getLayer().batch();
                    }
                });
            }
        });
        m_mongod.setWidth(90);

        getToolBarContainer().add(m_mongod);

        m_postsq.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                RPC.get().call("SQLCommand", new JSONCommandCallback()
                {
                    @Override
                    public void onSuccess(final NObject result)
                    {
                        m_banner.setText(result.toJSONString());

                        m_banner.getLayer().batch();
                    }
                });
            }
        });
        m_postsq.setWidth(90);

        getToolBarContainer().add(m_postsq);

        final Layer layer = new Layer();

        new ImageLoader(LMClientBundle.INSTANCE.crosshatch())
        {
            @Override
            public void onLoad(ImageElement image)
            {
                m_banner.setFillGradient(new PatternGradient(image, FillRepeat.REPEAT)).setFillAlpha(0.50);

                layer.batch();
            }

            @Override
            public void onError(String message)
            {
            }
        };
        m_banner.setFillColor(ColorName.WHITE).setFillAlpha(0.20);

        layer.add(m_banner);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    private final static Text getText(String label)
    {
        return new Text(label).setStrokeWidth(3).setFontSize(48).setFontStyle("bold").setStrokeColor(ColorName.WHITE).setX(25).setY(50).setTextAlign(TextAlign.LEFT).setTextBaseLine(TextBaseLine.MIDDLE).setDraggable(true);
    }

    @Override
    public boolean setActive(boolean active)
    {
        if (super.setActive(active))
        {
            m_banner.getLayer().setListening(false);

            m_banner.animate(LINEAR, AnimationProperties.toPropertyList(SCALE(1, -1)), 500, new AnimationCallback()
            {
                @Override
                public void onClose(IAnimation animation, IAnimationHandle handle)
                {
                    m_banner.animate(LINEAR, AnimationProperties.toPropertyList(SCALE(1, 1)), 500, new AnimationCallback()
                    {
                        @Override
                        public void onClose(IAnimation animation, IAnimationHandle handle)
                        {
                            m_banner.getLayer().setListening(true);

                            m_banner.getLayer().draw();
                        }
                    });
                }
            });
            return true;
        }
        return false;
    }
}
