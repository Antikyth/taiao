// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.model;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.leaves.FruitLeavesBlock;
import antikyth.taiao.block.log.ThinLogBlock;
import antikyth.taiao.block.plant.HarvestableTripleTallPlantBlock;
import antikyth.taiao.block.plant.TripleTallPlantBlock;
import antikyth.taiao.block.state.*;
import antikyth.taiao.item.TaiaoItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.data.client.BlockStateModelGenerator.TintType;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TaiaoModelProvider extends FabricModelProvider {
	public TaiaoModelProvider(FabricDataOutput generator) {
		super(generator);
	}

	@Override
	public void generateBlockStateModels(@NotNull BlockStateModelGenerator generator) {
		TextureMap kauriLeavesTextures = TextureMap.all(new Identifier("minecraft:block/acacia_leaves"));
		TextureMap coniferFruitLeavesTextures = new TextureMap().put(
			TextureKey.ALL,
			new Identifier("minecraft:block/acacia_leaves")
		).put(TaiaoTextures.Keys.OVERLAY, Taiao.id("block/conifer_fruit_overlay"));
		TextureMap fernTreeLeavesTextures = TextureMap.all(Taiao.id("block/fern_tree_leaves"));

		TextureMap strippedCabbageTreeTextures = TaiaoModels.thinLogTextures(
			TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG,
			new Identifier("minecraft:block/stripped_oak_log"),
			null
		);
		TextureMap cabbageTreeTextures = TaiaoModels.thinLogTextures(
			TaiaoBlocks.CABBAGE_TREE_LOG,
			new Identifier("minecraft:block/acacia_log"),
			null
		);

		// Kauri foliage
		generator.registerFlowerPotPlant(
			TaiaoBlocks.KAURI_SAPLING,
			TaiaoBlocks.POTTED_KAURI_SAPLING,
			TintType.NOT_TINTED
		);
		generator.registerSingleton(TaiaoBlocks.KAURI_LEAVES, kauriLeavesTextures, Models.LEAVES);

		// Kauri wood
		generator.registerLog(TaiaoBlocks.KAURI_LOG)
			.log(TaiaoBlocks.KAURI_LOG)
			.wood(TaiaoBlocks.KAURI_WOOD);
		generator.registerLog(TaiaoBlocks.STRIPPED_KAURI_LOG)
			.log(TaiaoBlocks.STRIPPED_KAURI_LOG)
			.wood(TaiaoBlocks.STRIPPED_KAURI_WOOD);
		// Kauri wood family
		generator.registerCubeAllModelTexturePool(TaiaoBlocks.KAURI_PLANKS)
			.family(TaiaoBlocks.WoodFamily.KAURI.getBlockFamily());

		// Kahikatea foliage
		generator.registerFlowerPotPlant(
			TaiaoBlocks.KAHIKATEA_SAPLING,
			TaiaoBlocks.POTTED_KAHIKATEA_SAPLING,
			TintType.NOT_TINTED
		);
		registerFruitLeaves(generator, TaiaoBlocks.KAHIKATEA_LEAVES, coniferFruitLeavesTextures);

		// Kahikatea wood
		generator.registerLog(TaiaoBlocks.KAHIKATEA_LOG)
			.log(TaiaoBlocks.KAHIKATEA_LOG)
			.wood(TaiaoBlocks.KAHIKATEA_WOOD);
		generator.registerLog(TaiaoBlocks.STRIPPED_KAHIKATEA_LOG)
			.log(TaiaoBlocks.STRIPPED_KAHIKATEA_LOG)
			.wood(TaiaoBlocks.STRIPPED_KAHIKATEA_WOOD);
		// Kahikatea wood family
		generator.registerCubeAllModelTexturePool(TaiaoBlocks.KAHIKATEA_PLANKS)
			.family(TaiaoBlocks.WoodFamily.KAHIKATEA.getBlockFamily());

		// Rimu foliage
		generator.registerFlowerPotPlant(
			TaiaoBlocks.RIMU_SAPLING,
			TaiaoBlocks.POTTED_RIMU_SAPLING,
			TintType.NOT_TINTED
		);
		registerFruitLeaves(generator, TaiaoBlocks.RIMU_LEAVES, coniferFruitLeavesTextures);

		// Rimu wood
		generator.registerLog(TaiaoBlocks.RIMU_LOG)
			.log(TaiaoBlocks.RIMU_LOG)
			.wood(TaiaoBlocks.RIMU_WOOD);
		generator.registerLog(TaiaoBlocks.STRIPPED_RIMU_LOG)
			.log(TaiaoBlocks.STRIPPED_RIMU_LOG)
			.wood(TaiaoBlocks.STRIPPED_RIMU_WOOD);
		registerChiseledLog(generator, TaiaoBlocks.CHISELED_STRIPPED_RIMU_LOG, TaiaoBlocks.STRIPPED_RIMU_LOG, null);
		registerChiseledLog(
			generator,
			TaiaoBlocks.CHISELED_STRIPPED_RIMU_LOG,
			TaiaoBlocks.STRIPPED_RIMU_LOG,
			TaiaoBlocks.CHISELED_STRIPPED_RIMU_WOOD
		);
		// Rimu wood family
		generator.registerCubeAllModelTexturePool(TaiaoBlocks.RIMU_PLANKS)
			.family(TaiaoBlocks.WoodFamily.RIMU.getBlockFamily());

		// Tī kōuka foliage
		generator.registerFlowerPotPlant(
			TaiaoBlocks.CABBAGE_TREE_SAPLING,
			TaiaoBlocks.POTTED_CABBAGE_TREE_SAPLING,
			TintType.NOT_TINTED
		);
		generator.registerTintableCross(TaiaoBlocks.CABBAGE_TREE_LEAVES, TintType.TINTED);

		// Tī kōuka wood
		registerThinLog(generator, TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG, strippedCabbageTreeTextures);
		registerThinWood(generator, TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD, strippedCabbageTreeTextures);
		registerThinLog(generator, TaiaoBlocks.CABBAGE_TREE_LOG, cabbageTreeTextures);
		registerThinWood(generator, TaiaoBlocks.CABBAGE_TREE_WOOD, cabbageTreeTextures);

		// Mamaku foliage
		generator.registerFlowerPotPlant(
			TaiaoBlocks.MAMAKU_SAPLING,
			TaiaoBlocks.POTTED_MAMAKU_SAPLING,
			TintType.NOT_TINTED
		);
		registerDirectionalLeaves(generator, TaiaoBlocks.MAMAKU_LEAVES, fernTreeLeavesTextures);

		// Mamaku wood
		registerThinLog(generator, TaiaoBlocks.MAMAKU_LOG);
		registerThinWood(generator, TaiaoBlocks.MAMAKU_WOOD, TaiaoBlocks.MAMAKU_LOG);
		registerThinLog(generator, TaiaoBlocks.STRIPPED_MAMAKU_LOG);
		registerThinWood(generator, TaiaoBlocks.STRIPPED_MAMAKU_WOOD, TaiaoBlocks.STRIPPED_MAMAKU_LOG);
		// Mamaku wood family
		generator.registerCubeAllModelTexturePool(TaiaoBlocks.MAMAKU_PLANKS)
			.family(TaiaoBlocks.WoodFamily.MAMAKU.getBlockFamily());

		// Whekī ponga foliage
		generator.registerFlowerPotPlant(
			TaiaoBlocks.WHEKII_PONGA_SAPLING,
			TaiaoBlocks.POTTED_WHEKII_PONGA_SAPLING,
			TintType.NOT_TINTED
		);
		registerDirectionalLeaves(generator, TaiaoBlocks.WHEKII_PONGA_LEAVES, fernTreeLeavesTextures);

		// Whekī ponga wood
		generator.registerLog(TaiaoBlocks.WHEKII_PONGA_LOG)
			.log(TaiaoBlocks.WHEKII_PONGA_LOG)
			.wood(TaiaoBlocks.WHEKII_PONGA_WOOD);
		generator.registerLog(TaiaoBlocks.STRIPPED_WHEKII_PONGA_LOG)
			.log(TaiaoBlocks.STRIPPED_WHEKII_PONGA_LOG)
			.wood(TaiaoBlocks.STRIPPED_WHEKII_PONGA_WOOD);

		registerCarpet(generator, TaiaoBlocks.HARAKEKE_MAT);

		registerTripleBlock(generator, TaiaoBlocks.GIANT_CANE_RUSH, TintType.NOT_TINTED);
		generator.registerDoubleBlock(TaiaoBlocks.RAUPOO, TintType.NOT_TINTED);
		registerHarvestableTriplePlantBlock(generator, TaiaoBlocks.HARAKEKE, TintType.NOT_TINTED, false);

		registerHiinaki(generator, TaiaoBlocks.HIINAKI, Taiao.id("hiinaki_front"), Taiao.id("hiinaki_back"));
		registerHaastsEagleNest(generator, TaiaoBlocks.HAASTS_EAGLE_NEST);

		registerNorthDefaultHorizontalFacing(generator, TaiaoBlocks.THATCH_ROOF);
		registerNorthDefaultHorizontalFacing(generator, TaiaoBlocks.THATCH_ROOF_TOP);
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerator generator) {
		generator.register(TaiaoBlocks.HIINAKI.asItem(), Models.GENERATED);
		generator.register(TaiaoBlocks.HARAKEKE.asItem(), Models.GENERATED);

		generator.register(TaiaoItems.KETE, Models.GENERATED);
		generator.register(TaiaoItems.KIWI_BANNER_PATTERN, Models.GENERATED);

		// Fruit
		generator.register(TaiaoItems.CONIFER_FRUIT, Models.GENERATED);
		// Animal items
		generator.register(TaiaoItems.EEL, Models.GENERATED);
		generator.register(TaiaoItems.COOKED_EEL, Models.GENERATED);
		generator.register(TaiaoItems.WEETAA, Models.GENERATED);

		generator.register(TaiaoItems.HAASTS_EAGLE_EGG, Models.GENERATED);
		generator.register(TaiaoItems.PARTIALLY_CRACKED_HAASTS_EAGLE_EGG, Models.GENERATED);
		generator.register(TaiaoItems.CRACKED_HAASTS_EAGLE_EGG, Models.GENERATED);

		// Fish buckets
		generator.register(TaiaoItems.EEL_BUCKET, Models.GENERATED);

		// Boats
		generator.register(TaiaoItems.KAURI_BOAT, Models.GENERATED);
		generator.register(TaiaoItems.KAURI_CHEST_BOAT, Models.GENERATED);
		generator.register(TaiaoItems.KAHIKATEA_BOAT, Models.GENERATED);
		generator.register(TaiaoItems.KAHIKATEA_CHEST_BOAT, Models.GENERATED);
		generator.register(TaiaoItems.RIMU_BOAT, Models.GENERATED);
		generator.register(TaiaoItems.RIMU_CHEST_BOAT, Models.GENERATED);
		generator.register(TaiaoItems.MAMAKU_RAFT, Models.GENERATED);
		generator.register(TaiaoItems.MAMAKU_CHEST_RAFT, Models.GENERATED);

		// Spawn eggs
		generator.register(TaiaoItems.KIWI_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
		generator.register(TaiaoItems.PUUKEKO_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
		generator.register(TaiaoItems.HAASTS_EAGLE_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
		generator.register(TaiaoItems.MOA_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
		generator.register(TaiaoItems.KAAKAAPOO_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
		generator.register(TaiaoItems.AUSTRALASIAN_BITTERN_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
		generator.register(TaiaoItems.KERERUU_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
		generator.register(TaiaoItems.EEL_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
		generator.register(TaiaoItems.WEETAA_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
	}

	public static void registerNorthDefaultHorizontalFacing(@NotNull BlockStateModelGenerator generator, Block block) {
		Identifier model = Registries.BLOCK.getId(block).withPath(path -> "block/" + path);

		generator.blockStateCollector.accept(
			VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, model))
				.coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
		);
		generator.registerParentedItemModel(block, model);
	}

	public static void registerHiinaki(
		@NotNull BlockStateModelGenerator generator,
		Block hiinaki,
		Identifier frontModel,
		Identifier backModel
	) {
		frontModel = frontModel.withPath(path -> "block/" + path);
		backModel = backModel.withPath(path -> "block/" + path);

		generator.blockStateCollector.accept(
			VariantsBlockStateSupplier.create(hiinaki)
				.coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
				.coordinate(
					BlockStateVariantMap.create(TaiaoStateProperties.LONG_BLOCK_HALF)
						.register(
							LongBlockHalf.FRONT,
							BlockStateVariant.create().put(VariantSettings.MODEL, frontModel)
						)
						.register(
							LongBlockHalf.BACK,
							BlockStateVariant.create().put(VariantSettings.MODEL, backModel)
						)
				)
		);
	}

	public static void registerHaastsEagleNest(@NotNull BlockStateModelGenerator generator, Block block) {
		Identifier emptyModel = TaiaoModels.LARGE_BIRD_NEST.upload(
			block,
			TaiaoTextures.Maps.largeBirdNest(block),
			generator.modelCollector
		);
		Identifier intactModel = TaiaoModels.LARGE_BIRD_NEST_EGG.upload(
			block,
			TaiaoTextures.Maps.largeBirdNestEgg(block),
			generator.modelCollector
		);
		Identifier partiallyCrackedModel = TaiaoModels.LARGE_BIRD_NEST_EGG.upload(
			block,
			"_partially_cracked",
			TaiaoTextures.Maps.largeBirdNestEgg(block, "_partially_cracked"),
			generator.modelCollector
		);
		Identifier crackedModel = TaiaoModels.LARGE_BIRD_NEST_EGG.upload(
			block,
			"_cracked",
			TaiaoTextures.Maps.largeBirdNestEgg(block, "_cracked"),
			generator.modelCollector
		);

		generator.registerItemModel(block.asItem());

		BlockStateVariant emptyVariant = BlockStateVariant.create().put(VariantSettings.MODEL, emptyModel);

		generator.blockStateCollector.accept(
			VariantsBlockStateSupplier.create(block)
				.coordinate(
					BlockStateVariantMap.create(TaiaoStateProperties.HAASTS_EAGLE_NEST_CONTENTS)
						.register(HaastsEagleNestContents.EMPTY, emptyVariant)
						.register(HaastsEagleNestContents.CHICK, emptyVariant)
						.register(
							HaastsEagleNestContents.INTACT_EGG,
							BlockStateVariant.create().put(VariantSettings.MODEL, intactModel)
						)
						.register(
							HaastsEagleNestContents.PARTIALLY_CRACKED_EGG,
							BlockStateVariant.create().put(VariantSettings.MODEL, partiallyCrackedModel)
						)
						.register(
							HaastsEagleNestContents.CRACKED_EGG,
							BlockStateVariant.create().put(VariantSettings.MODEL, crackedModel)
						)
				)
				.coordinate(
					BlockStateVariantMap.create(TaiaoStateProperties.HORIZONTAL_DOUBLE_SQUARE_BLOCK_PART)
						.register(
							HorizontalDoubleSquareBlockPart.NORTH_WEST,
							BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R0)
						)
						.register(
							HorizontalDoubleSquareBlockPart.NORTH_EAST,
							BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90)
						)
						.register(
							HorizontalDoubleSquareBlockPart.SOUTH_EAST,
							BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180)
						)
						.register(
							HorizontalDoubleSquareBlockPart.SOUTH_WEST,
							BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270)
						)
				)
		);
	}

	public static void registerCarpet(@NotNull BlockStateModelGenerator generator, Block carpet) {
		Identifier model = TexturedModel.CARPET.get(carpet).upload(carpet, generator.modelCollector);

		generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(carpet, model));
	}

	public static void registerChiseledLog(
		@NotNull BlockStateModelGenerator generator,
		Block chiseled,
		Block base,
		@Nullable Block wood
	) {
		TextureMap textures = TextureMap.sideEnd(
			TextureMap.getId(chiseled),
			wood != null ? TextureMap.getId(base) : TextureMap.getSubId(base, "_top")
		);

		Identifier vertical = Models.CUBE_COLUMN.upload(
			wood == null ? chiseled : wood,
			textures,
			generator.modelCollector
		);
		Identifier horizontal = Models.CUBE_COLUMN_HORIZONTAL.upload(
			wood == null ? chiseled : wood,
			textures,
			generator.modelCollector
		);

		generator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(
			wood == null ? chiseled : wood,
			vertical,
			horizontal
		));
	}

	public static void registerTripleBlock(
		@NotNull BlockStateModelGenerator generator,
		Block tripleBlock,
		@NotNull TintType tintType
	) {
		Identifier top = generator.createSubModel(tripleBlock, "_top", tintType.getCrossModel(), TextureMap::cross);
		Identifier center = generator.createSubModel(
			tripleBlock,
			"_center",
			tintType.getCrossModel(),
			TextureMap::cross
		);
		Identifier bottom = generator.createSubModel(
			tripleBlock,
			"_bottom",
			tintType.getCrossModel(),
			TextureMap::cross
		);

		registerTripleBlock(generator, tripleBlock, top, center, bottom);

		generator.registerItemModel(tripleBlock, "_top");
	}

	public static void registerTripleBlock(
		@NotNull BlockStateModelGenerator generator,
		Block tripleBlock,
		Identifier topModelId,
		Identifier centerModelId,
		Identifier bottomModelId
	) {
		generator.blockStateCollector.accept(
			VariantsBlockStateSupplier.create(tripleBlock).coordinate(
				BlockStateVariantMap.create(TripleTallPlantBlock.TRIPLE_BLOCK_PART)
					.register(
						TripleBlockPart.UPPER,
						BlockStateVariant.create().put(VariantSettings.MODEL, topModelId)
					)
					.register(
						TripleBlockPart.MIDDLE,
						BlockStateVariant.create().put(VariantSettings.MODEL, centerModelId)
					)
					.register(
						TripleBlockPart.LOWER,
						BlockStateVariant.create().put(VariantSettings.MODEL, bottomModelId)
					)
			)
		);
	}

	public static void registerHarvestableTriplePlantBlock(
		@NotNull BlockStateModelGenerator generator,
		Block plant,
		@NotNull TintType tintType,
		boolean registerItem
	) {
		Identifier top = generator.createSubModel(plant, "_top", tintType.getCrossModel(), TextureMap::cross);
		Identifier center = generator.createSubModel(plant, "_center", tintType.getCrossModel(), TextureMap::cross);
		Identifier bottom = generator.createSubModel(plant, "_bottom", tintType.getCrossModel(), TextureMap::cross);

		Identifier topHarvested = generator.createSubModel(
			plant,
			"_top_harvested",
			tintType.getCrossModel(),
			TextureMap::cross
		);
		Identifier centerHarvested = generator.createSubModel(
			plant,
			"_center_harvested",
			tintType.getCrossModel(),
			TextureMap::cross
		);
		Identifier bottomHarvested = generator.createSubModel(
			plant,
			"_bottom_harvested",
			tintType.getCrossModel(),
			TextureMap::cross
		);

		generator.blockStateCollector.accept(
			VariantsBlockStateSupplier.create(plant).coordinate(
				BlockStateVariantMap.create(
						HarvestableTripleTallPlantBlock.TRIPLE_BLOCK_PART,
						HarvestableTripleTallPlantBlock.HARVESTABLE
					)
					.register(
						TripleBlockPart.UPPER,
						true,
						BlockStateVariant.create().put(VariantSettings.MODEL, top)
					)
					.register(
						TripleBlockPart.MIDDLE,
						true,
						BlockStateVariant.create().put(VariantSettings.MODEL, center)
					)
					.register(
						TripleBlockPart.LOWER,
						true,
						BlockStateVariant.create().put(VariantSettings.MODEL, bottom)
					)

					.register(
						TripleBlockPart.UPPER,
						false,
						BlockStateVariant.create().put(VariantSettings.MODEL, topHarvested)
					)
					.register(
						TripleBlockPart.MIDDLE,
						false,
						BlockStateVariant.create().put(VariantSettings.MODEL, centerHarvested)
					)
					.register(
						TripleBlockPart.LOWER,
						false,
						BlockStateVariant.create().put(VariantSettings.MODEL, bottomHarvested)
					)
			)
		);

		if (registerItem) generator.registerItemModel(plant, "_top");
	}

	public static void registerFruitLeaves(@NotNull BlockStateModelGenerator generator, Block block) {
		registerFruitLeaves(generator, block, TaiaoModels.fruitLeavesTextures(block, null, null));
	}

	public static void registerFruitLeaves(
		@NotNull BlockStateModelGenerator generator,
		Block block,
		TextureMap textures
	) {
		Identifier normalModel = Models.LEAVES.upload(block, textures, generator.modelCollector);
		Identifier fruitModel = TaiaoModels.FRUIT_LEAVES.upload(block, textures, generator.modelCollector);

		BlockStateVariantMap variantMap = BlockStateVariantMap.create(FruitLeavesBlock.FRUIT)
			.register(false, BlockStateVariant.create().put(VariantSettings.MODEL, normalModel))
			.register(true, BlockStateVariant.create().put(VariantSettings.MODEL, fruitModel));

		VariantsBlockStateSupplier variants = VariantsBlockStateSupplier.create(block).coordinate(variantMap);

		generator.blockStateCollector.accept(variants);
		generator.registerParentedItemModel(block, fruitModel);
	}

	public static void registerDirectionalLeaves(@NotNull BlockStateModelGenerator generator, Block block) {
		registerDirectionalLeaves(generator, block, TextureMap.all(block));
	}

	public static void registerDirectionalLeaves(
		@NotNull BlockStateModelGenerator generator,
		Block block,
		TextureMap textures
	) {
		generator.blockStateCollector.accept(
			VariantsBlockStateSupplier.create(
				block,
				BlockStateVariant.create()
					.put(
						VariantSettings.MODEL,
						Models.LEAVES.upload(block, textures, generator.modelCollector)
					)
			).coordinate(generator.createUpDefaultFacingVariantMap())
		);
	}

	public static void registerThinLog(BlockStateModelGenerator generator, Block block) {
		registerThinLog(generator, block, null, null);
	}

	public static void registerThinLog(
		BlockStateModelGenerator generator,
		Block block,
		@Nullable Identifier sideTexture,
		@Nullable Identifier endTexture
	) {
		registerThinLog(generator, block, TaiaoModels.thinLogTextures(block, sideTexture, endTexture));
	}

	public static void registerThinWood(BlockStateModelGenerator generator, Block woodBlock, Block logBlock) {
		registerThinWood(generator, woodBlock, TaiaoModels.thinWoodTextures(logBlock));
	}

	public static void registerThinWood(
		@NotNull BlockStateModelGenerator generator,
		Block block,
		@NotNull TextureMap textures
	) {
		Identifier side = textures.getTexture(TextureKey.SIDE);
		TextureMap woodTextures = TextureMap.sideEnd(side, side);

		registerThinLog(generator, block, woodTextures);
	}

	public static void registerThinLog(
		@NotNull BlockStateModelGenerator generator,
		Block block,
		@NotNull TextureMap textures
	) {
		TextureMap endTextures = new TextureMap().put(TextureKey.SIDE, textures.getTexture(TextureKey.END));

		Identifier downSideModelId = TaiaoModels.THIN_LOG_SIDE_DOWN.upload(block, textures, generator.modelCollector);
		Identifier upSideModelId = TaiaoModels.THIN_LOG_SIDE_UP.upload(block, textures, generator.modelCollector);
		Identifier leftSideModelId = TaiaoModels.THIN_LOG_SIDE_LEFT.upload(block, textures, generator.modelCollector);
		Identifier rightSideModelId = TaiaoModels.THIN_LOG_SIDE_RIGHT.upload(block, textures, generator.modelCollector);

		Identifier sidelessVerticalModelId = TaiaoModels.THIN_LOG_SIDELESS_VERTICAL.upload(
			block,
			textures,
			generator.modelCollector
		);
		Identifier sidelessLeftModelId = TaiaoModels.THIN_LOG_SIDELESS_LEFT.upload(
			block,
			textures,
			generator.modelCollector
		);
		Identifier sidelessRightModelId = TaiaoModels.THIN_LOG_SIDELESS_RIGHT.upload(
			block,
			textures,
			generator.modelCollector
		);
		Identifier sidelessEndModelId = TaiaoModels.THIN_LOG_SIDELESS_VERTICAL.upload(
			block,
			"_end",
			endTextures,
			generator.modelCollector
		);

		// Create a blockstate file for this thin log using the generated models
		generator.blockStateCollector.accept(createThinLogBlockState(
			block,
			upSideModelId,
			downSideModelId,
			leftSideModelId,
			rightSideModelId,
			sidelessVerticalModelId,
			sidelessLeftModelId,
			sidelessRightModelId,
			sidelessEndModelId
		));

		// Create an item model for this thin log
		TaiaoModels.uploadItem(TaiaoModels.THIN_LOG_INVENTORY, block.asItem(), textures, generator.modelCollector);
	}

	private static BlockStateVariant applyRotation(BlockStateVariant variant, @NotNull Direction face) {
		switch (face) {
			case NORTH:
				variant.put(VariantSettings.Y, VariantSettings.Rotation.R0);
				break;
			case EAST:
				variant.put(VariantSettings.Y, VariantSettings.Rotation.R90);
				break;
			case SOUTH:
				variant.put(VariantSettings.Y, VariantSettings.Rotation.R180);
				break;
			case WEST:
				variant.put(VariantSettings.Y, VariantSettings.Rotation.R270);
				break;

			case UP:
				variant.put(VariantSettings.X, VariantSettings.Rotation.R270);
				variant.put(VariantSettings.Y, VariantSettings.Rotation.R180);
				break;
			case DOWN:
				variant.put(VariantSettings.X, VariantSettings.Rotation.R90);
				break;
		}

		return variant;
	}

	public static @NotNull BlockStateSupplier createThinLogBlockState(
		Block block,
		Identifier upSideModelId,
		Identifier downSideModelId,
		Identifier leftSideModelId,
		Identifier rightSideModelId,
		Identifier sidelessVerticalModelId,
		Identifier sidelessLeftModelId,
		Identifier sidelessRightModelId,
		Identifier sidelessEndModelId
	) {
		MultipartBlockStateSupplier supplier = MultipartBlockStateSupplier.create(block);

		// Vertical sides
		supplier.with(
			When.create().set(ThinLogBlock.DOWN, true),
			BlockStateVariant.create()
				.put(VariantSettings.MODEL, downSideModelId)
		);
		supplier.with(
			When.create().set(ThinLogBlock.UP, true),
			BlockStateVariant.create()
				.put(VariantSettings.MODEL, upSideModelId)
		);

		// Left sides (east and south)
		supplier.with(
			When.create().set(ThinLogBlock.EAST, true),
			BlockStateVariant.create()
				.put(VariantSettings.MODEL, leftSideModelId)
		);
		supplier.with(
			When.create().set(ThinLogBlock.SOUTH, true),
			BlockStateVariant.create()
				.put(VariantSettings.MODEL, leftSideModelId)
				.put(VariantSettings.Y, VariantSettings.Rotation.R90)
		);

		// Right sides (west and north)
		supplier.with(
			When.create().set(ThinLogBlock.WEST, true),
			BlockStateVariant.create()
				.put(VariantSettings.MODEL, rightSideModelId)
		);
		supplier.with(
			When.create().set(ThinLogBlock.NORTH, true),
			BlockStateVariant.create()
				.put(VariantSettings.MODEL, rightSideModelId)
				.put(VariantSettings.Y, VariantSettings.Rotation.R90)
		);

		for (Direction face : Direction.values()) {
			Direction opposite = face.getOpposite();
			BooleanProperty faceProperty = ThinLogBlock.getDirectionProperty(face);

			Identifier sidelessHorizontalModelId = switch (face) {
				case NORTH, EAST, DOWN -> sidelessLeftModelId;
				case SOUTH, WEST, UP -> sidelessRightModelId;
			};

			// Determine conditions to put an end piece
			// (i.e., there is only one side piece: opposite the end piece)
			When.PropertyCondition endPieceWhen = When.create();
			When.PropertyCondition zeroSidesWhen = When.create();
			for (int i = 0; i < Direction.values().length; i++) {
				Direction otherFace = Direction.values()[i];
				boolean isOpposite = otherFace == opposite;

				BooleanProperty property = ThinLogBlock.getDirectionProperty(otherFace);

				endPieceWhen.set(property, isOpposite);
				zeroSidesWhen.set(property, false);
			}

			// When there are no connections, use the end texture on the top, bark texture on the
			// sides.
			Identifier zeroSidesModelId = face.getAxis().isVertical()
				? sidelessEndModelId
				: sidelessVerticalModelId;

			// End piece
			supplier.with(
				endPieceWhen,
				applyRotation(
					BlockStateVariant.create().put(VariantSettings.MODEL, sidelessEndModelId),
					face
				)
			);
			// Zero side pieces
			supplier.with(
				zeroSidesWhen,
				applyRotation(
					BlockStateVariant.create().put(VariantSettings.MODEL, zeroSidesModelId),
					face
				)
			);

			BooleanProperty leftProperty = face.getAxis() == Direction.Axis.X
				? ThinLogBlock.SOUTH
				: ThinLogBlock.EAST;
			BooleanProperty rightProperty = face.getAxis() == Direction.Axis.X
				? ThinLogBlock.NORTH
				: ThinLogBlock.WEST;

			if (face.getAxis().isHorizontal()) {
				// Horizontal faces

				// Vertical
				supplier.with(
					When.allOf(
						When.create().set(faceProperty, false),
						// Has a vertical side
						When.anyOf(
							When.create().set(ThinLogBlock.UP, true),
							When.create().set(ThinLogBlock.DOWN, true)
						)
					),
					applyRotation(
						BlockStateVariant.create().put(VariantSettings.MODEL, sidelessVerticalModelId),
						face
					)
				);
				// Horizontal
				supplier.with(
					When.allOf(
						When.create().set(faceProperty, false),
						// Doesn't have a vertical side
						When.create()
							.set(ThinLogBlock.UP, false)
							.set(ThinLogBlock.DOWN, false),
						// Has a horizontal side
						When.anyOf(
							When.create().set(leftProperty, true),
							When.create().set(rightProperty, true)
						)
					),
					applyRotation(
						BlockStateVariant.create().put(VariantSettings.MODEL, sidelessHorizontalModelId),
						face
					)
				);
			} else {
				// Vertical faces

				// Vertical
				supplier.with(
					When.allOf(
						When.create().set(faceProperty, false),
						// Has a north/south side
						When.anyOf(
							When.create().set(ThinLogBlock.NORTH, true),
							When.create().set(ThinLogBlock.SOUTH, true)
						)
					),
					applyRotation(
						BlockStateVariant.create().put(VariantSettings.MODEL, sidelessVerticalModelId),
						face
					)
				);
				// Horizontal
				supplier.with(
					When.allOf(
						When.create().set(faceProperty, false),
						// Doesn't have a north or south side
						When.create()
							.set(ThinLogBlock.NORTH, false)
							.set(ThinLogBlock.SOUTH, false),
						// Has a horizontal side
						When.anyOf(
							When.create().set(leftProperty, true),
							When.create().set(rightProperty, true)
						)
					),
					applyRotation(
						BlockStateVariant.create().put(VariantSettings.MODEL, sidelessHorizontalModelId),
						face
					)
				);
			}
		}

		return supplier;
	}
}
