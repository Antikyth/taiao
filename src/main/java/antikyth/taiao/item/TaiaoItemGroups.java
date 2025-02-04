// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TaiaoItemGroups {
	public static final RegistryKey<ItemGroup> TE_TAIAO_O_AOTEAROA = registerItemGroup(
		Taiao.id("item_group"),
		TaiaoBlocks.CABBAGE_TREE_SAPLING.asItem()
	);

	@SuppressWarnings("CodeBlock2Expr")
	public static void initialize() {
		Taiao.LOGGER.debug("Registering item groups");

		// Add items to the item group.
		ItemGroupEvents.modifyEntriesEvent(TE_TAIAO_O_AOTEAROA).register(group -> {
			group.add(TaiaoBlocks.KAURI_SAPLING);
			group.add(TaiaoBlocks.KAURI_LEAVES);
			addKauriBuildingBlocks(group::add);
			group.add(TaiaoItems.KAURI_BOAT);
			group.add(TaiaoItems.KAURI_CHEST_BOAT);

			group.add(TaiaoBlocks.KAHIKATEA_SAPLING);
			group.add(TaiaoBlocks.KAHIKATEA_LEAVES);
			addKahikateaBuildingBlocks(group::add);
			group.add(TaiaoItems.KAHIKATEA_BOAT);
			group.add(TaiaoItems.KAHIKATEA_CHEST_BOAT);

			group.add(TaiaoBlocks.RIMU_SAPLING);
			group.add(TaiaoBlocks.RIMU_LEAVES);
			addRimuBuildingBlocks(group::add);
			group.add(TaiaoItems.RIMU_BOAT);
			group.add(TaiaoItems.RIMU_CHEST_BOAT);

			group.add(TaiaoBlocks.CABBAGE_TREE_SAPLING);
			group.add(TaiaoBlocks.CABBAGE_TREE_LEAVES);
			addCabbageTreeBuildingBlocks(group::add);

			group.add(TaiaoBlocks.MAMAKU_SAPLING);
			group.add(TaiaoBlocks.MAMAKU_LEAVES);
			addMamakuBuildingBlocks(group::add);
			group.add(TaiaoItems.MAMAKU_RAFT);
			group.add(TaiaoItems.MAMAKU_CHEST_RAFT);

			group.add(TaiaoBlocks.WHEKII_PONGA_SAPLING);
			group.add(TaiaoBlocks.WHEKII_PONGA_LEAVES);
			addWhekiiPongaBuildingBlocks(group::add);

			addWaterPlants(group::add);
			addFruit(group::add);

			addBanners(group::add);
			addSpawnEggs(group::add);
		});

		// Spawn eggs
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(group -> addSpawnEggs(group::add));
		// Food
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(group -> {
			addFruit(item -> group.addBefore(Items.CARROT, item));
		});
		// Building blocks
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(group -> {
			Consumer<ItemConvertible> add = item -> group.addBefore(Items.STONE, item);

			addKauriBuildingBlocks(add);
			addKahikateaBuildingBlocks(add);
			addRimuBuildingBlocks(add);
			addCabbageTreeBuildingBlocks(add);
			addMamakuBuildingBlocks(add);
			addWhekiiPongaBuildingBlocks(add);
		});
		// Natural blocks
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(group -> {
			addLogs(item -> group.addBefore(Items.OAK_LEAVES, item));
			addLeaves(item -> group.addBefore(Items.BROWN_MUSHROOM_BLOCK, item));
			addSaplings(item -> group.addBefore(Items.BROWN_MUSHROOM, item));
			addWaterPlants(item -> group.addBefore(Items.KELP, item));
		});
		// Tools & utilities
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(group -> {
			addBoats(item -> group.addBefore(Items.RAIL, item));
		});
		// Functional blocks
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(group -> {
			addBanners(stack -> group.addBefore(Items.SKELETON_SKULL, stack));
		});
	}

	public static void addLogs(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.KAURI_LOG);
		add.accept(TaiaoBlocks.KAHIKATEA_LOG);
		add.accept(TaiaoBlocks.RIMU_LOG);
		add.accept(TaiaoBlocks.CABBAGE_TREE_LOG);
		add.accept(TaiaoBlocks.MAMAKU_LOG);
		add.accept(TaiaoBlocks.WHEKII_PONGA_LOG);
	}

	public static void addLeaves(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.KAURI_LEAVES);
		add.accept(TaiaoBlocks.KAHIKATEA_LEAVES);
		add.accept(TaiaoBlocks.RIMU_LEAVES);
		add.accept(TaiaoBlocks.CABBAGE_TREE_LEAVES);
		add.accept(TaiaoBlocks.MAMAKU_LEAVES);
		add.accept(TaiaoBlocks.WHEKII_PONGA_LEAVES);
	}

	public static void addSaplings(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.KAURI_SAPLING);
		add.accept(TaiaoBlocks.KAHIKATEA_SAPLING);
		add.accept(TaiaoBlocks.RIMU_SAPLING);
		add.accept(TaiaoBlocks.CABBAGE_TREE_SAPLING);
		add.accept(TaiaoBlocks.MAMAKU_SAPLING);
		add.accept(TaiaoBlocks.WHEKII_PONGA_SAPLING);
	}

	public static void addBoats(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoItems.KAURI_BOAT);
		add.accept(TaiaoItems.KAURI_CHEST_BOAT);
		add.accept(TaiaoItems.KAHIKATEA_BOAT);
		add.accept(TaiaoItems.KAHIKATEA_CHEST_BOAT);
		add.accept(TaiaoItems.RIMU_BOAT);
		add.accept(TaiaoItems.RIMU_CHEST_BOAT);
		add.accept(TaiaoItems.MAMAKU_RAFT);
		add.accept(TaiaoItems.MAMAKU_CHEST_RAFT);
	}

	public static void addKauriBuildingBlocks(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.KAURI_LOG);
		add.accept(TaiaoBlocks.KAURI_WOOD);
		add.accept(TaiaoBlocks.STRIPPED_KAURI_LOG);
		add.accept(TaiaoBlocks.STRIPPED_KAURI_WOOD);

		add.accept(TaiaoBlocks.KAURI_PLANKS);
		add.accept(TaiaoBlocks.KAURI_STAIRS);
		add.accept(TaiaoBlocks.KAURI_SLAB);
		add.accept(TaiaoBlocks.KAURI_FENCE);
		add.accept(TaiaoBlocks.KAURI_FENCE_GATE);
		add.accept(TaiaoBlocks.KAURI_PRESSURE_PLATE);
		add.accept(TaiaoBlocks.KAURI_BUTTON);
	}

	public static void addKahikateaBuildingBlocks(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.KAHIKATEA_LOG);
		add.accept(TaiaoBlocks.KAHIKATEA_WOOD);
		add.accept(TaiaoBlocks.STRIPPED_KAHIKATEA_LOG);
		add.accept(TaiaoBlocks.STRIPPED_KAHIKATEA_WOOD);

		add.accept(TaiaoBlocks.KAHIKATEA_PLANKS);
		add.accept(TaiaoBlocks.KAHIKATEA_STAIRS);
		add.accept(TaiaoBlocks.KAHIKATEA_SLAB);
		add.accept(TaiaoBlocks.KAHIKATEA_FENCE);
		add.accept(TaiaoBlocks.KAHIKATEA_FENCE_GATE);
		add.accept(TaiaoBlocks.KAHIKATEA_PRESSURE_PLATE);
		add.accept(TaiaoBlocks.KAHIKATEA_BUTTON);
	}

	public static void addRimuBuildingBlocks(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.RIMU_LOG);
		add.accept(TaiaoBlocks.RIMU_WOOD);
		add.accept(TaiaoBlocks.STRIPPED_RIMU_LOG);
		add.accept(TaiaoBlocks.STRIPPED_RIMU_WOOD);

		add.accept(TaiaoBlocks.RIMU_PLANKS);
		add.accept(TaiaoBlocks.RIMU_STAIRS);
		add.accept(TaiaoBlocks.RIMU_SLAB);
		add.accept(TaiaoBlocks.RIMU_FENCE);
		add.accept(TaiaoBlocks.RIMU_FENCE_GATE);
		add.accept(TaiaoBlocks.RIMU_PRESSURE_PLATE);
		add.accept(TaiaoBlocks.RIMU_BUTTON);
	}

	public static void addCabbageTreeBuildingBlocks(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.CABBAGE_TREE_LOG);
		add.accept(TaiaoBlocks.CABBAGE_TREE_WOOD);
		add.accept(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG);
		add.accept(TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD);
	}

	public static void addMamakuBuildingBlocks(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.MAMAKU_LOG);
		add.accept(TaiaoBlocks.MAMAKU_WOOD);
		add.accept(TaiaoBlocks.STRIPPED_MAMAKU_LOG);
		add.accept(TaiaoBlocks.STRIPPED_MAMAKU_WOOD);

		add.accept(TaiaoBlocks.MAMAKU_PLANKS);
		add.accept(TaiaoBlocks.MAMAKU_STAIRS);
		add.accept(TaiaoBlocks.MAMAKU_SLAB);
		add.accept(TaiaoBlocks.MAMAKU_FENCE);
		add.accept(TaiaoBlocks.MAMAKU_FENCE_GATE);
		add.accept(TaiaoBlocks.MAMAKU_PRESSURE_PLATE);
		add.accept(TaiaoBlocks.MAMAKU_BUTTON);
	}

	public static void addWhekiiPongaBuildingBlocks(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.WHEKII_PONGA_LOG);
		add.accept(TaiaoBlocks.WHEKII_PONGA_WOOD);
		add.accept(TaiaoBlocks.STRIPPED_WHEKII_PONGA_LOG);
		add.accept(TaiaoBlocks.STRIPPED_WHEKII_PONGA_WOOD);
	}

	public static void addSpawnEggs(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoItems.KIWI_SPAWN_EGG);
		add.accept(TaiaoItems.PUUKEKO_SPAWN_EGG);
		add.accept(TaiaoItems.MOA_SPAWN_EGG);
		add.accept(TaiaoItems.KAAKAAPOO_SPAWN_EGG);
	}

	public static void addFruit(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoItems.CONIFER_FRUIT);
	}

	public static void addWaterPlants(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.RAUPOO);
	}

	public static void addBanners(@NotNull Consumer<ItemStack> add) {
		add.accept(TaiaoBannerPatterns.KAOKAO_TUKUTUKU);
		add.accept(TaiaoBannerPatterns.POUTAMA_TUKUTUKU);
		add.accept(TaiaoBannerPatterns.PAATIKI_TUKUTUKU);

		add.accept(TaiaoBannerPatterns.KAOKAO_TUKUTUKU_TOP);
		add.accept(TaiaoBannerPatterns.KAOKAO_TUKUTUKU_BOTTOM);

		add.accept(TaiaoBannerPatterns.POUTAMA_TUKUTUKU_TOP);
		add.accept(TaiaoBannerPatterns.POUTAMA_TUKUTUKU_BOTTOM);

		add.accept(TaiaoBannerPatterns.PAATIKI_TUKUTUKU_TOP);
		add.accept(TaiaoBannerPatterns.PAATIKI_TUKUTUKU_BOTTOM);
	}

	/**
	 * Register a new item group with the given {@code id} and {@code icon}.
	 */
	public static RegistryKey<ItemGroup> registerItemGroup(Identifier id, Item icon) {
		RegistryKey<ItemGroup> key = RegistryKey.of(RegistryKeys.ITEM_GROUP, id);

		ItemGroup group = FabricItemGroup.builder()
			.icon(() -> new ItemStack(icon))
			.displayName(Text.translatable(id.toTranslationKey("itemGroup")))
			.build();

		Registry.register(Registries.ITEM_GROUP, key, group);

		return key;
	}
}
