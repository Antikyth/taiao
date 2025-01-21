// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.world.gen.feature.tree.placer.foliage.PalmFoliagePlacer;
import antikyth.taiao.world.gen.feature.tree.placer.foliage.SphericalFoliagePlacer;
import antikyth.taiao.world.gen.feature.tree.placer.trunk.ThinSplittingTrunkPlacer;
import antikyth.taiao.world.gen.feature.tree.placer.trunk.ThinStraightTrunkPlacer;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;

public class TaiaoConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> KAURI_TREE = createRegistryKey(Taiao.id("kauri_tree"));
    /**
     * A tī kōuka tree.
     */
    public static final RegistryKey<ConfiguredFeature<?, ?>> CABBAGE_TREE = createRegistryKey(Taiao.id("cabbage_tree"));
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAMAKU_TREE = createRegistryKey(Taiao.id("mamaku_tree"));

    public static void bootstrapConfiguredFeatures(@NotNull Registerable<ConfiguredFeature<?, ?>> registerable) {
        registerable.register(
                KAURI_TREE,
                new ConfiguredFeature<>(
                        Feature.TREE,
                        new TreeFeatureConfig.Builder(
                                BlockStateProvider.of(TaiaoBlocks.KAURI_LOG),
                                new DarkOakTrunkPlacer(9, 3, 2),
                                BlockStateProvider.of(TaiaoBlocks.KAURI_LEAVES),
                                new DarkOakFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
                                new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
                        ).ignoreVines().build()
                )
        );
        registerable.register(
                CABBAGE_TREE,
                new ConfiguredFeature<>(
                        Feature.TREE,
                        new TreeFeatureConfig.Builder(
                                BlockStateProvider.of(TaiaoBlocks.CABBAGE_TREE_LOG),
                                new ThinSplittingTrunkPlacer(
                                        3,
                                        3,
                                        2,
                                        ConstantIntProvider.create(1),
                                        0.4f,
                                        new ThinSplittingTrunkPlacer.SplitTypeWeights(9, 5, 1)
                                ),
                                BlockStateProvider.of(TaiaoBlocks.CABBAGE_TREE_LEAVES),
                                new SphericalFoliagePlacer(
                                        ConstantIntProvider.create(0),
                                        ConstantIntProvider.create(0)
                                ),
                                new TwoLayersFeatureSize(1, 0, 1)
                        ).ignoreVines().build()
                )
        );
        registerable.register(
                MAMAKU_TREE,
                new ConfiguredFeature<>(
                        Feature.TREE,
                        new TreeFeatureConfig.Builder(
                                BlockStateProvider.of(TaiaoBlocks.MAMAKU_LOG),
                                new ThinStraightTrunkPlacer(5, 6, 6),
                                BlockStateProvider.of(TaiaoBlocks.MAMAKU_LEAVES),
                                new PalmFoliagePlacer(
                                        UniformIntProvider.create(3, 4),
                                        ConstantIntProvider.create(0)
                                ),
                                new TwoLayersFeatureSize(1, 0, 2)
                        ).ignoreVines().build()
                )
        );
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> createRegistryKey(Identifier id) {
        return Taiao.createRegistryKey(id, RegistryKeys.CONFIGURED_FEATURE);
    }
}
