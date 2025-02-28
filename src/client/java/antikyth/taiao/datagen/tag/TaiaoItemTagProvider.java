// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.tag;

import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.item.TaiaoItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TaiaoItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public TaiaoItemTagProvider(
		FabricDataOutput output,
		CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup,
		@Nullable FabricTagProvider.BlockTagProvider blockTagProvider
	) {
		super(output, registryLookup, blockTagProvider);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
		// Conventional item tags
		getOrCreateTagBuilder(TaiaoItemTags.CONVENTIONAL_SEEDS)
			.add(Items.WHEAT_SEEDS)
			.add(Items.MELON_SEEDS)
			.add(Items.PUMPKIN_SEEDS)
			.add(Items.BEETROOT_SEEDS)
			.add(Items.TORCHFLOWER_SEEDS)
			.add(Items.PITCHER_POD);
		getOrCreateTagBuilder(TaiaoItemTags.CONVENTIONAL_BERRIES)
			.add(Items.SWEET_BERRIES)
			.add(Items.GLOW_BERRIES);
		getOrCreateTagBuilder(TaiaoItemTags.CONVENTIONAL_VINES)
			.add(Items.VINE)
			.add(Items.WEEPING_VINES)
			.add(Items.TWISTING_VINES);
		getOrCreateTagBuilder(ConventionalItemTags.FOODS)
			.add(TaiaoItems.CONIFER_FRUIT)
			.add(TaiaoItems.EEL)
			.add(TaiaoItems.COOKED_EEL);

		// Te Taiao o Aotearoa item tags
		getOrCreateTagBuilder(TaiaoItemTags.KIWI_FOOD)
			.addOptionalTag(TaiaoItemTags.CONVENTIONAL_SEEDS)
			.addOptionalTag(TaiaoItemTags.CONVENTIONAL_BERRIES)
			.add(TaiaoItems.CONIFER_FRUIT);
		getOrCreateTagBuilder(TaiaoItemTags.PUUKEKO_FOOD)
			.addOptionalTag(TaiaoItemTags.CONVENTIONAL_SEEDS);
		getOrCreateTagBuilder(TaiaoItemTags.MOA_FOOD)
			.addTag(ItemTags.LEAVES)
			.addTag(ItemTags.SAPLINGS)
			.add(TaiaoItems.CONIFER_FRUIT);
		getOrCreateTagBuilder(TaiaoItemTags.KAAKAAPOO_FOOD)
			.addOptionalTag(TaiaoItemTags.CONVENTIONAL_SEEDS)
			.addOptionalTag(TaiaoItemTags.CONVENTIONAL_VINES)
			.add(TaiaoItems.CONIFER_FRUIT);
		getOrCreateTagBuilder(TaiaoItemTags.AUSTRALASIAN_BITTERN_FOOD)
			.addOptionalTag(ItemTags.FISHES)
			.add(Items.FROGSPAWN);

		getOrCreateTagBuilder(TaiaoItemTags.CARVINGS)
			.add(TaiaoBlocks.CHISELED_STRIPPED_RIMU_LOG.asItem())
			.add(TaiaoBlocks.CHISELED_STRIPPED_RIMU_WOOD.asItem());
		getOrCreateTagBuilder(TaiaoItemTags.FERNS)
			.add(Items.FERN)
			.add(Items.LARGE_FERN)
			.add(TaiaoBlocks.MAMAKU_LEAVES.asItem())
			.add(TaiaoBlocks.WHEKII_PONGA_LEAVES.asItem());
		getOrCreateTagBuilder(TaiaoItemTags.HIINAKI_BAIT)
			.addOptionalTag(ConventionalItemTags.FOODS)
			.add(TaiaoItems.WEETAA)
			.add(Items.FROGSPAWN);

		// Vanilla tags
		getOrCreateTagBuilder(ItemTags.BOATS)
			.add(TaiaoItems.KAURI_BOAT)
			.add(TaiaoItems.KAHIKATEA_BOAT)
			.add(TaiaoItems.MAMAKU_RAFT);
		getOrCreateTagBuilder(ItemTags.CHEST_BOATS)
			.add(TaiaoItems.KAURI_CHEST_BOAT)
			.add(TaiaoItems.KAHIKATEA_CHEST_BOAT)
			.add(TaiaoItems.MAMAKU_CHEST_RAFT);

		// Te Taiao o Aotearoa block tags
		copy(TaiaoBlockTags.KAURI_LOGS, TaiaoItemTags.KAURI_LOGS);
		copy(TaiaoBlockTags.KAHIKATEA_LOGS, TaiaoItemTags.KAHIKATEA_LOGS);
		copy(TaiaoBlockTags.RIMU_LOGS, TaiaoItemTags.RIMU_LOGS);
		copy(TaiaoBlockTags.CABBAGE_TREE_LOGS, TaiaoItemTags.CABBAGE_TREE_LOGS);
		copy(TaiaoBlockTags.MAMAKU_LOGS, TaiaoItemTags.MAMAKU_LOGS);
		copy(TaiaoBlockTags.WHEKII_PONGA_LOGS, TaiaoItemTags.WHEKII_PONGA_LOGS);

		copy(TaiaoBlockTags.THIN_LOGS, TaiaoItemTags.THIN_LOGS);

		// Vanilla block tags
		copy(BlockTags.LEAVES, ItemTags.LEAVES);
		copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
		copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);

		copy(BlockTags.PLANKS, ItemTags.PLANKS);
		copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
		copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
		copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
		copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
	}
}
