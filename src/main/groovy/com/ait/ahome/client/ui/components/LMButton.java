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

package com.ait.ahome.client.ui.components;

import com.ait.tooling.nativetools.client.security.XSS;
import com.ait.toolkit.sencha.ext.client.ui.Button;

public class LMButton extends Button
{
    public LMButton(String label)
    {
        super(label);
    }

    @Override
    public void setText(String text)
    {
        super.setText(XSS.get().clean(text));
    }
}