// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.loot;

import antikyth.taiao.Taiao;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class TaiaoLootTables {
	protected static Map<Identifier, LootTable.Builder> TO_REGISTER = new HashMap<>();

	public static final Identifier VILLAGE_MARAE_PAATAKA_KAI_CHEST = register(
		Taiao.id("chests/village/village_marae_paataka_kai"),
		LootTable.builder()
			.pool(
				LootPool.builder()
					.rolls(UniformLootNumberProvider.create(3f, 8f))
					.with(
						ItemEntry.builder(Items.POTATO)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 7f)))
					)
					.with(
						ItemEntry.builder(Items.BREAD)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 4f)))
					)
					.with(
						ItemEntry.builder(Items.APPLE)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 5f)))
					)
					.with(
						ItemEntry.builder(Items.CARROT)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4f, 8f)))
					)
					.with(
						ItemEntry.builder(Items.WHEAT)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8f, 21f)))
					)
			)
	);

	public static void bootstrap(BiConsumer<Identifier, LootTable.Builder> exporter) {
		Taiao.LOGGER.debug("Registering loot tables");

		for (Map.Entry<Identifier, LootTable.Builder> entry : TO_REGISTER.entrySet()) {
			exporter.accept(entry.getKey(), entry.getValue());
		}
	}

	public static Identifier register(Identifier id, LootTable.Builder builder) {
		TO_REGISTER.put(id, builder);

		return id;
	}
}
