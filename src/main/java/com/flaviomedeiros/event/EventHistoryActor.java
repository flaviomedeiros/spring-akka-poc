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

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Actor responsible to register the event cronological history
 *
 * @author Flavio Medeiros
 * @since 0.0.1-SNAPSHOT
 */
//TODO Implement as event sourcing using Akka Persistence
@Component("EventHistoryActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EventHistoryActor extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof TargetSubjectEvent) {
            processTargetSubjectEvent((TargetSubjectEvent) message);
            getSender().tell(message, ActorRef.noSender());
        }
    }

    private void processTargetSubjectEvent(TargetSubjectEvent targetSubjectEvent) {
        //FIXME Register the event
        log.info("Event history registered: " + targetSubjectEvent);
    }
}
