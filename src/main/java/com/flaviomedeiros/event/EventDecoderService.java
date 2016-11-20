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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * Utility service to decode event types
 *
 * @author Flavio
 * @since 0.0.1-SNAPSHOT
 */
@Service
public class EventDecoderService {

    //TODO Load event types with 'target+subject' event model from external configuration
    public static final String PRODUCT_ACCESSED_EVENT = "product.accessed";

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Decode a known event type
     *
     * @param eventReceived The event to decode
     * @return The decoded event or {@link  Optional#empty()  Optional.empty()} if the event type is unknown
     */
    public Optional<EventBase> decode(EventReceived eventReceived) {
        Optional<EventBase> result = Optional.empty();
        //TODO Turn the decoder service into an Actor or Stream step
        //TODO Move each event type decoding logic into their own service/actor/stream step
        switch (eventReceived.getEvent()) {
            case PRODUCT_ACCESSED_EVENT:
                result = processResourceAccessesEvent(eventReceived);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * Decodes a {@link #PRODUCT_ACCESSED_EVENT}
     *
     * @param eventReceived The event to decode
     * @return The decoded event or {@link  Optional#empty() Optional.empty()} if the event type is unknown
     * @see EventBase
     */
    private Optional<EventBase> processResourceAccessesEvent(EventReceived eventReceived) {
        Optional<EventBase> result;
        TargetSubjectEventInfo event = null;
        try {
            TargetSubjectEventInfo eventInfo = objectMapper.readValue(eventReceived.getPayload(), TargetSubjectEventInfo.class);
            TargetSubjectEvent targetSubjectEvent = new TargetSubjectEvent(eventReceived.getEvent(),
                    eventInfo.getTimestamp(), eventInfo.getTarget(), eventInfo.getTargetId(),
                    eventInfo.getSubject(), eventInfo.getSubjectId());
            result = Optional.of(targetSubjectEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
