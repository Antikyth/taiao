// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import antikyth.taiao.banner.Banner;
import antikyth.taiao.banner.TaiaoBanners;
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
import java.util.function.Supplier;

public class TaiaoItemGroups {
	public static final RegistryKey<ItemGroup> MAIN = registerItemGroup(
		Taiao.id("item_group"),
		Taiao.MOD_ICON
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registering item groups");

		// Add items to the item group.
		ItemGroupEvents.modifyEntriesEvent(MAIN).register(group -> {
			group.add(TaiaoItems.KETE);
			addEntityRelatingBlocks(group::add);

			addEntityBuckets(group::add);

			addFish(group::add);
			group.add(TaiaoItems.WEETAA);
			addFruit(group::add);

			addOtherBuildingBlocks(group::add);

			addBannerPatterns(group::add);
			addBanners(banner -> group.add(banner.getOrCreateStack()));

			addTallGroundPlants(group::add);
			addWaterPlants(group::add);

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

			addSpawnEggs(group::add);
		});

		// Spawn eggs
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(group -> addSpawnEggs(group::add));
		// Food
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(group -> {
			addFruit(item -> group.addBefore(Items.CARROT, item));
			addFish(item -> group.addBefore(Items.BREAD, item));
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

			addOtherBuildingBlocks(add);
		});
		// Natural blocks
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(group -> {
			addLogs(item -> group.addBefore(Items.OAK_LEAVES, item));
			addLeaves(item -> group.addBefore(Items.BROWN_MUSHROOM_BLOCK, item));
			addSaplings(item -> group.addBefore(Items.BROWN_MUSHROOM, item));

			addTallGroundPlants(item -> group.addBefore(Items.SUNFLOWER, item));
			addWaterPlants(item -> group.addBefore(Items.KELP, item));
		});
		// Tools & utilities
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(group -> {
			addBoats(item -> group.addBefore(Items.RAIL, item));
			addEntityBuckets(item -> group.addBefore(Items.LAVA_BUCKET, item));
			group.addBefore(Items.COMPASS, TaiaoItems.KETE);
		});
		// Functional blocks
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(group -> {
			addEntityRelatingBlocks(item -> group.addBefore(Items.SUSPICIOUS_SAND, item));
			addBanners(banner -> group.addBefore(Items.SKELETON_SKULL, banner.getOrCreateStack()));
		});
		// Ingredients
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(group -> {
			group.addBefore(Items.FLOWER_BANNER_PATTERN, TaiaoItems.WEETAA);
			addBannerPatterns(item -> group.addBefore(Items.ANGLER_POTTERY_SHERD, item));
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
		add.accept(TaiaoBlocks.CHISELED_STRIPPED_RIMU_LOG);
		add.accept(TaiaoBlocks.CHISELED_STRIPPED_RIMU_WOOD);

		add.accept(TaiaoBlocks.RIMU_PLANKS);
		add.accept(TaiaoBlocks.RIMU_STAIRS);
		add.accept(TaiaoBlocks.RIMU_SLAB);
		add.accept(TaiaoBlocks.RIMU_FENCE);
		add.accept(TaiaoBlocks.RIMU_FENCE_GATE);
		add.accept(TaiaoBlocks.RIMU_DOOR);
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

	public static void addOtherBuildingBlocks(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.HARAKEKE_MAT);
		add.accept(TaiaoBlocks.THATCH_ROOF);
		add.accept(TaiaoBlocks.THATCH_ROOF_TOP);
	}

	public static void addEntityRelatingBlocks(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.HIINAKI);
	}

	public static void addSpawnEggs(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoItems.KIWI_SPAWN_EGG);
		add.accept(TaiaoItems.PUUKEKO_SPAWN_EGG);
		add.accept(TaiaoItems.HAASTS_EAGLE_SPAWN_EGG);
		add.accept(TaiaoItems.MOA_SPAWN_EGG);
		add.accept(TaiaoItems.KAAKAAPOO_SPAWN_EGG);
		add.accept(TaiaoItems.AUSTRALASIAN_BITTERN_SPAWN_EGG);

		add.accept(TaiaoItems.EEL_SPAWN_EGG);
		add.accept(TaiaoItems.WEETAA_SPAWN_EGG);
	}

	public static void addEntityBuckets(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoItems.EEL_BUCKET);
	}

	public static void addFruit(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoItems.CONIFER_FRUIT);
	}

	public static void addFish(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoItems.EEL);
		add.accept(TaiaoItems.COOKED_EEL);
	}

	public static void addTallGroundPlants(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.GIANT_CANE_RUSH);
		add.accept(TaiaoBlocks.HARAKEKE);
	}

	public static void addWaterPlants(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoBlocks.RAUPOO);
	}

	public static void addBanners(@NotNull Consumer<Banner> add) {
		add.accept(TaiaoBanners.KIWI_TUPUNA_TUKUTUKU);

		add.accept(TaiaoBanners.KAOKAO_TUPUNA_TUKUTUKU);

		add.accept(TaiaoBanners.POUTAMA_TUPUNA_TUKUTUKU_LEFT);
		add.accept(TaiaoBanners.POUTAMA_TUPUNA_TUKUTUKU_RIGHT);

		add.accept(TaiaoBanners.PAATIKI_TUPUNA_TUKUTUKU);
		add.accept(TaiaoBanners.PAATIKI_TUPUNA_TUKUTUKU_TOP);
		add.accept(TaiaoBanners.PAATIKI_TUPUNA_TUKUTUKU_BOTTOM);
	}

	public static void addBannerPatterns(@NotNull Consumer<ItemConvertible> add) {
		add.accept(TaiaoItems.KIWI_BANNER_PATTERN);
	}

	/**
	 * Register a new item group with the given {@code id} and {@code icon}.
	 */
	public static RegistryKey<ItemGroup> registerItemGroup(Identifier id, Supplier<ItemStack> icon) {
		RegistryKey<ItemGroup> key = RegistryKey.of(RegistryKeys.ITEM_GROUP, id);

		ItemGroup group = FabricItemGroup.builder()
			.icon(icon)
			.displayName(Text.translatable(id.toTranslationKey("itemGroup")))
			.build();

		Registry.register(Registries.ITEM_GROUP, key, group);

		return key;
	}
}
