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
package com.flaviomedeiros;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.flaviomedeiros.SpringExtension.SpringExtensionProvider;

/**
 * Spring based configuration
 *
 * @author Flavio Medeiros
 * @since 0.0.1.SNAPSHOT
 */
@Configuration
@ComponentScan
public class AkkaSpringPocConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public ActorSystem actorSystem() {

        Config config = ConfigFactory.parseString("akka.actor.deployment {\n" +
                "\t/EventSupervisor {\n" +
                "\t\trouter = round-robin-pool\n" +
                "\t\tnr-of-instances = 1\n" +
                "\t}\n" +
                "}");
        ActorSystem system = ActorSystem.create("AkkaSpringPoc", config);
        SpringExtensionProvider.get(system).initialize(applicationContext);
        //Criar o supervisor de eventos
        String eventSupervisorName = "EventSupervisor";
        system.actorOf(SpringExtensionProvider.get(system)
                .props(eventSupervisorName), eventSupervisorName);
        return system;
    }
}
