// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.TaiaoEntities;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class TaiaoItems {
	public static final Item CONIFER_FRUIT = register(
		Taiao.id("conifer_fruit"),
		new Item(new FabricItemSettings().food(TaiaoFoodComponents.CONIFER_FRUIT))
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

	public static void initialize() {
		Taiao.LOGGER.debug("Registering items");
	}

	/**
	 * Register the given {@code item} with the given {@code id}.
	 */
	public static Item register(Identifier id, Item item) {
		return Registry.register(Registries.ITEM, id, item);
	}

	public static final ItemStack POUTAMA_LEFT_TUKUTUKU = getBannerItemStack(
		Taiao.id("poutama_left_tukutuku"),
		Items.BLACK_BANNER,
		new BannerPattern.Patterns()
			.add(TaiaoBannerPatterns.POUTAMA_LEFT_PRIMARY, DyeColor.WHITE)
			.add(TaiaoBannerPatterns.POUTAMA_LEFT_SECONDARY, DyeColor.YELLOW),
		Formatting.GOLD
	);
	public static final ItemStack POUTAMA_RIGHT_TUKUTUKU = getBannerItemStack(
		Taiao.id("poutama_right_tukutuku"),
		Items.BLACK_BANNER,
		new BannerPattern.Patterns()
			.add(TaiaoBannerPatterns.POUTAMA_RIGHT_PRIMARY, DyeColor.WHITE)
			.add(TaiaoBannerPatterns.POUTAMA_RIGHT_SECONDARY, DyeColor.YELLOW),
		Formatting.GOLD
	);
	public static final ItemStack PAATIKI_TUKUTUKU = getBannerItemStack(
		Taiao.id("paatiki_tukutuku"),
		Items.BLACK_BANNER,
		new BannerPattern.Patterns()
			.add(TaiaoBannerPatterns.PAATIKI_PRIMARY, DyeColor.WHITE)
			.add(TaiaoBannerPatterns.PAATIKI_SECONDARY, DyeColor.YELLOW),
		Formatting.GOLD
	);
	public static final ItemStack KAOKAO_TUKUTUKU = getBannerItemStack(
		Taiao.id("kaokao_up_tukutuku"),
		Items.BLACK_BANNER,
		new BannerPattern.Patterns()
			.add(BannerPatterns.STRIPE_TOP, DyeColor.RED)
			.add(BannerPatterns.STRIPE_BOTTOM, DyeColor.YELLOW)
			.add(TaiaoBannerPatterns.KAOKAO_UP_PRIMARY, DyeColor.WHITE)
			.add(TaiaoBannerPatterns.KAOKAO_UP_SECONDARY, DyeColor.YELLOW),
		Formatting.GOLD
	);

	public static @NotNull ItemStack getBannerItemStack(
		@NotNull Identifier id,
		Item base,
		BannerPattern.@NotNull Patterns patterns,
		Formatting... formattings
	) {
		ItemStack stack = new ItemStack(base);
		NbtCompound nbt = new NbtCompound();

		nbt.put("Patterns", patterns.toNbt());
		BlockItem.setBlockEntityNbt(stack, BlockEntityType.BANNER, nbt);
		stack.addHideFlag(ItemStack.TooltipSection.ADDITIONAL);
		stack.setCustomName(Text.translatable(id.toTranslationKey("block")).formatted(formattings));

		return stack;
	}

	public static class TaiaoFoodComponents {
		public static final FoodComponent CONIFER_FRUIT = new FoodComponent.Builder()
			.hunger(1)
			.saturationModifier(0.1f)
			.build();
	}

}
