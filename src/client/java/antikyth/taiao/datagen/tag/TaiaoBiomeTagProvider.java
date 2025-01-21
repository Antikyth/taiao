// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.tag;

import antikyth.taiao.world.gen.biome.TaiaoBiomes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class TaiaoBiomeTagProvider extends FabricTagProvider<Biome> {
    public TaiaoBiomeTagProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
    ) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        // Vanilla tags
        getOrCreateTagBuilder(BiomeTags.IS_FOREST).add(TaiaoBiomes.NATIVE_FOREST);
        getOrCreateTagBuilder(BiomeTags.STRONGHOLD_BIASED_TO).add(TaiaoBiomes.NATIVE_FOREST);

        // Conventional tags
        getOrCreateTagBuilder(ConventionalBiomeTags.TREE_DECIDUOUS).add(TaiaoBiomes.NATIVE_FOREST);
    }
}
