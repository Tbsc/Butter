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

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tbsc.butter.api.ButterAPI;
import tbsc.butter.api.loader.IHasCustomModel;
import tbsc.butter.api.loader.IHasItemBlock;
import tbsc.butter.api.loader.IHasTileEntity;
import tbsc.butter.proxy.IProxy;
import tbsc.butter.util.Debug;

@Mod(modid = Butter.MODID, version = Butter.VERSION)
public class Butter {

    public static final String MODID = "butter";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(clientSide = "tbsc.butter.proxy.ClientProxy", serverSide = "tbsc.butter.proxy.ServerProxy")
    public static IProxy proxy;

    @Mod.Instance
    public static Butter instance;

    @EventHandler
    public void onConstruction(FMLConstructionEvent event) {
        ButterAPI.registerModToButterLoader(MODID);
        ButterAPI.registerInstanceLoader(IHasItemBlock.class, instance -> {
            // Make sure it is a block
            if (instance instanceof Block) {
                Debug.log("[Butter] Registering %s item block", ((Block) instance).getRegistryName());
                // Register the ItemBlock
                GameRegistry.register(((IHasItemBlock) instance).getItemBlock(), ((Block) instance).getRegistryName());
            }
        });
        ButterAPI.registerInstanceLoader(IHasCustomModel.class, instance -> {
            // Run only if this is called on the client
            if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
                Debug.log("[Butter] Running on client side, able to load custom model for instance %s", instance);
                // Load custom model
                ((IHasCustomModel) instance).loadCustomModel();
            }
        });
        ButterAPI.registerInstanceLoader(IHasTileEntity.class, instance -> {
            Debug.log("[Butter] Registering tile entity for instance %s", instance);
            // Get IHasTileEntity
            IHasTileEntity instanceTile = (IHasTileEntity) instance;
            // Register tile
            GameRegistry.registerTileEntity(instanceTile.getTileClass(), instanceTile.getTileIdentifier());
        });
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

}
