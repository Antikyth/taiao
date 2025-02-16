// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.entity.TaiaoEntities;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class TaiaoItems {
	public static final Item CONIFER_FRUIT = register(
		Taiao.id("conifer_fruit"),
		new Item(new FabricItemSettings().food(TaiaoFoodComponents.CONIFER_FRUIT))
	);
	public static final Item EEL = register(
		Taiao.id("eel"),
		new Item(new FabricItemSettings().food(TaiaoFoodComponents.EEL))
	);
	public static final Item COOKED_EEL = register(
		Taiao.id("cooked_eel"),
		new Item(new FabricItemSettings().food(TaiaoFoodComponents.COOKED_EEL))
	);

	public static final Item EEL_BUCKET = register(
		Taiao.id("eel_bucket"),
		new EntityBucketItem(
			TaiaoEntities.EEL,
			Fluids.WATER,
			SoundEvents.ITEM_BUCKET_EMPTY_FISH,
			new FabricItemSettings().maxCount(1)
		)
	);

	public static final Item KAURI_BOAT = TerraformBoatItemHelper.registerBoatItem(
		Taiao.id("kauri_boat"),
		TaiaoBoats.KAURI,
		false
	);
	public static final Item KAURI_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(
		Taiao.id("kauri_chest_boat"),
		TaiaoBoats.KAURI,
		true
	);
	public static final Item KAHIKATEA_BOAT = TerraformBoatItemHelper.registerBoatItem(
		Taiao.id("kahikatea_boat"),
		TaiaoBoats.KAHIKATEA,
		false
	);
	public static final Item KAHIKATEA_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(
		Taiao.id("kahikatea_chest_boat"),
		TaiaoBoats.KAHIKATEA,
		true
	);
	public static final Item RIMU_BOAT = TerraformBoatItemHelper.registerBoatItem(
		Taiao.id("rimu_boat"),
		TaiaoBoats.RIMU,
		false
	);
	public static final Item RIMU_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(
		Taiao.id("rimu_chest_boat"),
		TaiaoBoats.RIMU,
		true
	);
	public static final Item MAMAKU_RAFT = TerraformBoatItemHelper.registerBoatItem(
		Taiao.id("mamaku_raft"),
		TaiaoBoats.MAMAKU,
		false
	);
	public static final Item MAMAKU_CHEST_RAFT = TerraformBoatItemHelper.registerBoatItem(
		Taiao.id("mamaku_chest_raft"),
		TaiaoBoats.MAMAKU,
		true
	);

	public static final Item KIWI_SPAWN_EGG = register(
		Taiao.id("kiwi_spawn_egg"),
		new SpawnEggItem(TaiaoEntities.KIWI, 0x482d19, 0xf5bb98, new FabricItemSettings())
	);
	public static final Item PUUKEKO_SPAWN_EGG = register(
		Taiao.id("puukeko_spawn_egg"),
		new SpawnEggItem(TaiaoEntities.PUUKEKO, 0x073673, 0xaf2e2e, new FabricItemSettings())
	);
	public static final Item MOA_SPAWN_EGG = register(
		Taiao.id("moa_spawn_egg"),
		new SpawnEggItem(TaiaoEntities.MOA, 0x2c180c, 0x55361c, new FabricItemSettings())
	);
	public static final Item KAAKAAPOO_SPAWN_EGG = register(
		Taiao.id("kaakaapoo_spawn_egg"),
		new SpawnEggItem(TaiaoEntities.KAAKAAPOO, 0x7a9539, 0xd4ae68, new FabricItemSettings())
	);
	public static final Item AUSTRALASIAN_BITTERN_SPAWN_EGG = register(
		Taiao.id("australasian_bittern_spawn_egg"),
		new SpawnEggItem(TaiaoEntities.AUSTRALASIAN_BITTERN, 0x3b3016, 0xcbb166, new FabricItemSettings())
	);
	public static final Item EEL_SPAWN_EGG = register(
		Taiao.id("eel_spawn_egg"),
		new SpawnEggItem(TaiaoEntities.EEL, 0x251e2c, 0x9f99b7, new FabricItemSettings())
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registering items and composting recipes");

		// Saplings
		registerComposting(
			0.3f,
			TaiaoBlocks.KAURI_SAPLING,
			TaiaoBlocks.KAHIKATEA_SAPLING,
			TaiaoBlocks.RIMU_SAPLING,
			TaiaoBlocks.CABBAGE_TREE_SAPLING,
			TaiaoBlocks.MAMAKU_SAPLING,
			TaiaoBlocks.WHEKII_PONGA_SAPLING
		);
		// Leaves
		registerComposting(
			0.3f,
			TaiaoBlocks.KAURI_LEAVES,
			TaiaoBlocks.KAHIKATEA_LEAVES,
			TaiaoBlocks.RIMU_LEAVES,
			TaiaoBlocks.CABBAGE_TREE_LEAVES,
			TaiaoBlocks.MAMAKU_LEAVES,
			TaiaoBlocks.WHEKII_PONGA_LEAVES
		);
		// Berries
		registerComposting(0.3f, CONIFER_FRUIT);
		// Plant-material-rich tall plants
		registerComposting(
			0.65f,
			TaiaoBlocks.GIANT_CANE_RUSH,
			TaiaoBlocks.RAUPOO
		);
		// Extra plant-material-rich tall plants
		registerComposting(
			0.85f,
			TaiaoBlocks.HARAKEKE
		);
	}

	/**
	 * Register the given {@code item} with the given {@code id}.
	 */
	public static Item register(Identifier id, Item item) {
		return Registry.register(Registries.ITEM, id, item);
	}

	public static void registerComposting(float chance, ItemConvertible @NotNull ... items) {
		for (ItemConvertible convertible : items) {
			CompostingChanceRegistry.INSTANCE.add(convertible.asItem(), chance);
		}
	}

	public static class TaiaoFoodComponents {
		public static final FoodComponent CONIFER_FRUIT = new FoodComponent.Builder()
			.hunger(1)
			.saturationModifier(0.1f)
			.build();

		public static final FoodComponent EEL = new FoodComponent.Builder()
			.hunger(3)
			.saturationModifier(0.3f)
			.build();
		public static final FoodComponent COOKED_EEL = new FoodComponent.Builder()
			.hunger(7)
			.saturationModifier(0.8f)
			.build();
	}

}
