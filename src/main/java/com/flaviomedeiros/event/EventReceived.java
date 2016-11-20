/*
 Copyright 2016 Flavio Medeiros

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
package com.flaviomedeiros.event;

/**
 * A received event
 *
 * @author Flavio Medeiros
 * @since 0.0.1-SNAPSHOT
 */
public class EventReceived extends EventBase {
    private String event;
    private String payload;

    public EventReceived(String event, String payload) {
        this.event = event;
        this.payload = payload;
    }

    public String getEvent() {
        return event;
    }

    public String getPayload() {
        return payload;
    }
}
