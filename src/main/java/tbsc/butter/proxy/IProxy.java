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

package tbsc.butter.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tbsc.butter.api.IInitiable;

/**
 * Proxy interface. Uses the {@link IInitiable} interface to create a predictable load cycle using API methods.
 * PreInit type parameter is {@link FMLPreInitializationEvent}, init is {@link FMLInitializationEvent}, post init
 * is {@link FMLPostInitializationEvent} and load complete is {@link FMLLoadCompleteEvent}.
 *
 * Any additional proxy calls need to be added separately from {@link IInitiable}, as {@link IInitiable} only
 * takes care of load cycle calls.
 *
 * Created by tbsc on 03/07/2016.
 */
public interface IProxy extends IInitiable<FMLPreInitializationEvent, FMLInitializationEvent, FMLPostInitializationEvent, FMLLoadCompleteEvent> {

}
