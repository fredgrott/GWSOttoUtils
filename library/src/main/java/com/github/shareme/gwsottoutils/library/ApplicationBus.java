/*
 * Copyright (C) 2012 Square, Inc.
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.shareme.gwsottoutils.library;

import com.squareup.otto.Bus;

import java.util.HashSet;
import java.util.Set;

/**
 * ApplicationBus class and ScopedBus class are for different purposes although
 * the basic behavior is the same. When you need an application bus that does not
 * unregister until app dies choose the ApplicationBus class.
 *
 * When you made need a Scoped Bus for one activity  than choose the scoped bus which
 * when used in this fashion will register and unregister with the lifecycle of
 * the activity or fragment.
 * Created by fgrott on 9/29/2015.
 */
@SuppressWarnings("unused")
public class ApplicationBus {

    private final Bus bus = BusProvider.getInstance();
    private final Set<Object> objects = new HashSet<>();
    private boolean active;

    public void register(Object obj) {
        objects.add(obj);
        if (active) {
            bus.register(obj);
        }
    }


    public void unregister(Object obj) {
        objects.remove(obj);
        if (active) {
            bus.unregister(obj);
        }
    }


    public void post(Object event) {
        bus.post(event);
    }


    public void paused() {
        active = false;
        for (Object obj : objects) {
            bus.unregister(obj);
        }
    }


    public void resumed() {
        active = true;
        for (Object obj : objects) {
            bus.register(obj);
        }
    }

    public void start() {
        active = true;
        for (Object obj: objects){
            bus.register(obj);
        }
    }

    public void stop(){
        active = false;
        for (Object obj: objects){
            bus.unregister(obj);
        }
    }
}
