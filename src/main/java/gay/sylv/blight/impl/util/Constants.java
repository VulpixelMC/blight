/*
 *     Blight
 *     Copyright (C) 2025  Vulpixel
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package gay.sylv.blight.impl.util;

import net.minecraft.resources.ResourceLocation;

public final class Constants {
	public static final String MOD_ID = "blight";
	public static final String MOD_NAME = "Blight";

	private Constants() {}

	public static ResourceLocation modId(String id) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
	}
}
