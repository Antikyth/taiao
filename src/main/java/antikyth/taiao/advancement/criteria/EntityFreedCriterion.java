// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.advancement.criteria;

import antikyth.taiao.Taiao;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class EntityFreedCriterion extends AbstractCriterion<EntityFreedCriterion.Conditions> {
	protected static final Identifier ID = Taiao.id("entity_freed");

	@Override
	public Identifier getId() {
		return ID;
	}

	public void trigger(ServerPlayerEntity player, Entity freedEntity) {
		LootContext context = EntityPredicate.createAdvancementEntityLootContext(player, freedEntity);

		this.trigger(player, conditions -> conditions.test(context));
	}

	@Override
	protected Conditions conditionsFromJson(
		JsonObject json,
		LootContextPredicate player,
		AdvancementEntityPredicateDeserializer deserializer
	) {
		LootContextPredicate entity = EntityPredicate.contextPredicateFromJson(json, "entity", deserializer);

		return new Conditions(player, entity);
	}

	public static class Conditions extends AbstractCriterionConditions {
		private final LootContextPredicate entity;

		public Conditions(LootContextPredicate player, LootContextPredicate entity) {
			super(ID, player);

			this.entity = entity;
		}

		@Contract(" -> new")
		public static @NotNull Conditions create() {
			return new Conditions(LootContextPredicate.EMPTY, LootContextPredicate.EMPTY);
		}

		@Contract("_ -> new")
		public static @NotNull Conditions create(LootContextPredicate entity) {
			return new Conditions(LootContextPredicate.EMPTY, entity);
		}

		public boolean test(LootContext context) {
			return this.entity.test(context);
		}

		@Override
		public JsonObject toJson(AdvancementEntityPredicateSerializer serializer) {
			JsonObject json = super.toJson(serializer);
			json.add("entity", this.entity.toJson(serializer));

			return json;
		}
	}
}
