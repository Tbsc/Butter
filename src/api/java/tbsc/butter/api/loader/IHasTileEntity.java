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

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Used to mark blocks with a tile entity, for the butter loader to register it.
 * Extends ITileEntityProvider to automatically let Minecraft know block that
 * implement this interface are tile entities.
 * Uses type parameter {@link T} for the tile entity.
 *
 * Created by tbsc on 04/07/2016.
 */
public interface IHasTileEntity<T extends TileEntity> extends ITileEntityProvider {

    @Override
    default TileEntity createNewTileEntity(World worldIn, int meta) {
        return getTileEntity(worldIn, meta);
    }

    /**
     * Return an instance of the tile entity class. Can use world object and metadata
     * to change the returned tile entity.
     * @param world The world object
     * @param metadata Metadata of the tile entity
     * @return A tile entity instance for the metadata given
     */
    T getTileEntity(World world, int metadata);

    /**
     * Used to register the tile entity to the game.
     * @return The class of the tile entity
     */
    Class<T> getTileClass();

}
