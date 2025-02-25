// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.entity.render.TaiaoBlockEntityRenderers;
import antikyth.taiao.entity.render.TaiaoEntityModels;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.item.tooltip.TaiaoTooltipComponents;
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
import net.minecraft.state.property.Property;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.NotNull;

public class TaiaoClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		TaiaoBuiltinResourcePacks.initialize();
		TaiaoEntityModels.initialize();
		TaiaoBlockEntityRenderers.initialize();
		TaiaoTooltipComponents.initialize();

		registerColors();
		registerRenderLayers();
	}

	public static void registerColors() {
		Taiao.LOGGER.debug("Registering block and item dynamic color providers");

		// Leaves
		registerFoliageColors(
			TaiaoBlocks.KAURI_LEAVES,
			TaiaoBlocks.KAHIKATEA_LEAVES,
			TaiaoBlocks.RIMU_LEAVES,
			TaiaoBlocks.CABBAGE_TREE_LEAVES,
			TaiaoBlocks.MAMAKU_LEAVES,
			TaiaoBlocks.WHEKII_PONGA_LEAVES
		);

		// Spawn eggs
		registerSpawnEggColors(
			TaiaoItems.KIWI_SPAWN_EGG,
			TaiaoItems.PUUKEKO_SPAWN_EGG,
			TaiaoItems.MOA_SPAWN_EGG,
			TaiaoItems.KAAKAAPOO_SPAWN_EGG,
			TaiaoItems.AUSTRALASIAN_BITTERN_SPAWN_EGG,
			TaiaoItems.EEL_SPAWN_EGG
		);
	}

	public static void registerRenderLayers() {
		Taiao.LOGGER.debug("Registering block render layers");

		// Saplings
		registerRenderLayer(
			RenderLayer.getCutout(),
			TaiaoBlocks.KAURI_SAPLING,
			TaiaoBlocks.POTTED_KAURI_SAPLING
		);
		registerRenderLayer(
			RenderLayer.getCutout(),
			TaiaoBlocks.KAHIKATEA_SAPLING,
			TaiaoBlocks.POTTED_KAHIKATEA_SAPLING
		);
		registerRenderLayer(
			RenderLayer.getCutout(),
			TaiaoBlocks.RIMU_SAPLING,
			TaiaoBlocks.POTTED_RIMU_SAPLING
		);
		registerRenderLayer(
			RenderLayer.getCutout(),
			TaiaoBlocks.CABBAGE_TREE_SAPLING,
			TaiaoBlocks.POTTED_CABBAGE_TREE_SAPLING
		);
		registerRenderLayer(
			RenderLayer.getCutout(),
			TaiaoBlocks.MAMAKU_SAPLING,
			TaiaoBlocks.POTTED_MAMAKU_SAPLING
		);
		registerRenderLayer(
			RenderLayer.getCutout(),
			TaiaoBlocks.WHEKII_PONGA_SAPLING,
			TaiaoBlocks.POTTED_WHEKII_PONGA_SAPLING
		);

		// Plants
		registerRenderLayer(
			RenderLayer.getCutout(),
			TaiaoBlocks.RAUPOO,
			TaiaoBlocks.GIANT_CANE_RUSH,
			TaiaoBlocks.HARAKEKE
		);
		// Other blocks
		registerRenderLayer(
			RenderLayer.getCutout(),
			TaiaoBlocks.RIMU_DOOR
		);

		registerRenderLayer(
			RenderLayer.getCutout(),
			TaiaoBlocks.HIINAKI
		);
	}

	/**
	 * Puts the given {@code blocks} in the {@link BlockRenderLayerMap} with the given {@code layer}.
	 */
	public static void registerRenderLayer(RenderLayer layer, Block @NotNull ... blocks) {
		for (Block block : blocks) {
			BlockRenderLayerMap.INSTANCE.putBlock(block, layer);
		}
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

	/**
	 * Returns {@link MutableText} of a description of the given {@code property} and {@code value}.
	 */
	public static MutableText propertyText(@NotNull Property<?> property, Comparable<?> value) {
		MutableText propertyText = Text.literal(property.getName() + ": ");
		MutableText valueText = Text.literal(Util.getValueAsString(property, value));

		if (Boolean.TRUE.equals(value)) {
			valueText.formatted(Formatting.GREEN);
		} else if (Boolean.FALSE.equals(value)) {
			valueText.formatted(Formatting.RED);
		}

		return propertyText.append(valueText);
	}
}