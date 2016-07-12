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

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tbsc.butter.api.ButterAPI;
import tbsc.butter.api.loader.IHasCustomModel;
import tbsc.butter.api.loader.IHasItemBlock;
import tbsc.butter.api.loader.IHasTileEntity;
import tbsc.butter.util.Debug;

import static tbsc.butter.Butter.MODID;

/**
 * Implementation class for everything that needs to be done on both sides.
 * Is abstract for any proxy methods that do different stuff on each side,
 * and therefore the CommonProxy doesn't need to do anything.
 *
 * Created by tbsc on 03/07/2016.
 */
public abstract class CommonProxy implements IProxy {

    public static ASMDataTable asmData;

    /**
     * Pre init stage of Minecraft lifecycle events.
     * @param preInit Can be used when wanting to load stuff to specific instances on stage
     */
    @Override
    public void preInit(FMLPreInitializationEvent preInit) {
        asmData = preInit.getAsmData();
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
        ButterAPI.registerModToButterLoader(MODID);
    }

}
