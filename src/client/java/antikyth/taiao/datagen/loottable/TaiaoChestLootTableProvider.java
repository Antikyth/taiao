// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.loottable;

import antikyth.taiao.loot.TaiaoLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class TaiaoChestLootTableProvider extends SimpleFabricLootTableProvider {
	public TaiaoChestLootTableProvider(FabricDataOutput output) {
		super(output, LootContextTypes.CHEST);
	}

	@Override
	public void accept(BiConsumer<Identifier, LootTable.Builder> exporter) {
		TaiaoLootTables.bootstrap(exporter);
	}
}
