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
 * An event type based on Target and Subject abstract model
 *
 * @author Flavio Medeiros
 * @since 0.0.1-SNAPSHOT
 */
public class TargetSubjectEvent extends EventBase {

    private String event;
    private Instant timestamp;
    private String target;
    private String targetId;
    private String subject;
    private String subjectId;

    public TargetSubjectEvent(String event,
                              Instant timestamp,
                              String target,
                              String targetId,
                              String subject,
                              String subjectId) {
        super();
        this.event = event;
        this.timestamp = timestamp;
        this.target = target;
        this.targetId = targetId;
        this.subject = subject;
        this.subjectId = subjectId;
    }

    /**
     * The event name
     *
     * @return Event name
     */
    public String getEvent() {
        return event;
    }

    /**
     * The moment the event happened
     *
     * @return Event instant
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * The target of the event. It's the thing the subject is acting upon.
     *
     * @return Event target
     */
    public String getTarget() {
        return target;
    }

    /**
     * The event target ID. This is anything that identifies the target.
     *
     * @return Event target ID
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * The subject of the event. This is 'who' is acting against the target.
     *
     * @return Event subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * The event subject ID. This is anything that identifies the subject.
     *
     * @return Event subject ID
     */
    public String getSubjectId() {
        return subjectId;
    }

    @Override
    public String toString() {
        return "TargetSubjectEvent{" +
                "event='" + event + '\'' +
                ", generatedAt=" + getGeneratedAt() +
                ", timestamp=" + timestamp +
                ", target='" + target + '\'' +
                ", targetId='" + targetId + '\'' +
                ", subject='" + subject + '\'' +
                ", subjectId='" + subjectId + '\'' +
                '}';
    }
}
