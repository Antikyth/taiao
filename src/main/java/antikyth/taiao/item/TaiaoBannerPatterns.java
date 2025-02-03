// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class TaiaoBannerPatterns {
	public static final RegistryKey<BannerPattern> POUTAMA_LEFT_PRIMARY = register(
		Taiao.id("poutama_left_primary"),
		Taiao.id("lpop")
	);
	public static final RegistryKey<BannerPattern> POUTAMA_LEFT_SECONDARY = register(
		Taiao.id("poutama_left_secondary"),
		Taiao.id("lpos")
	);
	public static final RegistryKey<BannerPattern> POUTAMA_RIGHT_PRIMARY = register(
		Taiao.id("poutama_right_primary"),
		Taiao.id("rpop")
	);
	public static final RegistryKey<BannerPattern> POUTAMA_RIGHT_SECONDARY = register(
		Taiao.id("poutama_right_secondary"),
		Taiao.id("rpos")
	);

	public static final RegistryKey<BannerPattern> PAATIKI_PRIMARY = register(
		Taiao.id("paatiki_primary"),
		Taiao.id("pap")
	);
	public static final RegistryKey<BannerPattern> PAATIKI_SECONDARY = register(
		Taiao.id("paatiki_secondary"),
		Taiao.id("pas")
	);

	public static final RegistryKey<BannerPattern> KAOKAO_UP_PRIMARY = register(
		Taiao.id("kaokao_up_primary"),
		Taiao.id("ukp")
	);
	public static final RegistryKey<BannerPattern> KAOKAO_UP_SECONDARY = register(
		Taiao.id("kaokao_up_secondary"),
		Taiao.id("uks")
	);
	public static final RegistryKey<BannerPattern> KAOKAO_DOWN_PRIMARY = register(
		Taiao.id("kaokao_down_primary"),
		Taiao.id("dkp")
	);
	public static final RegistryKey<BannerPattern> KAOKAO_DOWN_SECONDARY = register(
		Taiao.id("kaokao_down_secondary"),
		Taiao.id("dks")
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registering banner patterns");
	}

	public static RegistryKey<BannerPattern> register(Identifier id, @NotNull Identifier nbtId) {
		return register(id, new BannerPattern(nbtId.toString()));
	}

	public static RegistryKey<BannerPattern> register(Identifier id, BannerPattern pattern) {
		RegistryKey<BannerPattern> key = RegistryKey.of(RegistryKeys.BANNER_PATTERN, id);

		Registry.register(Registries.BANNER_PATTERN, key, pattern);

		return key;
	}

	public static final ItemStack POUTAMA_LEFT_TUKUTUKU = getBannerItemStack(
		Taiao.id("poutama_left_tukutuku"),
		Items.BLACK_BANNER,
		new BannerPattern.Patterns()
			.add(POUTAMA_LEFT_PRIMARY, DyeColor.WHITE)
			.add(POUTAMA_LEFT_SECONDARY, DyeColor.YELLOW)
	);
	public static final ItemStack POUTAMA_RIGHT_TUKUTUKU = getBannerItemStack(
		Taiao.id("poutama_right_tukutuku"),
		Items.BLACK_BANNER,
		new BannerPattern.Patterns()
			.add(POUTAMA_RIGHT_PRIMARY, DyeColor.WHITE)
			.add(POUTAMA_RIGHT_SECONDARY, DyeColor.YELLOW)
	);

	public static final ItemStack PAATIKI_TUKUTUKU = getBannerItemStack(
		Taiao.id("paatiki_tukutuku"),
		Items.BLACK_BANNER,
		new BannerPattern.Patterns()
			.add(PAATIKI_PRIMARY, DyeColor.WHITE)
			.add(PAATIKI_SECONDARY, DyeColor.YELLOW)
	);

	public static final ItemStack KAOKAO_TUKUTUKU = getBannerItemStack(
		Taiao.id("kaokao_up_tukutuku"),
		Items.BLACK_BANNER,
		new BannerPattern.Patterns()
			.add(BannerPatterns.STRIPE_TOP, DyeColor.RED)
			.add(BannerPatterns.STRIPE_BOTTOM, DyeColor.YELLOW)
			.add(KAOKAO_UP_PRIMARY, DyeColor.WHITE)
			.add(KAOKAO_UP_SECONDARY, DyeColor.YELLOW)
	);

	/**
	 * Creates an {@link ItemStack} of the given {@code base} banner with the given {@code patterns}.
	 *
	 * @param id       the ID used for the translation key, like {@code block.modid.name}
	 * @param base     the base banner block
	 * @param patterns ordered patterns to add to the banner
	 */
	public static @NotNull ItemStack getBannerItemStack(
		@NotNull Identifier id,
		Item base,
		BannerPattern.@NotNull Patterns patterns
	) {
		ItemStack stack = new ItemStack(base);
		NbtCompound nbt = new NbtCompound();

		nbt.put("Patterns", patterns.toNbt());
		BlockItem.setBlockEntityNbt(stack, BlockEntityType.BANNER, nbt);
		stack.addHideFlag(ItemStack.TooltipSection.ADDITIONAL);
		stack.setCustomName(Text.translatable(id.toTranslationKey("block")).formatted(Formatting.GOLD));

		return stack;
	}
}
