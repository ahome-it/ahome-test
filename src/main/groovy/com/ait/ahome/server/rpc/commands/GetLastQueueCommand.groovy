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

import org.springframework.stereotype.Service

import com.ait.ahome.server.rpc.LMCommandSupport
import com.ait.tooling.json.JSONObject
import com.ait.tooling.server.core.pubsub.IPubSubHandlerRegistration
import com.ait.tooling.server.core.pubsub.JSONMessage
import com.ait.tooling.server.core.pubsub.support.PubSubTrait
import com.ait.tooling.server.rpc.IJSONRequestContext

@Service
@CompileStatic
public class GetLastQueueCommand extends LMCommandSupport implements PubSubTrait
{
    private JSONObject                  m_payload = json()

    private IPubSubHandlerRegistration  m_handler = null

    @Override
    public JSONObject execute(final IJSONRequestContext context, final JSONObject object) throws Exception
    {
        if (null == m_handler)
        {
            m_handler = addMessageReceivedHandler('JSONCachedQueueEvents') { JSONMessage message ->

                m_payload = message.getPayload()

                logger().info('received ' + m_payload)

                publish('CoreServerEvents', new JSONMessage(m_payload))
                
                logger().info('dispatch ' + m_payload)
            }
        }
        logger().info('sending ' + m_payload)

        m_payload
    }
}
