// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.advancement.criteria;

import antikyth.taiao.Taiao;
import antikyth.taiao.advancement.criteria.predicate.KetePredicate;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NumberRange.IntRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class KeteStackCountCriterion extends AbstractCriterion<KeteStackCountCriterion.Conditions> {
	protected static final Identifier ID = Taiao.id("kete_changed");

	@Override
	public Identifier getId() {
		return ID;
	}

	public void trigger(ServerPlayerEntity player, ItemStack stack) {
		this.trigger(player, conditions -> conditions.matches(stack));
	}

	@Override
	protected Conditions conditionsFromJson(
		JsonObject json,
		LootContextPredicate player,
		AdvancementEntityPredicateDeserializer deserializer
	) {
		return new Conditions(player, KetePredicate.fromJson(json));
	}

	public static class Conditions extends AbstractCriterionConditions {
		private final KetePredicate predicate;

		public Conditions(LootContextPredicate player, KetePredicate predicate) {
			super(ID, player);

			this.predicate = predicate;
		}

		@Contract("_ -> new")
		public static @NotNull Conditions create(IntRange stackCount) {
			return new Conditions(LootContextPredicate.EMPTY, new KetePredicate(stackCount));
		}

		public boolean matches(@NotNull ItemStack stack) {
			return !stack.isEmpty() && this.predicate.test(stack);
		}

		@Override
		public JsonObject toJson(AdvancementEntityPredicateSerializer serializer) {
			JsonObject json = super.toJson(serializer);

			this.predicate.writeJson(json);

			return json;
		}
	}
}
