// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.banner;

import antikyth.taiao.Taiao;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;

public class TaiaoBanners {
	public static final Banner POUTAMA_TUKUTUKU_LEFT = new Banner(
		Taiao.id("poutama_tukutuku_left"),
		Items.BLACK_BANNER,
		true,
		new BannerPattern.Patterns()
			.add(TaiaoBannerPatterns.POUTAMA_RIGHT_PRIMARY, DyeColor.WHITE)
			.add(TaiaoBannerPatterns.POUTAMA_RIGHT_SECONDARY, DyeColor.GRAY)
	);
	public static final Banner POUTAMA_TUKUTUKU_RIGHT = new Banner(
		Taiao.id("poutama_tukutuku_right"),
		Items.BLACK_BANNER,
		true,
		new BannerPattern.Patterns()
			.add(TaiaoBannerPatterns.POUTAMA_LEFT_PRIMARY, DyeColor.WHITE)
			.add(TaiaoBannerPatterns.POUTAMA_LEFT_SECONDARY, DyeColor.GRAY)
	);
	public static final Banner PAATIKI_TUKUTUKU = new Banner(
		Taiao.id("paatiki_tukutuku"),
		Items.BLACK_BANNER,
		true,
		new BannerPattern.Patterns()
			.add(BannerPatterns.HALF_HORIZONTAL_BOTTOM, DyeColor.YELLOW)
			.add(TaiaoBannerPatterns.PAATIKI_PRIMARY, DyeColor.WHITE)
			.add(TaiaoBannerPatterns.PAATIKI_SECONDARY, DyeColor.YELLOW)
	);
	public static final Banner PAATIKI_TUKUTUKU_TOP = new Banner(
		Taiao.id("paatiki_tukutuku_top"),
		Items.BLACK_BANNER,
		true,
		new BannerPattern.Patterns()
			.add(TaiaoBannerPatterns.PAATIKI_PRIMARY, DyeColor.WHITE)
			.add(TaiaoBannerPatterns.PAATIKI_SECONDARY, DyeColor.YELLOW)
	);
	public static final Banner PAATIKI_TUKUTUKU_BOTTOM = new Banner(
		Taiao.id("paatiki_tukutuku_bottom"),
		Items.YELLOW_BANNER,
		true,
		new BannerPattern.Patterns()
			.add(TaiaoBannerPatterns.PAATIKI_PRIMARY, DyeColor.WHITE)
			.add(TaiaoBannerPatterns.PAATIKI_SECONDARY, DyeColor.BLACK)
	);
	public static final Banner KAOKAO_TUKUTUKU = new Banner(
		Taiao.id("kaokao_tukutuku"),
		Items.BLACK_BANNER,
		true,
		new BannerPattern.Patterns()
			.add(TaiaoBannerPatterns.KAOKAO_UP_PRIMARY, DyeColor.WHITE)
			.add(TaiaoBannerPatterns.KAOKAO_UP_SECONDARY, DyeColor.RED)
	);
}
