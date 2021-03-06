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

package tbsc.butter.loader;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderException;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import org.apache.logging.log4j.Level;
import tbsc.butter.api.ButterAPI;
import tbsc.butter.api.loader.InstanceLoader;
import tbsc.butter.api.loader.Register;
import tbsc.butter.util.Debug;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * This class scans for annotated classes or fields, in order to load them to the game.
 * The annotations this loader checks for are {@link Register.Instance} and
 * {@link Register.Class}.
 * <p>
 * Created by tbsc on 03/07/2016.
 */
public class ButterLoader {

    /**
     * Scans for any fields annotated with {@link Register.Instance} or classes annotated
     * with {@link Register.Class}, and if found attempt to register them to the game.
     *
     * @param data ASM data table, contains ASM information for annotations and other stuff
     * @param mod  The mod to check for
     */
    public static void scanForAnnotations(ASMDataTable data, ModContainer mod) {
        // Log
        FMLLog.fine("[Butter] Attempting to register objects for mod %s", mod.getModId());
        // Get all targets (fields) annotated with @Register.Instance
        Set<ASMData> instanceTargets = data.getAnnotationsFor(mod).get(Register.Instance.class.getName());
        // Get all targets (types) annotated with @Register.Class
        Set<ASMData> classTargets = data.getAnnotationsFor(mod).get(Register.Class.class.getName());
        // Get the mod class loader, to include @SideOnly class loader removals
        ClassLoader mcl = Loader.instance().getModClassLoader();

        // Loop through found instance targets
        for (ASMData target : instanceTargets) {
            try {
                // Get class of the field found
                Class<?> fieldClass = Class.forName(target.getClassName(), false, mcl);
                // Get the field
                Field targetField = fieldClass.getDeclaredField(target.getObjectName());
                // If field doesn't exist (can't really happen)
                if (targetField == null) { // Can't happen
                    FMLLog.severe("[Butter] Attempted to register non-existent field? Something is seriously messing with the game! (Class: %s, Field: %s)", target.getClassName(), target.getObjectName());
                    // Throw error
                    throw new LoaderException(String.format("[Butter] Attempted to register non-existent field? Something is seriously messing with the game! (Class: %s, Field: %s)", target.getClassName(), target.getObjectName()));
                }
                // Allow access to the field
                targetField.setAccessible(true);
                // Get the instance the field has
                Object instance = targetField.get(null);
                // Null check
                if (instance == null) {
                    throw new NullPointerException("[Techy] Cannot register null object to the game");
                }
                // Check if can be registered normally
                if (instance instanceof IForgeRegistryEntry) {
                    // Set registry name to be of the mod that own the class USING REFLECTION.
                    // Forge doesn't allow me to change the registry name after it has been set.
                    // The problem is that otherwise the mod that owns the block is butter, and
                    // I don't want that. To go around that limitation, I'll use reflection to
                    // change the resource domain.
                    Field resourceDomain = ((IForgeRegistryEntry) instance).getRegistryName().getClass().getDeclaredField("resourceDomain");
                    // Allow access
                    resourceDomain.setAccessible(true);
                    // Change the value of the resource domain to the mod owning the object
                    resourceDomain.set(((IForgeRegistryEntry) instance).getRegistryName(), mod.getModId());
                    // Deny access
                    resourceDomain.setAccessible(false);
                    // Register to the game
                    GameRegistry.register((IForgeRegistryEntry) instance);
                }
                // Register everything
                registerInstance(instance);
                // Deny access to the field
                targetField.setAccessible(false);
            } catch (Exception e) { // Caught exception
                // Log information about error
                FMLLog.log(Level.ERROR, e, "[Butter] An error occurred trying to register %s.%s into the game", target.getClassName(), target.getObjectName());
                // Throw LoaderException with the exception caught
                throw new LoaderException(e);
            }
        }

        // Loop through all type targets
        for (ASMData target : classTargets) {
            try {
                // Get class of the target found
                Class<?> targetClass = Class.forName(target.getClassName(), false, mcl);
                // If class doesn't exist (can't really happen)
                if (targetClass == null) { // Can't happen
                    FMLLog.severe("[Butter] Attempted to register non-existent class %s?", target.getClassName());
                    // Throw error
                    throw new LoaderException(String.format("[Butter] Attempted to register non-existent class %s?", target.getClassName()));
                }
                // Create instance for the class
                Object instance = targetClass.newInstance();
                // Null check
                if (instance == null) {
                    throw new NullPointerException("[Techy] Cannot register null object to the game");
                }
                // Check if can be registered normaly
                if (instance instanceof IForgeRegistryEntry) {
                    // Set registry name to be of the mod that own the class USING REFLECTION.
                    // Forge doesn't allow me to change the registry name after it has been set.
                    // The problem is that otherwise the mod that owns the block is butter, and
                    // I don't want that. To go around that limitation, I'll use reflection to
                    // change the resource domain.
                    Field resourceDomain = ((IForgeRegistryEntry) instance).getRegistryName().getClass().getDeclaredField("resourceDomain");
                    // Allow access
                    resourceDomain.setAccessible(true);
                    // Change the value of the resource domain to the mod owning the object
                    resourceDomain.set(((IForgeRegistryEntry) instance).getRegistryName(), mod.getModId());
                    // Deny access
                    resourceDomain.setAccessible(false);
                    // Register to the game
                    GameRegistry.register((IForgeRegistryEntry) instance);
                }
                // Register everything
                registerInstance(instance);
                // Loop through the class' fields
                for (Field field : targetClass.getFields()) {
                    // Allow access to field
                    field.setAccessible(true);
                    // If the field's class is the same as the class that holds it and is null
                    if (field.getDeclaringClass() == targetClass && field.get(instance) == null) {
                        // Assign value of field to the instance of the class
                        field.set(instance, instance);
                    }
                    // Deny access to field
                    field.setAccessible(false);
                }
            } catch (Exception e) { // Caught exception
                // Log information about error
                FMLLog.log(Level.ERROR, e, "[Butter] An error occurred trying to register %s into the game", target.getClassName());
                // Throw LoaderException with the exception caught
                throw new LoaderException(e);
            }
        }
    }

    /**
     * After finishing getting the data and creating instances, we can start actually registering
     * the instance to the game.
     * In order to allow me to have this separated from the big chunk of code above this method, the registration
     * part is in this method to also prevent from rewriting it multiple times in
     * {@link #scanForAnnotations(ASMDataTable, ModContainer)}.
     * @param instance The instance to register
     */
    private static void registerInstance(Object instance) {
        // Loop through instance loaders
        for (Map.Entry<Class<?>, InstanceLoader[]> entry : ButterAPI.getInstanceLoadersMap().entrySet()) {
            Debug.log("Looping, checking interface %s for instance %s", entry.getKey(), instance);
            // Make sure that the array of interfaces the instance has contains the interface
            if (entry.getKey().isAssignableFrom(instance.getClass())) {
                Debug.log("Instance %s implements interface %s", entry.getKey().getName(), instance.getClass().getName());
                // Loop through the array of loaders
                for (InstanceLoader loader : entry.getValue()) {
                    // Run the instance loader
                    loader.load(instance);
                }
            }
        }
    }

}
