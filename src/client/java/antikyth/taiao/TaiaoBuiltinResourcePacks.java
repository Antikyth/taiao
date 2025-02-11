// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TaiaoBuiltinResourcePacks {
	public static final Identifier ENGLISH_NAMES = register(
		Taiao.id("english_names"),
		ResourcePackActivationType.NORMAL
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registering built-in resource packs");
	}

	/**
	 * Registers a built-in resource pack with a translatable name.
	 * <p>
	 * The translation key will be {@code pack.namespace.path}.
	 */
	public static Identifier register(Identifier id, ResourcePackActivationType activationType) {
		return register(id, Text.translatable(id.toTranslationKey("pack")), activationType);
	}

	public static Identifier register(Identifier id, Text name, ResourcePackActivationType activationType) {
		FabricLoader.getInstance().getModContainer(Taiao.MOD_ID).ifPresentOrElse(
			container -> ResourceManagerHelper.registerBuiltinResourcePack(id, container, name, activationType),
			() -> Taiao.LOGGER.warn(
				"Unable to register built-in resource pack '{}' because mod container '{}' was not present",
				id,
				Taiao.MOD_ID
			)
		);

		return id;
	}
}
