// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.leaves.FruitLeavesBlock;
import antikyth.taiao.entity.TaiaoEntityTypeTags;
import antikyth.taiao.world.gen.blockpredicate.TaiaoBlockPredicates;
import antikyth.taiao.world.gen.blockpredicate.WithinHorizontalRangeBlockPredicate;
import antikyth.taiao.world.gen.entityprovider.EntityTypeProvider;
import antikyth.taiao.world.gen.feature.config.HiinakiFeatureConfig;
import antikyth.taiao.world.gen.feature.tree.placer.foliage.FernBushFoliagePlacer;
import antikyth.taiao.world.gen.feature.tree.placer.foliage.FernTreeFoliagePlacer;
import antikyth.taiao.world.gen.feature.tree.placer.foliage.SphericalFoliagePlacer;
import antikyth.taiao.world.gen.feature.tree.placer.trunk.ThinSplittingTrunkPlacer;
import antikyth.taiao.world.gen.feature.tree.placer.trunk.ThinStraightTrunkPlacer;
import antikyth.taiao.world.gen.loot.TaiaoLootTables;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.foliage.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.BlockFilterPlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Function;

public class TaiaoConfiguredFeatures {
	protected static Map<RegistryKey<ConfiguredFeature<?, ?>>, Function<RegistryEntryLookup<PlacedFeature>, ConfiguredFeature<?, ?>>> TO_REGISTER = new HashMap<>();

	public static final RegistryKey<ConfiguredFeature<?, ?>> HIINAKI = register(
		Taiao.id("hiinaki"),
		lookup -> new ConfiguredFeature<>(
			TaiaoFeatures.HIINAKI,
			new HiinakiFeatureConfig(
				new HiinakiFeatureConfig.ContentWeights(5, 4, 1),
				TaiaoLootTables.HIINAKI_BAIT,
				EntityTypeProvider.ofTag(TaiaoEntityTypeTags.HIINAKI_TRAPPED_ENTITIES)
			)
		)
	);

	// Trees
	public static final RegistryKey<ConfiguredFeature<?, ?>> KAURI_TREE = register(
		Taiao.id("kauri_tree"),
		lookup -> new ConfiguredFeature<>(
			Feature.TREE,
			new TreeFeatureConfig.Builder(
				BlockStateProvider.of(TaiaoBlocks.KAURI_LOG),
				new DarkOakTrunkPlacer(10, 7, 7),
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
				fruitLeavesProvider(TaiaoBlocks.KAHIKATEA_LEAVES, 12, 1),
				new SpruceFoliagePlacer(
					UniformIntProvider.create(2, 3),
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
				fruitLeavesProvider(TaiaoBlocks.KAHIKATEA_LEAVES, 16, 1),
				new MegaPineFoliagePlacer(
					ConstantIntProvider.create(0),
					ConstantIntProvider.create(0),
					UniformIntProvider.create(20, 24)
				),
				new TwoLayersFeatureSize(1, 1, 2)
			).build()
		)
	);

	public static final RegistryKey<ConfiguredFeature<?, ?>> RIMU_TREE = register(
		Taiao.id("mega_rimu_tree"),
		lookup -> new ConfiguredFeature<>(
			Feature.TREE,
			new TreeFeatureConfig.Builder(
				BlockStateProvider.of(TaiaoBlocks.RIMU_LOG),
				new GiantTrunkPlacer(20, 8, 8),
				fruitLeavesProvider(TaiaoBlocks.RIMU_LEAVES, 8, 1),
				new MegaPineFoliagePlacer(
					ConstantIntProvider.create(0),
					ConstantIntProvider.create(0),
					UniformIntProvider.create(8, 14)
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

	public static RegistryKey<ConfiguredFeature<?, ?>> FERN_BUSH = register(
		Taiao.id("fern_bush"),
		lookup -> new ConfiguredFeature<>(
			Feature.TREE,
			new TreeFeatureConfig.Builder(
				BlockStateProvider.of(TaiaoBlocks.WHEKII_PONGA_LOG),
				new StraightTrunkPlacer(1, 0, 0),
				BlockStateProvider.of(TaiaoBlocks.WHEKII_PONGA_LEAVES),
				new FernBushFoliagePlacer(
					ConstantIntProvider.create(1),
					ConstantIntProvider.create(0),
					ConstantIntProvider.create(1)
				),
				new TwoLayersFeatureSize(0, 0, 0)
			).build()
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
						0.005f
					),
					new RandomFeatureEntry(
						lookup.getOrThrow(TaiaoPlacedFeatures.RIMU_TREE_CHECKED),
						0.0075f
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
					),
					new RandomFeatureEntry(
						lookup.getOrThrow(TaiaoPlacedFeatures.FERN_BUSH_CHECKED),
						0.2f
					)
				),
				lookup.getOrThrow(TreePlacedFeatures.OAK_CHECKED)
			)
		)
	);
	public static final RegistryKey<ConfiguredFeature<?, ?>> KAHIKATEA_SWAMP_TREES = register(
		Taiao.id("trees_kahikatea_swamp"),
		lookup -> new ConfiguredFeature<>(
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(
				List.of(
					new RandomFeatureEntry(
						lookup.getOrThrow(TaiaoPlacedFeatures.YOUNG_KAHIKATEA_TREE_CHECKED),
						0.5f
					),
					new RandomFeatureEntry(
						lookup.getOrThrow(TaiaoPlacedFeatures.OLD_KAHIKATEA_TREE_CHECKED),
						0.05f
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
	public static final RegistryKey<ConfiguredFeature<?, ?>> RAUPOO_PATCH = register(
		Taiao.id("patch_raupoo"),
		lookup -> new ConfiguredFeature<>(
			Feature.RANDOM_PATCH,
			createReedsRandomPatchFeatureConfig(64, TaiaoBlocks.RAUPOO, 4)
		)
	);
	public static final RegistryKey<ConfiguredFeature<?, ?>> GIANT_CANE_RUSH_PATCH = register(
		Taiao.id("patch_giant_cane_rush"),
		lookup -> new ConfiguredFeature<>(
			Feature.RANDOM_PATCH,
			createTripleTallPlantRandomPatchFeatureConfig(96, TaiaoBlocks.GIANT_CANE_RUSH)
		)
	);
	public static final RegistryKey<ConfiguredFeature<?, ?>> HARAKEKE_PATCH = register(
		Taiao.id("patch_harakeke"),
		lookup -> new ConfiguredFeature<>(
			Feature.RANDOM_PATCH,
			createTripleTallPlantRandomPatchFeatureConfig(96, TaiaoBlocks.HARAKEKE)
		)
	);

	public static void bootstrap(@NotNull Registerable<ConfiguredFeature<?, ?>> registerable) {
		Taiao.LOGGER.debug("Registering configured features");

		RegistryEntryLookup<PlacedFeature> placedFeatureLookup = registerable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

		for (Map.Entry<RegistryKey<ConfiguredFeature<?, ?>>, Function<RegistryEntryLookup<PlacedFeature>, ConfiguredFeature<?, ?>>> entry : TO_REGISTER.entrySet()) {
			registerable.register(entry.getKey(), entry.getValue().apply(placedFeatureLookup));
		}
	}

	@Contract("_, _, _ -> new")
	public static @NotNull BlockStateProvider fruitLeavesProvider(
		@NotNull Block fruitLeaves,
		int fruitlessWeight,
		int fruitedWeight
	) {
		return new WeightedBlockStateProvider(DataPool.<BlockState>builder()
			.add(fruitLeaves.getDefaultState().with(FruitLeavesBlock.FRUIT, false), fruitlessWeight)
			.add(fruitLeaves.getDefaultState().with(FruitLeavesBlock.FRUIT, true), fruitedWeight));
	}

	@Contract("_, _ -> new")
	public static @NotNull RandomPatchFeatureConfig createTripleTallPlantRandomPatchFeatureConfig(
		int tries,
		@NotNull Block block
	) {
		return createTripleTallPlantRandomPatchFeatureConfig(tries, block.getDefaultState());
	}

	@Contract("_, _ -> new")
	public static @NotNull RandomPatchFeatureConfig createTripleTallPlantRandomPatchFeatureConfig(
		int tries,
		BlockState state
	) {
		return ConfiguredFeatures.createRandomPatchFeatureConfig(
			tries,
			PlacedFeatures.createEntry(
				TaiaoFeatures.CUSTOM_PLACEMENT_BLOCK,
				new SimpleBlockFeatureConfig(BlockStateProvider.of(state)),
				BlockFilterPlacementModifier.of(
					BlockPredicate.bothOf(
						BlockPredicate.wouldSurvive(state, BlockPos.ORIGIN),
						// Positions available
						BlockPredicate.allOf(
							TaiaoBlockPredicates.air(),
							TaiaoBlockPredicates.air(Direction.UP, 1),
							TaiaoBlockPredicates.air(Direction.UP, 2)
						)
					)
				)
			)
		);
	}

	@Contract("_, _, _ -> new")
	public static @NotNull RandomPatchFeatureConfig createReedsRandomPatchFeatureConfig(
		int tries,
		@NotNull Block block,
		int shoreRange
	) {
		return createReedsRandomPatchFeatureConfig(tries, block.getDefaultState(), shoreRange);
	}

	@Contract("_, _, _ -> new")
	public static @NotNull RandomPatchFeatureConfig createReedsRandomPatchFeatureConfig(
		int tries,
		BlockState state,
		int shoreRange
	) {
		return ConfiguredFeatures.createRandomPatchFeatureConfig(
			tries,
			PlacedFeatures.createEntry(
				Feature.SIMPLE_BLOCK,
				new SimpleBlockFeatureConfig(BlockStateProvider.of(state)),
				BlockFilterPlacementModifier.of(
					BlockPredicate.bothOf(
						BlockPredicate.wouldSurvive(state, BlockPos.ORIGIN),
						// Positions available
						BlockPredicate.bothOf(
							// Upper half is free of water
							TaiaoBlockPredicates.air(Direction.UP),
							BlockPredicate.eitherOf(
								// On land
								TaiaoBlockPredicates.air(),
								// or near the shore
								BlockPredicate.bothOf(
									TaiaoBlockPredicates.water(),
									TaiaoBlockPredicates.withinHorizontalRange(
										BlockPredicate.solid(),
										WithinHorizontalRangeBlockPredicate.Shape.CIRCLE,
										true,
										shoreRange
									)
								)
							)
						)
					)
				)
			)
		);
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
