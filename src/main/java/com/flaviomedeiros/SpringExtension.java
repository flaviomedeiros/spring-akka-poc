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

import akka.actor.*;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Akka extension for Spring
 *
 * @author Flavio Medeiros
 * @see <a href="http://doc.akka.io/docs/akka/current/java/extending-akka.html">Akka Extensions</a>
 * @since 0.0.1-SNAPSHOT
 */
public class SpringExtension extends AbstractExtensionId<SpringExtension.SpringExtensionImpl> implements ExtensionIdProvider {

    public static final SpringExtension SpringExtensionProvider = new SpringExtension();

    private SpringExtension() {
    }

    @Override
    public SpringExtensionImpl createExtension(ExtendedActorSystem system) {
        return new SpringExtensionImpl();
    }

    @Override
    public ExtensionId<? extends Extension> lookup() {
        return SpringExtensionProvider;
    }

    public static class SpringExtensionImpl implements Extension {
        private volatile ApplicationContext applicationContext;

        public void initialize(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public Props props(String actorBeanName, Object... args) {
            List<Object> argsAsList = Arrays.asList(args);
            ArrayList<Object> params = new ArrayList<>(argsAsList);
            params.add(0, actorBeanName);
            params.add(0, applicationContext);
            return Props.create(SpringActorProducer.class, params.toArray());
        }
    }
}