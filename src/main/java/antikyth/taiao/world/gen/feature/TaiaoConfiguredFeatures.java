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
import net.minecraft.world.gen.foliage.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Function;

public class TaiaoConfiguredFeatures {
    protected static Map<RegistryKey<ConfiguredFeature<?, ?>>, Function<RegistryEntryLookup<PlacedFeature>, ConfiguredFeature<?, ?>>> TO_REGISTER = new HashMap<>();

    // Trees
    public static final RegistryKey<ConfiguredFeature<?, ?>> KAURI_TREE = register(
            Taiao.id("kauri_tree"),
            lookup -> new ConfiguredFeature<>(
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

    public static final RegistryKey<ConfiguredFeature<?, ?>> YOUNG_KAHIKATEA_TREE = register(
            Taiao.id("kahikatea_tree"),
            lookup -> new ConfiguredFeature<>(
                    Feature.TREE,
                    new TreeFeatureConfig.Builder(
                            BlockStateProvider.of(TaiaoBlocks.KAHIKATEA_LOG),
                            new StraightTrunkPlacer(12, 8, 8),
                            BlockStateProvider.of(TaiaoBlocks.KAHIKATEA_LEAVES),
                            new SpruceFoliagePlacer(
                                    UniformIntProvider.create(2, 5),
                                    UniformIntProvider.create(0, 2),
                                    // offset from the base of the tree until the foliage starts
                                    UniformIntProvider.create(5, 14)
                            ),
                            new TwoLayersFeatureSize(2, 0, 2)
                    ).ignoreVines().build()
            )
    );
    public static final RegistryKey<ConfiguredFeature<?, ?>> OLD_KAHIKATEA_TREE = register(
            Taiao.id("mega_kahikatea_tree"),
            lookup -> new ConfiguredFeature<>(
                    Feature.TREE,
                    new TreeFeatureConfig.Builder(
                            BlockStateProvider.of(TaiaoBlocks.KAHIKATEA_LOG),
                            new GiantTrunkPlacer(26, 14, 14),
                            BlockStateProvider.of(TaiaoBlocks.KAHIKATEA_LEAVES),
                            new MegaPineFoliagePlacer(
                                    ConstantIntProvider.create(0),
                                    ConstantIntProvider.create(0),
                                    UniformIntProvider.create(20, 24)
                            ),
                            new TwoLayersFeatureSize(1, 1, 2)
                    ).build()
            )
    );

    public static final RegistryKey<ConfiguredFeature<?, ?>> CABBAGE_TREE = register(
            Taiao.id("cabbage_tree"),
            lookup -> new ConfiguredFeature<>(
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
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAMAKU_TREE = register(
            Taiao.id("mamaku_tree"),
            lookup -> new ConfiguredFeature<>(
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
            )
    );
    public static final RegistryKey<ConfiguredFeature<?, ?>> WHEKII_PONGA_TREE = register(
            Taiao.id("whekii_ponga_tree"),
            lookup -> new ConfiguredFeature<>(
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
            )
    );

    // Tree selectors
    public static final RegistryKey<ConfiguredFeature<?, ?>> NATIVE_FOREST_TREES = register(
            Taiao.id("trees_native_forest"),
            lookup -> new ConfiguredFeature<>(
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
            )
    );
    public static final RegistryKey<ConfiguredFeature<?, ?>> NATIVE_SWAMP_TREES = register(
            Taiao.id("trees_native_swamp"),
            lookup -> new ConfiguredFeature<>(
                    Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(
                            List.of(
                                    new RandomFeatureEntry(
                                            lookup.getOrThrow(TaiaoPlacedFeatures.YOUNG_KAHIKATEA_TREE_CHECKED),
                                            0.1f
                                    ),
                                    new RandomFeatureEntry(
                                            lookup.getOrThrow(TaiaoPlacedFeatures.OLD_KAHIKATEA_TREE_CHECKED),
                                            0.01f
                                    )
                            ),
                            lookup.getOrThrow(TaiaoPlacedFeatures.CABBAGE_TREE_CHECKED)
                    )
            )
    );

    // Ground foliage patches
    public static final RegistryKey<ConfiguredFeature<?, ?>> NATIVE_FOREST_GRASS_PATCH = register(
            Taiao.id("patch_native_forest_grass"),
            lookup -> new ConfiguredFeature<>(
                    Feature.RANDOM_PATCH,
                    VegetationConfiguredFeatures.createRandomPatchFeatureConfig(
                            new WeightedBlockStateProvider(
                                    DataPool.<BlockState>builder()
                                            .add(Blocks.GRASS.getDefaultState(), 1)
                                            .add(Blocks.FERN.getDefaultState(), 4)
                            ),
                            32
                    )
            )
    );

    public static void bootstrap(@NotNull Registerable<ConfiguredFeature<?, ?>> registerable) {
        Taiao.LOGGER.debug("Registering configured features");

        RegistryEntryLookup<PlacedFeature> placedFeatureLookup = registerable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

        for (Map.Entry<RegistryKey<ConfiguredFeature<?, ?>>, Function<RegistryEntryLookup<PlacedFeature>, ConfiguredFeature<?, ?>>> entry : TO_REGISTER.entrySet()) {
            registerable.register(entry.getKey(), entry.getValue().apply(placedFeatureLookup));
        }
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> register(
            Identifier id,
            Function<RegistryEntryLookup<PlacedFeature>, ConfiguredFeature<?, ?>> configuredFeatureFactory
    ) {
        RegistryKey<ConfiguredFeature<?, ?>> key = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id);

        TO_REGISTER.put(key, configuredFeatureFactory);

        return key;
    }
}
