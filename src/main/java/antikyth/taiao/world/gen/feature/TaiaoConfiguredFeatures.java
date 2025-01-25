// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.world.gen.feature.tree.placer.foliage.FernTreeFoliagePlacer;
import antikyth.taiao.world.gen.feature.tree.placer.foliage.SphericalFoliagePlacer;
import antikyth.taiao.world.gen.feature.tree.placer.trunk.ThinSplittingTrunkPlacer;
import antikyth.taiao.world.gen.feature.tree.placer.trunk.ThinStraightTrunkPlacer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.OptionalInt;

public class TaiaoConfiguredFeatures {
    // Trees
    public static final RegistryKey<ConfiguredFeature<?, ?>> KAURI_TREE = registryKey(Taiao.id("kauri_tree"));
    /**
     * A tī kōuka tree.
     */
    public static final RegistryKey<ConfiguredFeature<?, ?>> CABBAGE_TREE = registryKey(Taiao.id("cabbage_tree"));
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAMAKU_TREE = registryKey(Taiao.id("mamaku_tree"));
    /**
     * A whekī ponga tree.
     */
    public static final RegistryKey<ConfiguredFeature<?, ?>> WHEKII_PONGA_TREE = registryKey(
            Taiao.id("whekii_ponga_tree")
    );

    // Patches
    public static final RegistryKey<ConfiguredFeature<?, ?>> NATIVE_FOREST_TREES = registryKey(
            Taiao.id("trees_native_forest")
    );
    public static final RegistryKey<ConfiguredFeature<?, ?>> NATIVE_FOREST_GRASS_PATCH = registryKey(
            Taiao.id("patch_native_forest_grass")
    );

    public static void bootstrap(@NotNull Registerable<ConfiguredFeature<?, ?>> context) {
        Taiao.LOGGER.debug("Registering configured features");

        RegistryEntryLookup<PlacedFeature> placedFeatureLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

        // Trees
        context.register(KAURI_TREE, kauriTree());
        context.register(CABBAGE_TREE, cabbageTree());
        context.register(MAMAKU_TREE, mamakuTree());
        context.register(WHEKII_PONGA_TREE, whekiiPongaTree());

        // Patches
        context.register(NATIVE_FOREST_TREES, nativeForestTrees(placedFeatureLookup));
        context.register(NATIVE_FOREST_GRASS_PATCH, nativeForestGrassPatch());
    }

    // =================================================================================================================
    // Trees
    // =================================================================================================================

    @Contract(" -> new")
    public static @NotNull ConfiguredFeature<?, ?> kauriTree() {
        return new ConfiguredFeature<>(
                Feature.TREE,
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(TaiaoBlocks.KAURI_LOG),
                        new DarkOakTrunkPlacer(9, 3, 2),
                        BlockStateProvider.of(TaiaoBlocks.KAURI_LEAVES),
                        new DarkOakFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
                        new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
                ).ignoreVines().build()
        );
    }

    @Contract(" -> new")
    public static @NotNull ConfiguredFeature<?, ?> cabbageTree() {
        return new ConfiguredFeature<>(
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
        );
    }

    @Contract(" -> new")
    public static @NotNull ConfiguredFeature<?, ?> mamakuTree() {
        return new ConfiguredFeature<>(
                Feature.TREE,
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(TaiaoBlocks.MAMAKU_LOG),
                        new ThinStraightTrunkPlacer(5, 6, 6),
                        BlockStateProvider.of(TaiaoBlocks.MAMAKU_LEAVES),
                        new FernTreeFoliagePlacer(
                                UniformIntProvider.create(3, 4),
                                ConstantIntProvider.create(0)
                        ),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).ignoreVines().build()
        );
    }

    @Contract(" -> new")
    public static @NotNull ConfiguredFeature<?, ?> whekiiPongaTree() {
        return new ConfiguredFeature<>(
                Feature.TREE,
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(TaiaoBlocks.WHEKII_PONGA_LOG),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.of(TaiaoBlocks.WHEKII_PONGA_LEAVES),
                        new FernTreeFoliagePlacer(
                                ConstantIntProvider.create(3),
                                ConstantIntProvider.create(0)
                        ),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build()
        );
    }

    // =================================================================================================================
    // Patches
    // =================================================================================================================

    @Contract("_ -> new")
    public static @NotNull ConfiguredFeature<?, ?> nativeForestTrees(@NotNull RegistryEntryLookup<PlacedFeature> lookup) {
        return new ConfiguredFeature<>(
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfig(
                        List.of(
                                new RandomFeatureEntry(
                                        lookup.getOrThrow(TaiaoPlacedFeatures.KAURI_TREE_CHECKED),
                                        0.015f
                                ),
                                new RandomFeatureEntry(
                                        lookup.getOrThrow(TaiaoPlacedFeatures.CABBAGE_TREE_CHECKED),
                                        0.075f
                                ),
                                new RandomFeatureEntry(
                                        lookup.getOrThrow(TaiaoPlacedFeatures.MAMAKU_TREE_CHECKED),
                                        0.25f
                                ),
                                new RandomFeatureEntry(
                                        lookup.getOrThrow(TaiaoPlacedFeatures.WHEKII_PONGA_TREE_CHECKED),
                                        0.25f
                                )
                        ),
                        lookup.getOrThrow(TreePlacedFeatures.OAK_CHECKED)
                )
        );
    }

    @Contract(" -> new")
    public static @NotNull ConfiguredFeature<?, ?> nativeForestGrassPatch() {
        return new ConfiguredFeature<>(
                Feature.RANDOM_PATCH,
                VegetationConfiguredFeatures.createRandomPatchFeatureConfig(
                        new WeightedBlockStateProvider(
                                DataPool.<BlockState>builder()
                                        .add(Blocks.GRASS.getDefaultState(), 1)
                                        .add(Blocks.FERN.getDefaultState(), 4)
                        ),
                        32
                )
        );
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registryKey(Identifier id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id);
    }
}
