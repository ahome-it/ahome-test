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

import org.apache.log4j.Logger
import org.springframework.stereotype.Service

import com.ait.tooling.common.api.java.util.UUID
import com.ait.tooling.json.JSONObject
import com.ait.tooling.server.hazelcast.support.HazelcastSupport
import com.ait.tooling.server.rpc.IJSONRequestContext
import com.ait.tooling.server.rpc.JSONCommandSupport

@Service
@CompileStatic
public class SetLastCacheCommand extends JSONCommandSupport
{
    private static final Logger         logger = Logger.getLogger(SetLastCacheCommand.class)

    @Override
    public JSONObject execute(final IJSONRequestContext context, final JSONObject object) throws Exception
    {
        Map<String, JSONObject> hmap = HazelcastSupport.getHazelcastSupport().getHazelcastInstance().getMap('default')

        if (hmap)
        {
            hmap.set(UUID.uuid(), object)
        }
        else
        {
            logger.error('No default map found')
        }
        json()
    }
}
