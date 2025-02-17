// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.damage;

import antikyth.taiao.Taiao;
import net.minecraft.entity.damage.DamageEffects;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class TaiaoDamageTypes {
	public static final Map<RegistryKey<DamageType>, DamageType> DAMAGE_TYPES = new HashMap<>();

	public static final RegistryKey<DamageType> HIINAKI = register(Taiao.id("hiinaki"), 0.1f, DamageEffects.POKING);

	public static void bootstrap(Registerable<DamageType> registerable) {
		for (Map.Entry<RegistryKey<DamageType>, DamageType> entry : DAMAGE_TYPES.entrySet()) {
			registerable.register(entry.getKey(), entry.getValue());
		}
	}

	public static RegistryKey<DamageType> register(Identifier id, float exhaustion, DamageEffects effects) {
		return register(id, DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, exhaustion, effects);
	}

	public static RegistryKey<DamageType> register(
		Identifier id,
		DamageScaling scaling,
		float exhaustion,
		DamageEffects effects
	) {
		RegistryKey<DamageType> key = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id);
		String messageId = id.toShortTranslationKey();

		DAMAGE_TYPES.put(key, new DamageType(messageId, scaling, exhaustion, effects));

		return key;
	}
}
