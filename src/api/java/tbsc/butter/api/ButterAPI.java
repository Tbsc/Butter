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

package tbsc.butter.api;

import net.minecraftforge.fml.common.FMLLog;
import org.apache.commons.lang3.ArrayUtils;
import tbsc.butter.api.loader.InstanceLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class for the ButterAPI. Has methods for registration and other stuff.
 *
 * Created by tbsc on 04/07/2016.
 */
public class ButterAPI {

    /* MODS TO BE LOADED */

    /**
     * List of mods to be loaded by the butter loader
     */
    private static List<String> butterLoaders = new ArrayList<>();

    /**
     * Adds the specified MODID to the list of mods that'll be loaded by the butter loader.
     * This HAS be called on mod **CONSTRUCTION** stage (so the blocks and items can be loaded
     * to the game on pre init stage).
     * @param modid The ID of the mod to register
     */
    public static void registerModToButterLoader(String modid) {
        butterLoaders.add(modid);
        FMLLog.info("[Butter] Received loader registration request for mod %s", modid);
    }

    /**
     * @return List of registered MODIDs to be loaded by the butter loader.
     */
    public static List<String> getRegisteredLoaderModIDs() {
        return butterLoaders;
    }

    /* LOADER INTERFACES */

    /**
     * This map matches between an interface's class to an array of instance loaders.
     * When an instance implements an interface, it checks in this map for the specified
     * {@link InstanceLoader}s to run for that interface.
     */
    private static Map<Class, InstanceLoader[]> instanceLoaders = new HashMap<>();

    /**
     * Register the specified instance loader the butter loader.
     * @param interfaceClass When an instance implements interfaces, what interface does the instance need
     *                       to implement for this instance loader to run.
     * @param loader The instance loader to run for the interface specified
     */
    public static void registerInstanceLoader(Class interfaceClass, InstanceLoader loader) {
        if (instanceLoaders.containsKey(interfaceClass)) {
            instanceLoaders.put(interfaceClass, ArrayUtils.add(instanceLoaders.get(interfaceClass), loader));
        } else {
            instanceLoaders.put(interfaceClass, new InstanceLoader[] {
                    loader
            });
        }
        FMLLog.info("[Butter] Received instance loader %s registration request for interface class %s", interfaceClass.getSimpleName(), loader.getClass().getSimpleName());
    }

    public static Map<Class, InstanceLoader[]> getInstanceLoadersMap() {
        return instanceLoaders;
    }

    /**
     * Get the instance loaders for the interface given.
     * @param iface The interface to check for
     * @return Instance loaders for the interface given.
     */
    public static InstanceLoader[] getInstanceLoadersForInterface(Class iface) {
        return instanceLoaders.get(iface);
    }

}
