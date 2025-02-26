// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.CustomPlacementBlock;
import antikyth.taiao.world.gen.feature.config.HiinakiFeatureConfig;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;

public class TaiaoFeatures {
	/**
	 * Like a {@link Feature#SIMPLE_BLOCK} but supporting {@link CustomPlacementBlock}s.
	 */
	public static final Feature<SimpleBlockFeatureConfig> CUSTOM_PLACEMENT_BLOCK = register(
		Taiao.id("custom_placement_block"),
		new CustomPlacementBlockFeature()
	);

	public static final Feature<HiinakiFeatureConfig> HIINAKI = register(Taiao.id("hiinaki"), new HiinakiFeature());

	public static void initialize() {
		Taiao.LOGGER.debug("Registered features");
	}

	public static <FC extends FeatureConfig, F extends Feature<FC>> F register(Identifier id, F feature) {
		return Registry.register(Registries.FEATURE, id, feature);
	}
}
