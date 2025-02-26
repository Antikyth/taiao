// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class TaiaoBuiltinResourcePacks {
	/**
	 * Replaces Māori names with English ones, where the English names are the more common usage.
	 */
	public static final Identifier ENGLISH_NAMES = register(
		Taiao.id("english_names"),
		ResourcePackActivationType.NORMAL
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registered built-in resource packs");
	}

	/**
	 * Registers a built-in resource pack with a translatable name.
	 * <p>
	 * The translation key is created by {@link TaiaoBuiltinResourcePacks#translationKey(Identifier)}.
	 */
	public static Identifier register(Identifier id, ResourcePackActivationType activationType) {
		return register(id, Text.translatable(translationKey(id)), activationType);
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

	/**
	 * Creates the translation key for a resource pack.
	 * <p>
	 * The key will be {@code pack.namespace.path}.
	 */
	public static String translationKey(@NotNull Identifier id) {
		return id.toTranslationKey("pack");
	}
}
