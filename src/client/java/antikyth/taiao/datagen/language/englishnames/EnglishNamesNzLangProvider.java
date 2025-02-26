// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.language.englishnames;

import antikyth.taiao.advancement.TaiaoAdvancements;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.datagen.language.EnglishUsLangProvider;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.loot.TaiaoLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import org.jetbrains.annotations.NotNull;

public class EnglishNamesNzLangProvider extends EnglishNamesGbLangProvider {
	public EnglishNamesNzLangProvider(FabricDataOutput dataOutput) {
		super(dataOutput, "en_nz");
	}

	@Override
	public void generateTranslations(@NotNull TranslationBuilder builder) {
		super.generateTranslations(builder);

		// Advancements
		EnglishUsLangProvider.addAdvancement(
			builder,
			TaiaoAdvancements.HARAKEKE,
			"Whakatika",
			"Harvest flax with shears"
		);
		EnglishUsLangProvider.addAdvancement(
			builder,
			TaiaoAdvancements.BIGGER_ON_THE_INSIDE,
			"Bigger on the Inside",
			"Put more than one full stack's worth of items in a flax basket"
		);
		EnglishUsLangProvider.addAdvancement(
			builder,
			TaiaoAdvancements.EFFICIENT_CONSTRUCTION,
			"Efficient Construction",
			"Place a block directly from a flax basket"
		);

		// Other blocks
		builder.add(TaiaoBlocks.HARAKEKE_MAT, "Flax Mat");
		// Plants
		builder.add(TaiaoBlocks.HARAKEKE, "Flax");

		// Kete
		builder.add(TaiaoItems.KETE, "Flax Basket");
		builder.add(TaiaoItems.KETE.getTranslationKey() + ".filled", "Flax Basket of %s");

		// Chest loot tables
		EnglishUsLangProvider.addEmiLootChestLootTable(
			builder,
			TaiaoLootTables.VILLAGE_MARAE_PAA_HARAKEKE_CHEST,
			"Marae Flax Garden Chest"
		);

		EnglishUsLangProvider.addEelTranslations(builder);
	}
}
