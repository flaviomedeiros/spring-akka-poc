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

import java.time.Instant;

/**
 * A basic event
 * Created by Flavio on 15/11/2016.
 */
public class EventBase {

    private Instant generatedAt;

    public EventBase() {
        this.generatedAt = Instant.now();
    }

    /**
     * Instant of event instantiation
     */
    public Instant getGeneratedAt() {
        return generatedAt;
    }
}
