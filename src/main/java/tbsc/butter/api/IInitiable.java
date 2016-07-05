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

/**
 * To be implemented on classes that have load cycle.
 * Uses generics to define a parameter for the load cycles.
 *
 * Load cycle:
 *
 * Pre Init (Type parameter {@link P})
 *    |
 *   \/
 *   Init (Type parameter {@link I})
 *    |
 *   \/
 * Post Init (Type parameter {@link A})
 *    |
 *   \/
 * Load Complete (Type parameter {@link L})
 *
 * Created by tbsc on 03/07/2016.
 */
public interface IInitiable<P, I, A, L> {

    /**
     * Pre init stage of load cycle.
     * In terms of Minecraft load cycle, registration of configuration, items, blocks, models,
     * ore dictionary, and any other stuff that's **part** of the game.
     * Uses type parameter {@link P} for parameter (P meaning "pre-init").
     * @param preInit Can be used when wanting to load stuff to specific instances on stage
     */
    void preInit(P preInit);

    /**
     * Init stage of load cycle.
     * In terms of Minecraft load cycle, here recipes and other stuff that needs other mods should
     * be done.
     * Uses type parameter {@link I} for parameter (I meaning "init").
     * @param init Can be used when wanting to load stuff to specific instances on stage
     */
    void init(I init);

    /**
     * Post init stage of load cycle.
     * In terms of Minecraft load cycle, everything that needs to be done **after** everything
     * is initiated needs to be done here.
     * Uses type parameter {@link A} for parameter (A meaning "after").
     * @param postInit Can be used when wanting to load stuff to specific instances on stage
     */
    void postInit(A postInit);

    /**
     * Load complete stage of load cycle.
     * In terms of Minecraft load cycle, if you need to do stuff on the last load cycle, and that
     * depends on final stuff that happens in {@link #postInit(Object)}
     * Uses type parameter {@link L} for parameter (L meaning "load").
     * @param loadComplete Can be used when wanting to load stuff to specific instances on stage
     */
    void loadComplete(L loadComplete);

}
