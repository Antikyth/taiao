// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.biome;

import antikyth.taiao.Taiao;
import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;

import java.util.function.Consumer;

/**
 * TerraBlender {@link Region}s for adding our modded biomes in a compatible way.
 */
public class TaiaoRegions {
    public static void initialize() {
        Taiao.LOGGER.debug("Registering TerraBlender regions");

        register(new Region(Taiao.id("overworld_region"), RegionType.OVERWORLD, 1) {
            @Override
            public void addBiomes(
                    Registry<Biome> registry,
                    Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper
            ) {
                addBiomeSimilar(mapper, BiomeKeys.FOREST, TaiaoBiomes.NATIVE_FOREST);
            }
        });
    }

    public static Region register(Region region) {
        Regions.register(region);

        return region;
    }
}
