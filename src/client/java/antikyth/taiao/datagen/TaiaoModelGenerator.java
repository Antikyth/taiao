package antikyth.taiao.datagen;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.ThinLogBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

public class TaiaoModelGenerator extends FabricModelProvider {
    public TaiaoModelGenerator(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        registerThinLog(generator,
                (ThinLogBlock) TaiaoBlocks.CABBAGE_TREE_LOG,
                TaiaoModels.thinLogTextures(new Identifier("minecraft:block/acacia_log"),
                        Identifier.of(Taiao.MOD_ID, "block/cabbage_tree_log_top")));
        registerThinLog(generator,
                (ThinLogBlock) TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG,
                TaiaoModels.thinLogTextures(new Identifier("minecraft:block/stripped_oak_log"),
                        Identifier.of(Taiao.MOD_ID, "block/stripped_cabbage_tree_log_top")));
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
    }

    public static void registerThinLog(BlockStateModelGenerator generator, ThinLogBlock block, TextureMap textures) {
        // Use the END texture for the SIDE of end_noside models.
        TextureMap noSideEndTextures = new TextureMap().put(TextureKey.SIDE, textures.getTexture(TextureKey.END));

        // Create models for this thin log using the given textures
        Identifier noSideModelId = TaiaoModels.THIN_LOG_NOSIDE.upload(block, textures, generator.modelCollector);
        Identifier noSideEndModelId = TaiaoModels.THIN_LOG_NOSIDE.upload(block,
                "_end",
                noSideEndTextures,
                generator.modelCollector);
        Identifier sideModelId = TaiaoModels.THIN_LOG_SIDE.upload(block, textures, generator.modelCollector);

        // Create a blockstate file for this thin log using the generated models
        generator.blockStateCollector.accept(createThinLogBlockState(block,
                noSideModelId,
                noSideEndModelId,
                sideModelId));

        // Create an item model for this thin log
        TaiaoModels.uploadItem(TaiaoModels.THIN_LOG_INVENTORY, block.asItem(), textures, generator.modelCollector);
    }

    public static BlockStateSupplier createThinLogBlockState(ThinLogBlock block, String noSideModelName, String noSideEndModelName, String sideModelName) {
        Identifier noSideModelId = new Identifier(Taiao.MOD_ID, "block/" + noSideModelName);
        Identifier noSideEndModelId = new Identifier(Taiao.MOD_ID, "block/" + noSideEndModelName);
        Identifier sideModelId = new Identifier(Taiao.MOD_ID, "block/" + sideModelName);

        return createThinLogBlockState(block, noSideModelId, noSideEndModelId, sideModelId);
    }

    public static BlockStateSupplier createThinLogBlockState(ThinLogBlock block, Identifier noSideModelId, Identifier noSideEndModelId, Identifier sideModelId) {
        MultipartBlockStateSupplier supplier = MultipartBlockStateSupplier.create(block);

        // Side pieces
        supplier.with(When.create().set(ThinLogBlock.NORTH, true),
                        BlockStateVariant.create().put(VariantSettings.MODEL, sideModelId))
                .with(When.create().set(ThinLogBlock.EAST, true),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, sideModelId)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .with(When.create().set(ThinLogBlock.SOUTH, true),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, sideModelId)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R180))
                .with(When.create().set(ThinLogBlock.WEST, true),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, sideModelId)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R270))
                .with(When.create().set(ThinLogBlock.UP, true),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, sideModelId)
                                .put(VariantSettings.X, VariantSettings.Rotation.R270))
                .with(When.create().set(ThinLogBlock.DOWN, true),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, sideModelId)
                                .put(VariantSettings.X, VariantSettings.Rotation.R90));

        // No side pieces - horizontal
//        supplier.with(When.allOf(When.create().set(ThinLogBlock.NORTH, true),
//                        When.create().set(ThinLogBlock.UP, false),
//                        When.create().set(ThinLogBlock.DOWN, false)),
//                BlockStateVariant.create().put(VariantSettings.MODEL, noSideModelId));

        // No side pieces - end
        supplier.with(When.allOf(When.create().set(ThinLogBlock.NORTH, false),
                                When.create().set(ThinLogBlock.UP, false),
                                When.create().set(ThinLogBlock.EAST, false),
                                When.create().set(ThinLogBlock.DOWN, false),
                                When.create().set(ThinLogBlock.WEST, false),
                                When.create().set(ThinLogBlock.SOUTH, true)),
                        BlockStateVariant.create().put(VariantSettings.MODEL, noSideEndModelId))
                .with(When.allOf(When.create().set(ThinLogBlock.EAST, false),
                                When.create().set(ThinLogBlock.UP, false),
                                When.create().set(ThinLogBlock.SOUTH, false),
                                When.create().set(ThinLogBlock.DOWN, false),
                                When.create().set(ThinLogBlock.NORTH, false),
                                When.create().set(ThinLogBlock.WEST, true)),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, noSideEndModelId)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .with(When.allOf(When.create().set(ThinLogBlock.SOUTH, false),
                                When.create().set(ThinLogBlock.UP, false),
                                When.create().set(ThinLogBlock.WEST, false),
                                When.create().set(ThinLogBlock.DOWN, false),
                                When.create().set(ThinLogBlock.EAST, false),
                                When.create().set(ThinLogBlock.NORTH, true)),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, noSideEndModelId)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R180))
                .with(When.allOf(When.create().set(ThinLogBlock.WEST, false),
                                When.create().set(ThinLogBlock.UP, false),
                                When.create().set(ThinLogBlock.NORTH, false),
                                When.create().set(ThinLogBlock.DOWN, false),
                                When.create().set(ThinLogBlock.SOUTH, false),
                                When.create().set(ThinLogBlock.EAST, true)),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, noSideEndModelId)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R270))
                .with(When.allOf(When.create().set(ThinLogBlock.UP, false),
                                When.create().set(ThinLogBlock.NORTH, false),
                                When.create().set(ThinLogBlock.EAST, false),
                                When.create().set(ThinLogBlock.SOUTH, false),
                                When.create().set(ThinLogBlock.WEST, false),
                                When.create().set(ThinLogBlock.DOWN, true)),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, noSideEndModelId)
                                .put(VariantSettings.X, VariantSettings.Rotation.R270))
                .with(When.allOf(When.create().set(ThinLogBlock.DOWN, false),
                                When.create().set(ThinLogBlock.NORTH, false),
                                When.create().set(ThinLogBlock.EAST, false),
                                When.create().set(ThinLogBlock.SOUTH, false),
                                When.create().set(ThinLogBlock.WEST, false),
                                When.create().set(ThinLogBlock.UP, true)),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, noSideEndModelId)
                                .put(VariantSettings.X, VariantSettings.Rotation.R90));

        // No side pieces - vertical
        supplier.with(When.allOf(When.create().set(ThinLogBlock.NORTH, false),
                                When.anyOf(When.create().set(ThinLogBlock.UP, true),
                                        When.create().set(ThinLogBlock.EAST, true),
                                        When.create().set(ThinLogBlock.DOWN, true),
                                        When.create().set(ThinLogBlock.WEST, true),
                                        When.create().set(ThinLogBlock.SOUTH, false))),
                        BlockStateVariant.create().put(VariantSettings.MODEL, noSideModelId))
                .with(When.allOf(When.create().set(ThinLogBlock.EAST, false),
                                When.anyOf(When.create().set(ThinLogBlock.UP, true),
                                        When.create().set(ThinLogBlock.SOUTH, true),
                                        When.create().set(ThinLogBlock.DOWN, true),
                                        When.create().set(ThinLogBlock.NORTH, true),
                                        When.create().set(ThinLogBlock.WEST, false))),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, noSideModelId)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .with(When.allOf(When.create().set(ThinLogBlock.SOUTH, false),
                                When.anyOf(When.create().set(ThinLogBlock.UP, true),
                                        When.create().set(ThinLogBlock.WEST, true),
                                        When.create().set(ThinLogBlock.DOWN, true),
                                        When.create().set(ThinLogBlock.EAST, true),
                                        When.create().set(ThinLogBlock.NORTH, false))),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, noSideModelId)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R180))
                .with(When.allOf(When.create().set(ThinLogBlock.WEST, false),
                                When.anyOf(When.create().set(ThinLogBlock.UP, true),
                                        When.create().set(ThinLogBlock.NORTH, true),
                                        When.create().set(ThinLogBlock.DOWN, true),
                                        When.create().set(ThinLogBlock.SOUTH, true),
                                        When.create().set(ThinLogBlock.EAST, false))),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, noSideModelId)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R270))
                .with(When.allOf(When.create().set(ThinLogBlock.UP, false),
                                When.anyOf(When.create().set(ThinLogBlock.NORTH, true),
                                        When.create().set(ThinLogBlock.EAST, true),
                                        When.create().set(ThinLogBlock.SOUTH, true),
                                        When.create().set(ThinLogBlock.WEST, true),
                                        When.create().set(ThinLogBlock.DOWN, false))),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, noSideModelId)
                                .put(VariantSettings.X, VariantSettings.Rotation.R270))
                .with(When.allOf(When.create().set(ThinLogBlock.DOWN, false),
                                When.anyOf(When.create().set(ThinLogBlock.NORTH, true),
                                        When.create().set(ThinLogBlock.EAST, true),
                                        When.create().set(ThinLogBlock.SOUTH, true),
                                        When.create().set(ThinLogBlock.WEST, true),
                                        When.create().set(ThinLogBlock.UP, false))),
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, noSideModelId)
                                .put(VariantSettings.X, VariantSettings.Rotation.R90));

        return supplier;
    }
}
