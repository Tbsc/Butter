/*
 * Copyright © 2016 Tbsc
 *
 * Butter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Butter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Butter.  If not, see <http://www.gnu.org/licenses/>.
 */

package tbsc.butter.api.loader;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

/**
 * Used by the ButterLoader to create instances of this for the mods to run.
 * Since I want the mods that own the blocks to also register the mods as their
 * MODID, I return a runnable that lets them run it themselves.
 *
 * Created by tbsc on 07/07/2016.
 */
public class InstanceRegister {

    private Set<Instance> instances = new HashSet<>();

    public InstanceRegister(Instance... instances) {
        this.instances = Sets.newHashSet(instances);
    }

    /**
     * Calling this method will register everything.
     */
    public void register() {
        for (Instance instance : instances) {
            instance.run();
        }
    }

    public interface Instance {

        /**
         * To be able to register more than 1 thing, I need to store all of the instances
         * to register individually, and register each using a wrapper.
         * This interface is the interface wrapped using {@link InstanceRegister}, and
         * is to be stored in a set of instance registers to run.
         */
        void run();

    }

}
