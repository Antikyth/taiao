// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.world.gen.feature.foliage.SingleFoliagePlacer;
import antikyth.taiao.world.gen.feature.trunk.BranchingTrunkPlacer;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.jetbrains.annotations.NotNull;

public class TaiaoConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> CABBAGE_TREE = Taiao.createRegistryKey(
            Taiao.id("cabbage_tree"),
            RegistryKeys.CONFIGURED_FEATURE
    );

    public static void bootstrapConfiguredFeatures(@NotNull Registerable<ConfiguredFeature<?, ?>> registerable) {
        registerable.register(
                TaiaoConfiguredFeatures.CABBAGE_TREE,
                new ConfiguredFeature<>(
                        Feature.TREE,
                        new TreeFeatureConfig.Builder(
                                BlockStateProvider.of(TaiaoBlocks.CABBAGE_TREE_LOG),
                                new BranchingTrunkPlacer(4, 2, 0),
                                BlockStateProvider.of(TaiaoBlocks.CABBAGE_TREE_LEAVES),
                                new SingleFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
                                new TwoLayersFeatureSize(1, 0, 1)
                        ).ignoreVines().build()
                )
        );
    }
}
