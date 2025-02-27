// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.advancement.criteria;

import antikyth.taiao.Taiao;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ItemCraftedCriterion extends AbstractCriterion<ItemCraftedCriterion.Conditions> {
	protected static final Identifier ID = Taiao.id("item_crafted");

	@Override
	public Identifier getId() {
		return ID;
	}

	public void trigger(ServerPlayerEntity player, Recipe<?> recipe) {
		this.trigger(player, conditions -> conditions.test(player, recipe));
	}

	@Override
	protected Conditions conditionsFromJson(
		@NotNull JsonObject json,
		LootContextPredicate player,
		AdvancementEntityPredicateDeserializer deserializer
	) {
		return new Conditions(player, ItemPredicate.fromJson(json.get("item")));
	}

	public static class Conditions extends AbstractCriterionConditions {
		protected final ItemPredicate item;

		public Conditions(LootContextPredicate player, ItemPredicate item) {
			super(ID, player);

			this.item = item;
		}

		@Contract("_ -> new")
		public static @NotNull Conditions create(ItemPredicate item) {
			return new Conditions(LootContextPredicate.EMPTY, item);
		}

		public boolean test(@NotNull ServerPlayerEntity player, @NotNull Recipe<?> recipe) {
			DynamicRegistryManager manager = player.getWorld().getRegistryManager();

			return this.item.test(recipe.getOutput(manager));
		}

		@Override
		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			JsonObject json = super.toJson(predicateSerializer);

			json.add("item", this.item.toJson());

			return json;
		}
	}
}
