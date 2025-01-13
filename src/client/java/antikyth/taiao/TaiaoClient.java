// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

import antikyth.taiao.block.TaiaoBlocks;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.NotNull;

public class TaiaoClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerBlockColors(this::foliageBlockColor, this::foliageItemColor, TaiaoBlocks.CABBAGE_TREE_LEAVES);

        BlockRenderLayerMap.INSTANCE.putBlock(TaiaoBlocks.CABBAGE_TREE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TaiaoBlocks.POTTED_CABBAGE_TREE_SAPLING, RenderLayer.getCutout());
    }

    public void registerBlockColors(BlockColorProvider colorProvider, Block @NotNull ... blocks) {
        for (Block block : blocks) {
            ColorProviderRegistry.BLOCK.register(colorProvider, block);
        }
    }

    public void registerBlockColors(
            BlockColorProvider blockColorProvider,
            ItemColorProvider itemColorProvider,
            Block @NotNull ... blocks
    ) {
        for (Block block : blocks) {
            ColorProviderRegistry.BLOCK.register(blockColorProvider, block);
            ColorProviderRegistry.ITEM.register(itemColorProvider, block.asItem());
        }
    }

    public int foliageBlockColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        return (world != null && pos != null) ? BiomeColors.getFoliageColor(world, pos)
                : FoliageColors.getDefaultColor();
    }

    public int grassBlockColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        return (world != null && pos != null) ? BiomeColors.getGrassColor(world, pos) : GrassColors.getDefaultColor();
    }

    public int foliageItemColor(ItemStack stack, int tintIndex) {
        return FoliageColors.getDefaultColor();
    }

    public int grassItemColor(ItemStack stack, int tintIndex) {
        return GrassColors.getDefaultColor();
    }
}