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

package gay.sylv.blight.impl;

import gay.sylv.blight.impl.util.Constants;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlightMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(Constants.MOD_NAME);

	@Override
	public void onInitialize() {
		LOGGER.info("Blight started");
	}

	public static Logger createLogger(String... paths) {
		return LoggerFactory.getLogger(Constants.MOD_NAME + "/" + String.join("/", paths));
	}
}
