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

package com.ait.ahome.server.security

import com.ait.tooling.json.JSONObject
import com.ait.tooling.server.core.security.session.IServerSession
import com.ait.tooling.server.core.security.session.IServerSessionRepository
import com.ait.tooling.server.core.security.session.ISessionDomain
import com.ait.tooling.server.core.support.CoreGroovySupport
import com.ait.tooling.server.mongodb.support.MongoDBTrait

class RPCServerSessionRepository extends CoreGroovySupport implements IServerSessionRepository, MongoDBTrait
{
    private String      m_domain

    private JSONObject  m_properties = json()

    public RPCServerSessionRepository(String domain)
    {
        m_domain = Objects.requireNonNull(domain)
    }

    @Override
    public IServerSession createSession()
    {
        null
    }

    @Override
    public void save(IServerSession session)
    {
    }

    @Override
    public IServerSession getSession(String id)
    {
        null
    }

    @Override
    public void delete(String id)
    {
        collection('sessions').deleteOne(EQ('id', id))
    }

    @Override
    public boolean isActive()
    {
        true
    }

    @Override
    public JSONObject getProperties()
    {
        m_properties
    }

    @Override
    public ISessionDomain getDomain()
    {
        null
    }

    @Override
    public void setRateLimit(double limit)
    {
    }

    @Override
    public void touch(IServerSession session)
    {
    }

    @Override
    public IServerSession createSession(JSONObject keys)
    {
        null
    }

    @Override
    public void cleanExpiredSessions()
    {
    }
}
