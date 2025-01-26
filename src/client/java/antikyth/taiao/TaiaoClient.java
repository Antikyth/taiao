// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.entity.render.TaiaoEntityModels;
import antikyth.taiao.item.TaiaoItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.NotNull;

public class TaiaoClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TaiaoEntityModels.initialize();

        registerColors();
        registerRenderLayers();
    }

    public static void registerColors() {
        Taiao.LOGGER.debug("Registering block and item dynamic color providers");

        // Leaves
        registerFoliageColors(
                TaiaoBlocks.KAURI_LEAVES,
                TaiaoBlocks.KAHIKATEA_LEAVES,
                TaiaoBlocks.CABBAGE_TREE_LEAVES,
                TaiaoBlocks.MAMAKU_LEAVES,
                TaiaoBlocks.WHEKII_PONGA_LEAVES
        );

        // Spawn eggs
        registerSpawnEggColors(
                TaiaoItems.KIWI_SPAWN_EGG,
                TaiaoItems.PUUKEKO_SPAWN_EGG,
                TaiaoItems.MOA_SPAWN_EGG,
                TaiaoItems.KAAKAAPOO_SPAWN_EGG
        );
    }

    public static void registerRenderLayers() {
        Taiao.LOGGER.debug("Registering block render layers");

        BlockRenderLayerMap.INSTANCE.putBlock(TaiaoBlocks.CABBAGE_TREE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TaiaoBlocks.POTTED_CABBAGE_TREE_SAPLING, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TaiaoBlocks.KAURI_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TaiaoBlocks.POTTED_KAURI_SAPLING, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TaiaoBlocks.MAMAKU_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TaiaoBlocks.POTTED_MAMAKU_SAPLING, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TaiaoBlocks.WHEKII_PONGA_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TaiaoBlocks.POTTED_WHEKII_PONGA_SAPLING, RenderLayer.getCutout());
    }

    public static void registerSpawnEggColors(Item @NotNull ... items) {
        registerItemColors(TaiaoClient::spawnEggColor, items);
    }

    public static void registerItemColors(ItemColorProvider colorProvider, Item @NotNull ... items) {
        for (Item item : items) {
            ColorProviderRegistry.ITEM.register(colorProvider, item);
        }
    }

    public static void registerFoliageColors(Block @NotNull ... blocks) {
        registerBlockColors(TaiaoClient::foliageBlockColor, TaiaoClient::foliageItemColor, blocks);
    }

    public static void registerBlockColors(BlockColorProvider colorProvider, Block @NotNull ... blocks) {
        for (Block block : blocks) {
            ColorProviderRegistry.BLOCK.register(colorProvider, block);
        }
    }

    public static void registerBlockColors(
            BlockColorProvider blockColorProvider,
            ItemColorProvider itemColorProvider,
            Block @NotNull ... blocks
    ) {
        for (Block block : blocks) {
            ColorProviderRegistry.BLOCK.register(blockColorProvider, block);
            ColorProviderRegistry.ITEM.register(itemColorProvider, block.asItem());
        }
    }

    public static int foliageBlockColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        return (world != null && pos != null) ? BiomeColors.getFoliageColor(world, pos)
                : FoliageColors.getDefaultColor();
    }

    public static int grassBlockColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        return (world != null && pos != null) ? BiomeColors.getGrassColor(world, pos) : GrassColors.getDefaultColor();
    }

    public static int foliageItemColor(ItemStack stack, int tintIndex) {
        return FoliageColors.getDefaultColor();
    }

    public static int grassItemColor(ItemStack stack, int tintIndex) {
        return GrassColors.getDefaultColor();
    }

    public static int spawnEggColor(@NotNull ItemStack stack, int tintIndex) {
        if (stack.getItem() instanceof SpawnEggItem item) {
            return item.getColor(tintIndex);
        } else {
            return 0;
        }
    }
}