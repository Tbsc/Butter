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

package tbsc.butter;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import tbsc.butter.api.ButterAPI;
import tbsc.butter.proxy.IProxy;

@Mod(modid = Butter.MODID, version = Butter.VERSION)
public class Butter {

    public static final String MODID = "butter";
    public static final String VERSION = "0.0.1.0";

    @SidedProxy(clientSide = "tbsc.butter.proxy.ClientProxy", serverSide = "tbsc.butter.proxy.ServerProxy")
    public static IProxy proxy;

    @Mod.Instance
    public static Butter instance;

    @EventHandler
    public void onConstruction(FMLConstructionEvent event) {
        ButterAPI.registerModToButterLoader(MODID);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete(event);
    }

}
