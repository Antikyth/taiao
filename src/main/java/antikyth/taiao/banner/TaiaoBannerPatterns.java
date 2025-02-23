// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.banner;

import antikyth.taiao.Taiao;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class TaiaoBannerPatterns {
	public static final RegistryKey<BannerPattern> KIWI = register(
		Taiao.id("kiwi"),
		Taiao.id("kw")
	);

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
}
