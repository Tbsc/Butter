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

package tbsc.butter.util;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.FMLLog;

/**
 * Development debug class. Has util methods for debugging stuff in code, that'll
 * only run in a development environment to prevent from accidentally releasing the
 * mod with test log or other stuff that shouldn't be released.
 *
 * Created by tbsc on 03/07/2016.
 */
public class Debug {

    public static final boolean isDevEnv = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    public static void log(String text, Object... format) {
        if (isDevEnv) FMLLog.info(text, format);
    }

}
