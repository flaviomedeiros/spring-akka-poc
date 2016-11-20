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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.flaviomedeiros.SpringExtension.SpringExtensionProvider;

/**
 * The events processing supervisor actor
 *
 * @author Flavio Medeiros
 * @see <a href="http://doc.akka.io/docs/akka/snapshot/general/supervision.html">Supervision and Monitoring</a>
 * @since 0.0.1-SNAPSHOT
 */
@Component("EventSupervisor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EventSupervisor extends UntypedActor {

    //TODO Move actor names no a constants file (check if still needed when using streams)
    public static final String EVENT_HISTORY_ACTOR_NAME = "EventHistoryActor";
    public static final String TARGET_SUBJECT_EVENT_COUNTER_ACTOR_NAME = "TargetSubjectEventCounterActor";
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    @Autowired
    private EventDecoderService eventDecoderService;
    private ActorRef eventHistoyActor;
    private ActorRef targetSubjectEventCounterActor;

    @Override
    public void preStart() throws Exception {
        super.preStart();

        //TODO Change child actor instantiation logic to use ActorSelection to prevent duplicated instantiation
        this.eventHistoyActor = context().actorOf(SpringExtensionProvider.get(context().system())
                .props(EVENT_HISTORY_ACTOR_NAME), EVENT_HISTORY_ACTOR_NAME);
        this.targetSubjectEventCounterActor = context().actorOf(SpringExtensionProvider.get(context().system())
                .props(TARGET_SUBJECT_EVENT_COUNTER_ACTOR_NAME), TARGET_SUBJECT_EVENT_COUNTER_ACTOR_NAME);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof EventReceived) {
            EventReceived eventReceived = ((EventReceived) message);
            processEventReceived(eventReceived);
        } else if (message instanceof TargetSubjectEvent) {
            processTargetSubjectEvent((TargetSubjectEvent) message);
        } else {
            unhandled(message);
        }
    }

    private void processTargetSubjectEvent(TargetSubjectEvent message) {
        targetSubjectEventCounterActor.tell(message, getSelf());
    }

    private void processEventReceived(EventReceived eventReceived) {
        //TODO Move decoding logic to an actor or stream step
        Optional<EventBase> decodedEvent = eventDecoderService.decode(eventReceived);
        decodedEvent.ifPresent(eventBase -> eventHistoyActor.tell(eventBase, getSelf()));
    }
}
