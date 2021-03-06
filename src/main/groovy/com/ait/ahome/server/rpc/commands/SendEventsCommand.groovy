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

package com.ait.ahome.server.rpc.commands

import groovy.transform.CompileStatic

import org.springframework.integration.channel.PublishSubscribeChannel
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

import com.ait.tooling.server.core.json.JSONObject
import com.ait.tooling.server.core.pubsub.JSONMessageBuilder
import com.ait.tooling.server.rpc.IJSONRequestContext
import com.ait.tooling.server.rpc.JSONCommandSupport

@Service
@CompileStatic
public class SendEventsCommand extends JSONCommandSupport
{
    @Override
    public JSONObject execute(final IJSONRequestContext context, final JSONObject object) throws Exception
    {
        final PublishSubscribeChannel channel = getPublishSubscribeChannel('CoreServerEvents')

        final Message<JSONObject> message = JSONMessageBuilder.createMessage(object)

        final int loop = object.getAsInteger('loop')

        final long time = System.currentTimeMillis()

        for (int i = 0; i < loop; i++)
        {
            channel.send(message)
        }
        object.set('took', Long.toString(System.currentTimeMillis() - time))

        object
    }
}
