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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * API annotation used to mark anything as unfinished, just to let other people know
 * that anything annotated with this annotation is WIP.
 *
 * Created by tbsc on 04/07/2016.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Unfinished {

}
