// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.loot.condition;

import antikyth.taiao.loot.predicate.EffectTagPredicate;
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

public class HasStatusEffectTagLootCondition implements LootCondition {
	protected final EffectTagPredicate predicate;

	HasStatusEffectTagLootCondition(EffectTagPredicate predicate) {
		this.predicate = predicate;
	}

	@Contract(pure = true)
	public static LootCondition.@NotNull Builder create(EffectTagPredicate predicate) {
		return () -> new HasStatusEffectTagLootCondition(predicate);
	}

	@Override
	public LootConditionType getType() {
		return TaiaoLootConditionTypes.HAS_STATUS_EFFECT_TAG;
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

	public static class Serializer implements JsonSerializer<HasStatusEffectTagLootCondition> {
		@Override
		public void toJson(
			JsonObject json,
			@NotNull HasStatusEffectTagLootCondition condition,
			JsonSerializationContext context
		) {
			condition.predicate.writeJson(json);
		}

		@Override
		public HasStatusEffectTagLootCondition fromJson(JsonObject json, JsonDeserializationContext context) {
			return new HasStatusEffectTagLootCondition(EffectTagPredicate.fromJson(json));
		}
	}
}
