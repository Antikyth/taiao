// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.effect;

import antikyth.taiao.Taiao;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class TaiaoStatusEffectTags {
	public static final TagKey<StatusEffect> TAPU = tagKey(Taiao.id("tapu"));

	public static TagKey<StatusEffect> tagKey(Identifier id) {
		return TagKey.of(RegistryKeys.STATUS_EFFECT, id);
	}
}
