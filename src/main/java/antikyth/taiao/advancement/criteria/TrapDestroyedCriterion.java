// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.advancement.criteria;

import antikyth.taiao.Taiao;
import antikyth.taiao.loot.TaiaoLootContextTypes;
import antikyth.taiao.loot.predicate.BooleanPredicate;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TrapDestroyedCriterion extends AbstractCriterion<TrapDestroyedCriterion.Conditions> {
	protected static final Identifier ID = Taiao.id("trap_destroyed");

	@Override
	public Identifier getId() {
		return ID;
	}

	public void trigger(
		@NotNull ServerPlayerEntity player,
		BlockPos pos,
		@Nullable Entity trappedEntity,
		ItemStack bait,
		ItemStack tool,
		boolean released
	) {
		ServerWorld world = player.getServerWorld();
		BlockState state = world.getBlockState(pos);

		LootContextParameterSet blockParameters = new LootContextParameterSet.Builder(world)
			.add(LootContextParameters.ORIGIN, pos.toCenterPos())
			.add(LootContextParameters.THIS_ENTITY, player)
			.add(LootContextParameters.BLOCK_STATE, state)
			.add(LootContextParameters.TOOL, tool)
			.build(LootContextTypes.ADVANCEMENT_LOCATION);

		LootContextParameterSet.Builder entityParameters = new LootContextParameterSet.Builder(world)
			.add(LootContextParameters.ORIGIN, pos.toCenterPos());
		if (trappedEntity != null) entityParameters.add(LootContextParameters.THIS_ENTITY, trappedEntity);

		LootContext block = new LootContext.Builder(blockParameters).build(null);
		LootContext entity = new LootContext.Builder(
			entityParameters.build(TaiaoLootContextTypes.ADVANCEMENT_OPTIONAL_ENTITY)
		).build(null);

		this.trigger(player, conditions -> conditions.test(block, entity, bait, released));
	}

	@Override
	protected Conditions conditionsFromJson(
		@NotNull JsonObject json,
		LootContextPredicate player,
		AdvancementEntityPredicateDeserializer deserializer
	) {
		LootContextPredicate block = LootContextPredicate.fromJson(
			"block",
			deserializer,
			json.get("block"),
			LootContextTypes.ADVANCEMENT_LOCATION
		);
		LootContextPredicate entity = LootContextPredicate.fromJson(
			"entity",
			deserializer,
			json.get("entity"),
			TaiaoLootContextTypes.ADVANCEMENT_OPTIONAL_ENTITY
		);
		ItemPredicate bait = ItemPredicate.fromJson(json.get("bait"));
		BooleanPredicate released = BooleanPredicate.fromJson(json.get("released"));

		return new Conditions(
			player,
			block == null ? LootContextPredicate.EMPTY : block,
			entity == null ? LootContextPredicate.EMPTY : entity,
			bait,
			released
		);
	}

	public static class Conditions extends AbstractCriterionConditions {
		protected final LootContextPredicate block;
		protected final LootContextPredicate entity;
		protected final ItemPredicate bait;
		protected final BooleanPredicate released;

		public Conditions(
			LootContextPredicate player,
			LootContextPredicate block,
			LootContextPredicate entity,
			ItemPredicate bait,
			BooleanPredicate released
		) {
			super(ID, player);

			this.block = block;
			this.entity = entity;
			this.bait = bait;
			this.released = released;
		}

		public static @NotNull Conditions create(
			Block block,
			LootContextPredicate entity,
			ItemPredicate bait,
			BooleanPredicate released
		) {
			return create(
				LootContextPredicate.create(
					LocationCheckLootCondition.builder(
						LocationPredicate.Builder.create()
							.block(BlockPredicate.Builder.create().blocks(block).build())
					).build()
				),
				entity,
				bait,
				released
			);
		}

		@Contract("_, _, _, _ -> new")
		public static @NotNull Conditions create(
			LootContextPredicate block,
			LootContextPredicate entity,
			ItemPredicate bait,
			BooleanPredicate released
		) {
			return new Conditions(LootContextPredicate.EMPTY, block, entity, bait, released);
		}

		public boolean test(LootContext block, LootContext entity, ItemStack bait, boolean released) {
			return this.block.test(block)
				&& this.entity.test(entity)
				&& this.bait.test(bait)
				&& this.released.test(released);
		}

		@Override
		public JsonObject toJson(AdvancementEntityPredicateSerializer serializer) {
			JsonObject json = super.toJson(serializer);

			json.add("block", this.block.toJson(serializer));
			json.add("entity", this.entity.toJson(serializer));
			json.add("bait", this.bait.toJson());
			json.add("released", this.released.toJson());

			return json;
		}
	}
}
