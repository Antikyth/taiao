package antikyth.taiao.datagen;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.ThinLogBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;

import java.util.Optional;

public class TaiaoModelGenerator extends FabricModelProvider {
    public TaiaoModelGenerator(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        registerThinLog(generator,
                (ThinLogBlock) TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG,
                Optional.of(new Identifier("minecraft:block/stripped_oak_log")),
                Optional.empty());
        registerThinLog(generator,
                (ThinLogBlock) TaiaoBlocks.CABBAGE_TREE_LOG,
                Optional.of(new Identifier("minecraft:block/acacia_log")),
                Optional.empty());
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
    }

    public static void registerThinLog(BlockStateModelGenerator generator, ThinLogBlock block, Optional<Identifier> sideTexture, Optional<Identifier> endTexture) {
        registerThinLog(generator, block, TaiaoModels.thinLogTextures(block, sideTexture, endTexture));
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

    /**
     * Gets the rotation variant setting appropriate for the given {@code direction}.
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private static Pair<VariantSetting<VariantSettings.Rotation>, VariantSettings.Rotation> getRotation(Direction direction) {
        return switch (direction) {
            case NORTH -> new Pair<>(VariantSettings.Y, VariantSettings.Rotation.R0);
            case EAST -> new Pair<>(VariantSettings.Y, VariantSettings.Rotation.R90);
            case SOUTH -> new Pair<>(VariantSettings.Y, VariantSettings.Rotation.R180);
            case WEST -> new Pair<>(VariantSettings.Y, VariantSettings.Rotation.R270);
            case UP -> new Pair<>(VariantSettings.X, VariantSettings.Rotation.R270);
            case DOWN -> new Pair<>(VariantSettings.X, VariantSettings.Rotation.R90);
        };
    }

    public static BlockStateSupplier createThinLogBlockState(ThinLogBlock block, Identifier noSideModelId, Identifier noSideEndModelId, Identifier sideModelId) {
        MultipartBlockStateSupplier supplier = MultipartBlockStateSupplier.create(block);

        // Side pieces
        for (Direction direction : Direction.values()) {
            Pair<VariantSetting<VariantSettings.Rotation>, VariantSettings.Rotation> rotation = getRotation(direction);

            // Generate blockstates
            supplier.with(When.create().set(ThinLogBlock.getDirectionProperty(direction), true),
                    BlockStateVariant.create()
                            .put(VariantSettings.MODEL, sideModelId)
                            .put(rotation.getLeft(), rotation.getRight()));
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
            supplier.with(When.allOf(endPieceWhens),
                    BlockStateVariant.create()
                            .put(VariantSettings.MODEL, noSideEndModelId)
                            .put(rotation.getLeft(), rotation.getRight()));
            // Generate side piece blockstates
            supplier.with(When.allOf(When.create().set(ThinLogBlock.getDirectionProperty(direction), false),
                            When.anyOf(sidePieceWhens)),
                    BlockStateVariant.create()
                            .put(VariantSettings.MODEL, noSideModelId)
                            .put(rotation.getLeft(), rotation.getRight()));
        }

        return supplier;
    }
}
