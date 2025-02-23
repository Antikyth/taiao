// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.banner;

import antikyth.taiao.Taiao;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TaiaoBannerPatternTags {
	public static final TagKey<BannerPattern> KIWI_PATTERN_ITEM = of(Taiao.id("pattern_item/kiwi"));

	public static TagKey<BannerPattern> of(Identifier id) {
		return TagKey.of(RegistryKeys.BANNER_PATTERN, id);
	}
}
