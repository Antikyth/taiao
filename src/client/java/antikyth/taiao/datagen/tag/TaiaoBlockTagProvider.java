// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.tag;

import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.block.TaiaoBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TaiaoBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public TaiaoBlockTagProvider(
		FabricDataOutput output,
		CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
	) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
		// Te Taiao o Aotearoa tags
		getOrCreateTagBuilder(TaiaoBlockTags.KAURI_LOGS)
			.add(TaiaoBlocks.KAURI_LOG)
			.add(TaiaoBlocks.STRIPPED_KAURI_LOG)
			.add(TaiaoBlocks.KAURI_WOOD)
			.add(TaiaoBlocks.STRIPPED_KAURI_WOOD);
		getOrCreateTagBuilder(TaiaoBlockTags.KAHIKATEA_LOGS)
			.add(TaiaoBlocks.KAHIKATEA_LOG)
			.add(TaiaoBlocks.STRIPPED_KAHIKATEA_LOG)
			.add(TaiaoBlocks.KAHIKATEA_WOOD)
			.add(TaiaoBlocks.STRIPPED_KAHIKATEA_WOOD);
		getOrCreateTagBuilder(TaiaoBlockTags.RIMU_LOGS)
			.add(TaiaoBlocks.RIMU_LOG)
			.add(TaiaoBlocks.STRIPPED_RIMU_LOG)
			.add(TaiaoBlocks.RIMU_WOOD)
			.add(TaiaoBlocks.STRIPPED_RIMU_WOOD)
			.add(TaiaoBlocks.CHISELED_STRIPPED_RIMU_LOG)
			.add(TaiaoBlocks.CHISELED_STRIPPED_RIMU_WOOD);
		getOrCreateTagBuilder(TaiaoBlockTags.CABBAGE_TREE_LOGS)
			.add(TaiaoBlocks.CABBAGE_TREE_LOG)
			.add(TaiaoBlocks.CABBAGE_TREE_WOOD)
			.add(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG)
			.add(TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD);
		getOrCreateTagBuilder(TaiaoBlockTags.MAMAKU_LOGS)
			.add(TaiaoBlocks.MAMAKU_LOG)
			.add(TaiaoBlocks.MAMAKU_WOOD)
			.add(TaiaoBlocks.STRIPPED_MAMAKU_LOG)
			.add(TaiaoBlocks.STRIPPED_MAMAKU_WOOD);
		getOrCreateTagBuilder(TaiaoBlockTags.WHEKII_PONGA_LOGS)
			.add(TaiaoBlocks.WHEKII_PONGA_LOG)
			.add(TaiaoBlocks.WHEKII_PONGA_WOOD)
			.add(TaiaoBlocks.STRIPPED_WHEKII_PONGA_LOG)
			.add(TaiaoBlocks.STRIPPED_WHEKII_PONGA_WOOD);

		getOrCreateTagBuilder(TaiaoBlockTags.THIN_LOGS)
			.addTag(TaiaoBlockTags.CABBAGE_TREE_LOGS)
			.addTag(TaiaoBlockTags.MAMAKU_LOGS);
		getOrCreateTagBuilder(TaiaoBlockTags.DIRECTIONAL_LEAVES)
			.add(TaiaoBlocks.MAMAKU_LEAVES)
			.add(TaiaoBlocks.WHEKII_PONGA_LEAVES);

		getOrCreateTagBuilder(TaiaoBlockTags.THIN_LOG_CONNECTION_OVERRIDE)
			.addTag(TaiaoBlockTags.THIN_LOGS)
			.addTag(BlockTags.LEAVES);
		getOrCreateTagBuilder(TaiaoBlockTags.REEDS_PLANTABLE_ON)
			.addOptionalTag(BlockTags.DIRT);
		getOrCreateTagBuilder(TaiaoBlockTags.HYDRATES_REEDS)
			.add(Blocks.FROSTED_ICE);

		// Vanilla tags
		getOrCreateTagBuilder(BlockTags.SAPLINGS)
			.add(TaiaoBlocks.KAURI_SAPLING)
			.add(TaiaoBlocks.KAHIKATEA_SAPLING)
			.add(TaiaoBlocks.RIMU_SAPLING)
			.add(TaiaoBlocks.CABBAGE_TREE_SAPLING)
			.add(TaiaoBlocks.MAMAKU_SAPLING)
			.add(TaiaoBlocks.WHEKII_PONGA_SAPLING);
		getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
			.add(TaiaoBlocks.POTTED_KAURI_SAPLING)
			.add(TaiaoBlocks.POTTED_KAHIKATEA_SAPLING)
			.add(TaiaoBlocks.POTTED_RIMU_SAPLING)
			.add(TaiaoBlocks.POTTED_CABBAGE_TREE_SAPLING)
			.add(TaiaoBlocks.POTTED_MAMAKU_SAPLING)
			.add(TaiaoBlocks.POTTED_WHEKII_PONGA_SAPLING);

		getOrCreateTagBuilder(BlockTags.LEAVES)
			.add(TaiaoBlocks.KAURI_LEAVES)
			.add(TaiaoBlocks.KAHIKATEA_LEAVES)
			.add(TaiaoBlocks.RIMU_LEAVES)
			.add(TaiaoBlocks.CABBAGE_TREE_LEAVES)
			.add(TaiaoBlocks.MAMAKU_LEAVES)
			.add(TaiaoBlocks.WHEKII_PONGA_LEAVES);

		getOrCreateTagBuilder(BlockTags.WALL_POST_OVERRIDE)
			.addTag(TaiaoBlockTags.THIN_LOGS);

		getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
			.addTag(TaiaoBlockTags.KAURI_LOGS)
			.addTag(TaiaoBlockTags.KAHIKATEA_LOGS)
			.addTag(TaiaoBlockTags.RIMU_LOGS)
			.addTag(TaiaoBlockTags.CABBAGE_TREE_LOGS)
			.addTag(TaiaoBlockTags.MAMAKU_LOGS)
			.addTag(TaiaoBlockTags.WHEKII_PONGA_LOGS);
		// Logs that generate naturally in the world
		getOrCreateTagBuilder(BlockTags.OVERWORLD_NATURAL_LOGS)
			.add(TaiaoBlocks.KAURI_LOG)
			.add(TaiaoBlocks.KAHIKATEA_LOG)
			.add(TaiaoBlocks.RIMU_LOG)
			.add(TaiaoBlocks.CABBAGE_TREE_LOG)
			.add(TaiaoBlocks.MAMAKU_LOG)
			.add(TaiaoBlocks.WHEKII_PONGA_LOG);

		addWoodFamilyTags(TaiaoBlocks.WoodFamily.KAURI.getBlockFamily());
		addWoodFamilyTags(TaiaoBlocks.WoodFamily.KAHIKATEA.getBlockFamily());
		addWoodFamilyTags(TaiaoBlocks.WoodFamily.RIMU.getBlockFamily());
		addWoodFamilyTags(TaiaoBlocks.WoodFamily.MAMAKU.getBlockFamily());
	}

	/**
	 * Adds the given wooden {@code family}'s variants to the appropriate tags.
	 */
	protected void addWoodFamilyTags(@NotNull BlockFamily family) {
		getOrCreateTagBuilder(BlockTags.PLANKS).add(family.getBaseBlock());

		addVariantTag(family, BlockFamily.Variant.BUTTON, BlockTags.WOODEN_BUTTONS);
		addVariantTag(family, BlockFamily.Variant.PRESSURE_PLATE, BlockTags.WOODEN_PRESSURE_PLATES);

		addVariantTag(family, BlockFamily.Variant.DOOR, BlockTags.WOODEN_DOORS);
		addVariantTag(family, BlockFamily.Variant.TRAPDOOR, BlockTags.WOODEN_TRAPDOORS);

		addVariantTag(family, BlockFamily.Variant.STAIRS, BlockTags.WOODEN_STAIRS);
		addVariantTag(family, BlockFamily.Variant.SLAB, BlockTags.WOODEN_SLABS);

		addVariantTag(family, BlockFamily.Variant.FENCE, BlockTags.WOODEN_FENCES);
		addVariantTag(family, BlockFamily.Variant.CUSTOM_FENCE, BlockTags.WOODEN_FENCES);
		addVariantTag(family, BlockFamily.Variant.FENCE_GATE, BlockTags.FENCE_GATES);
		addVariantTag(family, BlockFamily.Variant.CUSTOM_FENCE_GATE, BlockTags.FENCE_GATES);
	}

	protected void addVariantTag(@NotNull BlockFamily family, BlockFamily.Variant variant, TagKey<Block> tag) {
		Block block = family.getVariant(variant);

		if (block != null) getOrCreateTagBuilder(tag).add(block);
	}
}
