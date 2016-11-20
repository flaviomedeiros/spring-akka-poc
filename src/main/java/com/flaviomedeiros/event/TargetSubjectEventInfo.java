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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Utility object to represent a {@link TargetSubjectEvent} payload
 *
 * @author Flavio Medeiros
 * @since 0.0.1-SNAPSHOT
 */
public class TargetSubjectEventInfo {
    private Instant timestamp;
    private String target;
    private String targetId;
    private String subject;
    private String subjectId;

    @JsonCreator
    public TargetSubjectEventInfo(@JsonProperty("timestamp") Instant timestamp,
                                  @JsonProperty("target") String target,
                                  @JsonProperty("targetId") String targetId,
                                  @JsonProperty("subject") String subject,
                                  @JsonProperty("subjectId") String subjectId) {
        this.timestamp = timestamp;
        this.target = target;
        this.targetId = targetId;
        this.subject = subject;
        this.subjectId = subjectId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getTarget() {
        return target;
    }

    public String getTargetId() {
        return targetId;
    }

    public String getSubject() {
        return subject;
    }

    public String getSubjectId() {
        return subjectId;
    }

    @Override
    public String toString() {
        return "TargetSubjectEventInfo{" +
                "timestamp=" + timestamp +
                ", target='" + target + '\'' +
                ", targetId='" + targetId + '\'' +
                ", subject='" + subject + '\'' +
                ", subjectId='" + subjectId + '\'' +
                '}';
    }
}
