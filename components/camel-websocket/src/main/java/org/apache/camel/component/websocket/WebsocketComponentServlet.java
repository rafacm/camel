/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.websocket;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class WebsocketComponentServlet extends WebSocketServlet {

    private static final long serialVersionUID = 207837507742337364L;

    private WebsocketConsumer consumer;
    private NodeSynchronization sync;

    public WebsocketComponentServlet(NodeSynchronization sync) {
        this.sync = sync;
    }

    public WebsocketConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(WebsocketConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
        return new DefaultWebsocket(sync, consumer);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: why do we grab the default servlet?
        getServletContext().getNamedDispatcher("default").forward(request, response);
    }

}