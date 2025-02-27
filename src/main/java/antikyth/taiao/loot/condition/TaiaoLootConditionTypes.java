// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.loot.condition;

import antikyth.taiao.Taiao;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonSerializer;

public class TaiaoLootConditionTypes {
	public static final LootConditionType HAS_STATUS_EFFECT_TAG = register(
		Taiao.id("has_status_effect_tag"),
		new HasStatusEffectTagLootCondition.Serializer()
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registered loot condition types");
	}

	public static LootConditionType register(Identifier id, JsonSerializer<? extends LootCondition> serializer) {
		return Registry.register(Registries.LOOT_CONDITION_TYPE, id, new LootConditionType(serializer));
	}
}
