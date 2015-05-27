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

package com.ait.ahome.server.rpc

import groovy.transform.CompileStatic

import com.ait.tooling.json.JSONObject
import com.ait.tooling.server.core.pubsub.IPubSubHandlerRegistration
import com.ait.tooling.server.core.pubsub.IPubSubMessageReceivedHandler
import com.ait.tooling.server.core.pubsub.PubSubChannelType
import com.ait.tooling.server.hazelcast.support.HazelcastSupport
import com.ait.tooling.server.rpc.JSONCommandSupport
import com.hazelcast.core.IMap

@CompileStatic
public abstract class LMCommandSupport extends JSONCommandSupport
{
    public IMap<String, JSONObject> getJSONCachedMap(String name)
    {
        IMap<String, JSONObject> imap

        if (getApplicationContext().containsBean(Objects.requireNonNull(name)))
        {
            logger().info("Cache bean ${name} exists")

            try
            {
                imap = getBean(name, IMap.class)

                if (null == imap)
                {
                    logger().info("Cache first bean ${name} null")
                }
            }
            catch (Exception e)
            {
                logger().error("Got error first getBean() Cache bean ${name}", e)
            }
            if (null == imap)
            {
                logger().info("Cache bean ${name} second try")

                try
                {
                    imap = ((IMap<String, JSONObject>)getApplicationContext().getBean(name))

                    if (null == imap)
                    {
                        logger().info("Cache second bean ${name} null")
                    }
                }
                catch (Exception e)
                {
                    logger().error("Got error second getBean() Cache bean ${name}", e)
                }
            }
        }
        else
        {
            logger().info("Cache bean ${name} not a bean")
        }
        if (null == imap)
        {
            logger().info("Trying Instance")

            imap = HazelcastSupport.getHazelcastSupport().getHazelcastInstance().getMap(name)
        }
        if (null == imap)
        {
            logger().info("Cache returning bean ${name} null")
        }
        return imap
    }
}
