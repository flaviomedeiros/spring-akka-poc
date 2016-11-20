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
package com.flaviomedeiros.web;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.flaviomedeiros.event.EventReceived;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;


/**
 * Events REST controller
 *
 * @author Flavio Medeiros
 * @since 0.0.1-SNAPSHOT
 */
@RestController
@RequestMapping(value = "events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

    @Autowired
    private ActorSystem system;

    private ActorSelection eventSupervisor;

    @PostConstruct
    public void initialize() {
        String eventSupervisorName = "user/EventSupervisor";
        this.eventSupervisor = system.actorSelection(eventSupervisorName);
    }

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity> postEvent() {
        DeferredResult<ResponseEntity> result = new DeferredResult<ResponseEntity>();

        result.setResult(new ResponseEntity(HttpStatus.NOT_IMPLEMENTED));

        return result;
    }

    /**
     * Receives an event
     * <p>
     * Sample event payload:
     * <pre>
     *  {
     *      "timestamp": "2016-11-19T20:55:32Z",
     *      "target": "product",
     *      "targetId": "8976",
     *      "subject": "customer",
     *      "subjectId": "2134"
     *  }
     * </pre>
     * <p>
     * The client can call
     * <a href="http://localhost:8080/events/resource.accessed/">http://localhost:8080/events/resource.accessed/</a>
     * with the sample payload as the body.
     *
     * @param event   Event name
     * @param payload Event payload
     * @return Http status 202 (Accepted) if the call succeeds. The corresponding 4XX or 5XX codes otherwise.
     * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">Status Code Definitions</a>
     */
    @RequestMapping(value = "{event}", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity> postEvent(@PathVariable("event") String event, @RequestBody String payload) {

        //The DeferredResult return type makes this an asynchronous call
        DeferredResult<ResponseEntity> result = new DeferredResult<ResponseEntity>();

        /*
            Just delegate the processing to Akka.
            Because this is a non blocking call, there's no wait for execution.
         */
        eventSupervisor.tell(new EventReceived(event, payload), ActorRef.noSender());

        /*
            As ActorSelection.tell is a non blocking call,
            just return HTTP status code 202 (Accepted) to client
            and the processing continue into Akka actors.

            In this case, exceptions must be logged by actors processing logic.
         */
        result.setResult(new ResponseEntity(HttpStatus.ACCEPTED));

        return result;
    }
}