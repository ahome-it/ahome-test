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

import com.ait.ahome.client.ui.components.LMContainer;
import com.ait.ahome.client.ui.components.LMSimple;
import com.ait.toolkit.sencha.ext.client.layout.Align;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.layout.HBoxLayout;

public class HeaderPanel extends LMContainer
{
    public HeaderPanel()
    {
        setId("HeaderPanel");

        setRegion(BorderRegion.NORTH);

        setHeight(60);

        LMContainer inside = new LMContainer(new HBoxLayout(Align.MIDDLE));

        inside.setId("HeaderPanel-Inside");

        inside.setHeight(60);

        LMSimple title = new LMSimple("Ahome Library Test 1.0.24", 1);

        title.setId("HeaderPanel-Title");

        inside.add(title);

        add(inside);
    }
}
