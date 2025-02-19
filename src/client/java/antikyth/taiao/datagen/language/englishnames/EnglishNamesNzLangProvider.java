// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.language.englishnames;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.datagen.language.EnglishUsLangProvider;
import antikyth.taiao.world.gen.loot.TaiaoLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import org.jetbrains.annotations.NotNull;

public class EnglishNamesNzLangProvider extends EnglishNamesGbLangProvider {
	public EnglishNamesNzLangProvider(FabricDataOutput dataOutput) {
		super(dataOutput, "en_nz");
	}

	@Override
	public void generateTranslations(@NotNull TranslationBuilder builder) {
		super.generateTranslations(builder);

		// Other blocks
		builder.add(TaiaoBlocks.HARAKEKE_MAT, "Flax Mat");
		// Plants
		builder.add(TaiaoBlocks.HARAKEKE, "Flax");

		// Chest loot tables
		EnglishUsLangProvider.addEmiLootChestLootTable(
			builder,
			TaiaoLootTables.VILLAGE_MARAE_PAA_HARAKEKE_CHEST,
			"Marae Flax Garden Chest"
		);

		EnglishUsLangProvider.addEelTranslations(builder);
	}
}
