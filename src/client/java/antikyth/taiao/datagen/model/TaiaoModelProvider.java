// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.model;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.leaves.FruitLeavesBlock;
import antikyth.taiao.block.log.ThinLogBlock;
import antikyth.taiao.item.TaiaoItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.data.client.BlockStateModelGenerator.TintType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
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
        ).put(TaiaoModels.OVERLAY_KEY, Taiao.id("block/conifer_fruit_overlay"));
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

        generator.registerDoubleBlock(TaiaoBlocks.RAUPOO, TintType.NOT_TINTED);
    }

    @Override
    public void generateItemModels(@NotNull ItemModelGenerator generator) {
        generator.register(TaiaoItems.CONIFER_FRUIT, Models.GENERATED);

        generator.register(TaiaoItems.KAURI_BOAT, Models.GENERATED);
        generator.register(TaiaoItems.KAURI_CHEST_BOAT, Models.GENERATED);
        generator.register(TaiaoItems.KAHIKATEA_BOAT, Models.GENERATED);
        generator.register(TaiaoItems.KAHIKATEA_CHEST_BOAT, Models.GENERATED);
        generator.register(TaiaoItems.RIMU_BOAT, Models.GENERATED);
        generator.register(TaiaoItems.RIMU_CHEST_BOAT, Models.GENERATED);
        generator.register(TaiaoItems.MAMAKU_RAFT, Models.GENERATED);
        generator.register(TaiaoItems.MAMAKU_CHEST_RAFT, Models.GENERATED);

        generator.register(TaiaoItems.KIWI_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
        generator.register(TaiaoItems.PUUKEKO_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
        generator.register(TaiaoItems.MOA_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
        generator.register(TaiaoItems.KAAKAAPOO_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
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
        // Use the END texture for the SIDE of end_noside models.
        TextureMap noSideEndTextures = new TextureMap().put(TextureKey.SIDE, textures.getTexture(TextureKey.END));

        // Create models for this thin log using the given textures
        Identifier noSideModelId = TaiaoModels.THIN_LOG_NOSIDE.upload(block, textures, generator.modelCollector);
        Identifier noSideEndModelId = TaiaoModels.THIN_LOG_NOSIDE.upload(
                block,
                "_end",
                noSideEndTextures,
                generator.modelCollector
        );
        Identifier sideModelId = TaiaoModels.THIN_LOG_SIDE.upload(block, textures, generator.modelCollector);

        // Create a blockstate file for this thin log using the generated models
        generator.blockStateCollector.accept(createThinLogBlockState(
                block,
                noSideModelId,
                noSideEndModelId,
                sideModelId
        ));

        // Create an item model for this thin log
        TaiaoModels.uploadItem(TaiaoModels.THIN_LOG_INVENTORY, block.asItem(), textures, generator.modelCollector);
    }

    /**
     * Gets the rotation variant setting appropriate for the given {@code direction}.
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private static @NotNull Pair<VariantSetting<VariantSettings.Rotation>, VariantSettings.Rotation> getRotation(
            @NotNull Direction direction
    ) {
        return switch (direction) {
            case NORTH -> new Pair<>(VariantSettings.Y, VariantSettings.Rotation.R0);
            case EAST -> new Pair<>(VariantSettings.Y, VariantSettings.Rotation.R90);
            case SOUTH -> new Pair<>(VariantSettings.Y, VariantSettings.Rotation.R180);
            case WEST -> new Pair<>(VariantSettings.Y, VariantSettings.Rotation.R270);
            case UP -> new Pair<>(VariantSettings.X, VariantSettings.Rotation.R270);
            case DOWN -> new Pair<>(VariantSettings.X, VariantSettings.Rotation.R90);
        };
    }

    public static @NotNull BlockStateSupplier createThinLogBlockState(
            Block block,
            Identifier noSideModelId,
            Identifier noSideEndModelId,
            Identifier sideModelId
    ) {
        MultipartBlockStateSupplier supplier = MultipartBlockStateSupplier.create(block);

        // Side pieces
        for (Direction direction : Direction.values()) {
            Pair<VariantSetting<VariantSettings.Rotation>, VariantSettings.Rotation> rotation = getRotation(direction);

            // Generate blockstates
            supplier.with(
                    When.create().set(ThinLogBlock.getDirectionProperty(direction), true),
                    BlockStateVariant.create()
                            .put(VariantSettings.MODEL, sideModelId)
                            .put(rotation.getLeft(), rotation.getRight())
            );
        }

        // No-side pieces
        for (Direction direction : Direction.values()) {
            Pair<VariantSetting<VariantSettings.Rotation>, VariantSettings.Rotation> rotation = getRotation(direction);

            // Determine conditions for an end piece vs. a side piece
            When[] endPieceWhens = new When[Direction.values().length];
            When[] sidePieceWhens = new When[Direction.values().length];
            for (int i = 0; i < Direction.values().length; i++) {
                boolean isOppositeDirection = i == direction.getOpposite().getId();

                BooleanProperty property = ThinLogBlock.getDirectionProperty(Direction.values()[i]);

                endPieceWhens[i] = When.create().set(property, isOppositeDirection);
                sidePieceWhens[i] = When.create().set(property, !isOppositeDirection);
            }

            // Generate end piece blockstates
            supplier.with(
                    When.allOf(endPieceWhens),
                    BlockStateVariant.create()
                            .put(VariantSettings.MODEL, noSideEndModelId)
                            .put(rotation.getLeft(), rotation.getRight())
            );
            // Generate side piece blockstates
            supplier.with(
                    When.allOf(
                            When.create().set(ThinLogBlock.getDirectionProperty(direction), false),
                            When.anyOf(sidePieceWhens)
                    ),
                    BlockStateVariant.create()
                            .put(VariantSettings.MODEL, noSideModelId)
                            .put(rotation.getLeft(), rotation.getRight())
            );
        }

        return supplier;
    }
}
