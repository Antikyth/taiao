// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.advancement.criteria;

import antikyth.taiao.Taiao;
import antikyth.taiao.item.kete.KeteItem;
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
		this.trigger(player, conditions -> conditions.test(stack));
	}

	@Override
	protected Conditions conditionsFromJson(
		@NotNull JsonObject json,
		LootContextPredicate player,
		AdvancementEntityPredicateDeserializer deserializer
	) {
		return new Conditions(player, IntRange.fromJson(json.get("stack_count")));
	}

	public static class Conditions extends AbstractCriterionConditions {
		private final IntRange stackCountRange;

		public Conditions(LootContextPredicate player, IntRange stackCountRange) {
			super(ID, player);

			this.stackCountRange = stackCountRange;
		}

		@Contract("_ -> new")
		public static @NotNull Conditions create(IntRange stackCount) {
			return new Conditions(LootContextPredicate.EMPTY, stackCount);
		}

		public boolean test(@NotNull ItemStack stack) {
			return !stack.isEmpty()
				&& stack.getItem() instanceof KeteItem
				&& this.stackCountRange.test(KeteItem.getStackCount(stack));
		}

		@Override
		public JsonObject toJson(AdvancementEntityPredicateSerializer serializer) {
			JsonObject json = super.toJson(serializer);

			json.add("stack_count", this.stackCountRange.toJson());

			return json;
		}
	}
}
