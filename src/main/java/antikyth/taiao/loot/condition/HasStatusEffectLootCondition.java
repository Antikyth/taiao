// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.loot.condition;

import antikyth.taiao.loot.predicate.StatusEffectPredicate;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.JsonSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class HasStatusEffectLootCondition implements LootCondition {
	protected final StatusEffectPredicate predicate;

	HasStatusEffectLootCondition(StatusEffectPredicate predicate) {
		this.predicate = predicate;
	}

	public static LootCondition.@NotNull Builder builder(StatusEffectPredicate.@NotNull Builder predicate) {
		return builder(predicate.build());
	}

	@Contract(pure = true)
	public static LootCondition.@NotNull Builder builder(StatusEffectPredicate predicate) {
		return () -> new HasStatusEffectLootCondition(predicate);
	}

	@Override
	public LootConditionType getType() {
		return TaiaoLootConditionTypes.HAS_STATUS_EFFECT;
	}

	@Override
	public boolean test(@NotNull LootContext context) {
		Entity entity = context.get(LootContextParameters.THIS_ENTITY);

		if (entity instanceof LivingEntity living) {
			for (StatusEffect effect : living.getActiveStatusEffects().keySet()) {
				if (this.predicate.test(effect)) return true;
			}
		}

		return false;
	}

	public static class Serializer implements JsonSerializer<HasStatusEffectLootCondition> {
		@Override
		public void toJson(
			JsonObject json,
			@NotNull HasStatusEffectLootCondition condition,
			JsonSerializationContext context
		) {
			condition.predicate.writeJson(json);
		}

		@Override
		public HasStatusEffectLootCondition fromJson(JsonObject json, JsonDeserializationContext context) {
			return new HasStatusEffectLootCondition(StatusEffectPredicate.fromJson(json));
		}
	}
}
