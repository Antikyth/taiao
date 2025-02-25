// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.loot.function;

import antikyth.taiao.Taiao;
import antikyth.taiao.item.kete.KeteItem;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class AddKeteContentsLootFunction extends ConditionalLootFunction {
	protected final LootPool pool;

	protected AddKeteContentsLootFunction(LootCondition[] conditions, LootPool pool) {
		super(conditions);

		this.pool = pool;
	}

	@Override
	public LootFunctionType getType() {
		return TaiaoLootFunctionTypes.ADD_KETE_CONTENTS;
	}

	@Override
	protected ItemStack process(@NotNull ItemStack kete, LootContext context) {
		if (kete.isEmpty()) return kete;

		DefaultedList<ItemStack> stacks = DefaultedList.of();
		this.pool.addGeneratedLoot(LootTable.processStacks(context.getWorld(), stacks::add), context);

		for (ItemStack stack : stacks) {
			if (!stack.isEmpty() && KeteItem.addToKete(kete, stack) <= 0) {
				Taiao.LOGGER.warn("Tried to overfill a kete");
			}
		}

		return kete;
	}

	@Contract("_ -> new")
	public static @NotNull Builder builder(LootPool.Builder pool) {
		return new Builder(pool);
	}

	public static class Builder extends ConditionalLootFunction.Builder<AddKeteContentsLootFunction.Builder> {
		protected final LootPool.Builder pool;

		Builder(LootPool.Builder pool) {
			this.pool = pool;
		}

		@Override
		protected Builder getThisBuilder() {
			return this;
		}

		@Override
		public LootFunction build() {
			return new AddKeteContentsLootFunction(this.getConditions(), this.pool.build());
		}
	}

	public static class Serializer extends ConditionalLootFunction.Serializer<AddKeteContentsLootFunction> {
		@Override
		public void toJson(
			JsonObject json,
			AddKeteContentsLootFunction function,
			JsonSerializationContext context
		) {
			super.toJson(json, function, context);

			json.add("pool", context.serialize(function.pool));
		}

		@Override
		public AddKeteContentsLootFunction fromJson(
			JsonObject json,
			JsonDeserializationContext context,
			LootCondition[] conditions
		) {
			LootPool pool = JsonHelper.deserialize(json, "pool", context, LootPool.class);

			return new AddKeteContentsLootFunction(conditions, pool);
		}
	}
}
