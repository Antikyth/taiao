// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.damage;

import antikyth.taiao.Taiao;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class TaiaoDamageTypeTags {
	public static final TagKey<DamageType> TRAPS = tagKey(Taiao.id("traps"));

	public static TagKey<DamageType> tagKey(Identifier id) {
		return TagKey.of(RegistryKeys.DAMAGE_TYPE, id);
	}
}
