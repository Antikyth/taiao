// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.model;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.ThinLogBlock;
import antikyth.taiao.item.TaiaoItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
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
        Identifier kauriLeaves = new Identifier("minecraft:block/acacia_leaves");

        Identifier strippedCabbageTreeLogSide = new Identifier("minecraft:block/stripped_oak_log");
        Identifier cabbageTreeLogSide = new Identifier("minecraft:block/acacia_log");

        generator.registerFlowerPotPlant(
                TaiaoBlocks.KAURI_SAPLING,
                TaiaoBlocks.POTTED_KAURI_SAPLING,
                BlockStateModelGenerator.TintType.NOT_TINTED
        );
        generator.registerSingleton(TaiaoBlocks.KAURI_LEAVES, TextureMap.all(kauriLeaves), Models.LEAVES);

        generator.registerLog(TaiaoBlocks.KAURI_LOG).log(TaiaoBlocks.KAURI_LOG).wood(TaiaoBlocks.KAURI_WOOD);
        generator.registerLog(TaiaoBlocks.STRIPPED_KAURI_LOG)
                .log(TaiaoBlocks.STRIPPED_KAURI_LOG)
                .wood(TaiaoBlocks.STRIPPED_KAURI_WOOD);
        generator.registerSimpleCubeAll(TaiaoBlocks.KAURI_PLANKS);


        generator.registerFlowerPotPlant(
                TaiaoBlocks.CABBAGE_TREE_SAPLING,
                TaiaoBlocks.POTTED_CABBAGE_TREE_SAPLING,
                BlockStateModelGenerator.TintType.NOT_TINTED
        );
        generator.registerTintableCross(TaiaoBlocks.CABBAGE_TREE_LEAVES, BlockStateModelGenerator.TintType.TINTED);

        registerThinLog(
                generator,
                (ThinLogBlock) TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG,
                strippedCabbageTreeLogSide,
                null
        );
        registerThinLog(
                generator,
                (ThinLogBlock) TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD,
                strippedCabbageTreeLogSide,
                strippedCabbageTreeLogSide
        );
        registerThinLog(generator, (ThinLogBlock) TaiaoBlocks.CABBAGE_TREE_LOG, cabbageTreeLogSide, null);
        registerThinLog(
                generator,
                (ThinLogBlock) TaiaoBlocks.CABBAGE_TREE_WOOD,
                cabbageTreeLogSide,
                cabbageTreeLogSide
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(TaiaoItems.PUUKEKO_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
        generator.register(TaiaoItems.MOA_SPAWN_EGG, TaiaoModels.SPAWN_EGG);
    }

    public static void registerThinLog(BlockStateModelGenerator generator, ThinLogBlock block) {
        registerThinLog(generator, block, null, null);
    }

    public static void registerThinLog(
            BlockStateModelGenerator generator,
            ThinLogBlock block,
            @Nullable Identifier sideTexture,
            @Nullable Identifier endTexture
    ) {
        registerThinLog(generator, block, TaiaoModels.thinLogTextures(block, sideTexture, endTexture));
    }

    public static void registerThinLog(BlockStateModelGenerator generator, ThinLogBlock block, TextureMap textures) {
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

    public static BlockStateSupplier createThinLogBlockState(
            ThinLogBlock block,
            String noSideModelName,
            String noSideEndModelName,
            String sideModelName
    ) {
        Identifier noSideModelId = new Identifier(Taiao.MOD_ID, "block/" + noSideModelName);
        Identifier noSideEndModelId = new Identifier(Taiao.MOD_ID, "block/" + noSideEndModelName);
        Identifier sideModelId = new Identifier(Taiao.MOD_ID, "block/" + sideModelName);

        return createThinLogBlockState(block, noSideModelId, noSideEndModelId, sideModelId);
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
            ThinLogBlock block,
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
