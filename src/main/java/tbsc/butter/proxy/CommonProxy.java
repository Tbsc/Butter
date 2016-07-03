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

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tbsc.butter.Butter;
import tbsc.butter.loader.ButterLoader;

/**
 * Implementation class for everything that needs to be done on both sides.
 * Is abstract for any proxy methods that do different stuff on each side,
 * and therefore the CommonProxy doesn't need to do anything.
 *
 * Created by tbsc on 03/07/2016.
 */
public abstract class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent preInit) {
        // Load all classes from ASM data and register them
        ButterLoader.scanForAnnotations(preInit.getAsmData(), FMLCommonHandler.instance().findContainerFor(Butter.instance));
    }

}
