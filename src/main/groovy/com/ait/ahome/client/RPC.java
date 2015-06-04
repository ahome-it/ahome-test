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

import com.ait.tooling.gwtdata.client.rpc.JSONCommandRequest;

public final class RPC extends JSONCommandRequest
{
    private static final long serialVersionUID = 5141720871785529694L;

    private static final RPC  INSTANCE         = new RPC();

    public static final RPC get()
    {
        return INSTANCE;
    }

    private RPC()
    {
        super("JSONCommand.rpc", false);
    }
}
