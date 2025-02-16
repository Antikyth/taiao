// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.loottable;

import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.item.TaiaoItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.FurnaceSmeltLootFunction;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class TaiaoEntityLootTableProvider extends FabricEntityLootTableProvider {
	public TaiaoEntityLootTableProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generate() {
		this.register(
			TaiaoEntities.PUUKEKO,
			LootTable.builder()
				.pool(
					LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1.0F))
						.with(
							ItemEntry.builder(Items.FEATHER)
								.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(
									0.0F,
									2.0F
								)))
								.apply(LootingEnchantLootFunction.builder(
									UniformLootNumberProvider.create(0.0F, 1.0F)))
						)
				)
				.pool(
					LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1.0F))
						.with(
							ItemEntry.builder(Items.CHICKEN)
								.apply(FurnaceSmeltLootFunction.builder()
									.conditionally(EntityPropertiesLootCondition.builder(
										LootContext.EntityTarget.THIS,
										NEEDS_ENTITY_ON_FIRE
									)))
								.apply(LootingEnchantLootFunction.builder(
									UniformLootNumberProvider.create(0.0F, 1.0F)))
						)
				)
		);
		this.register(
			TaiaoEntities.MOA,
			LootTable.builder()
				.pool(
					LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1.0F))
						.with(
							ItemEntry.builder(Items.FEATHER)
								.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(
									0.0F,
									5.0F
								)))
								.apply(LootingEnchantLootFunction.builder(
									UniformLootNumberProvider.create(0.0F, 2.0F)))
						)
				)
				.pool(
					LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1.0F))
						.with(
							ItemEntry.builder(Items.CHICKEN)
								.apply(FurnaceSmeltLootFunction.builder()
									.conditionally(EntityPropertiesLootCondition.builder(
										LootContext.EntityTarget.THIS,
										NEEDS_ENTITY_ON_FIRE
									)))
								.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(
									1f,
									3f
								)))
								.apply(LootingEnchantLootFunction.builder(
									UniformLootNumberProvider.create(0.0F, 1.0F)))
						)
				)
		);

		this.register(
			TaiaoEntities.EEL,
			LootTable.builder()
				.pool(
					LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1f))
						.with(
							ItemEntry.builder(TaiaoItems.EEL)
								.apply(
									FurnaceSmeltLootFunction.builder().conditionally(
										EntityPropertiesLootCondition.builder(
											LootContext.EntityTarget.THIS,
											NEEDS_ENTITY_ON_FIRE
										)
									)
								)
						)
				)
				.pool(
					LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1f))
						.with(ItemEntry.builder(Items.BONE_MEAL))
						.conditionally(RandomChanceLootCondition.builder(0.05f))
				)
		);
	}
}
