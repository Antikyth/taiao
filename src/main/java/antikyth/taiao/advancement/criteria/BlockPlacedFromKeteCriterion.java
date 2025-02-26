// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.advancement.criteria;

import antikyth.taiao.Taiao;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class BlockPlacedFromKeteCriterion extends AbstractCriterion<BlockPlacedFromKeteCriterion.Conditions> {
	protected static final Identifier ID = Taiao.id("block_placed_from_kete");

	@Override
	public Identifier getId() {
		return ID;
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

		return new Conditions(player, block == null ? LootContextPredicate.EMPTY : block);
	}

	public void trigger(@NotNull ServerPlayerEntity player, BlockPos pos, ItemStack kete) {
		ServerWorld world = player.getServerWorld();
		BlockState state = world.getBlockState(pos);

		LootContextParameterSet parameters = new LootContextParameterSet.Builder(world)
			.add(LootContextParameters.ORIGIN, pos.toCenterPos())
			.add(LootContextParameters.THIS_ENTITY, player)
			.add(LootContextParameters.BLOCK_STATE, state)
			.add(LootContextParameters.TOOL, kete)
			.build(LootContextTypes.ADVANCEMENT_LOCATION);

		LootContext context = new LootContext.Builder(parameters).build(null);
		this.trigger(player, conditions -> conditions.test(context));
	}

	public static class Conditions extends AbstractCriterionConditions {
		private final LootContextPredicate block;

		public Conditions(LootContextPredicate player, LootContextPredicate block) {
			super(ID, player);

			this.block = block;
		}

		@Contract(" -> new")
		public static @NotNull Conditions create() {
			return create(LocationPredicate.Builder.create());
		}

		@Contract("_ -> new")
		public static @NotNull Conditions create(LocationPredicate.Builder block) {
			LootContextPredicate blockContextPredicate = LootContextPredicate.create(
				LocationCheckLootCondition.builder(block).build()
			);

			return new Conditions(LootContextPredicate.EMPTY, blockContextPredicate);
		}

		@Contract("_ -> new")
		public static @NotNull Conditions create(Block block) {
			return create(LocationPredicate.Builder.create()
				.block(BlockPredicate.Builder.create().blocks(block).build()));
		}

		public boolean test(LootContext context) {
			return this.block.test(context);
		}

		@Override
		public JsonObject toJson(AdvancementEntityPredicateSerializer serializer) {
			JsonObject json = super.toJson(serializer);
			json.add("block", this.block.toJson(serializer));

			return json;
		}
	}
}
