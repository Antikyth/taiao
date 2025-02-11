// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.language;

import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.world.gen.loot.TaiaoLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class EnglishNamesUsLangProvider extends FabricLanguageProvider {
	public EnglishNamesUsLangProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generateTranslations(TranslationBuilder builder) {
		// Tī kōuka/cabbage tree
		EnglishUsLangProvider.addTreeBlocks(
			builder,
			TaiaoBlocks.CABBAGE_TREE_SAPLING,
			TaiaoBlocks.POTTED_CABBAGE_TREE_SAPLING,
			TaiaoBlocks.CABBAGE_TREE_LEAVES,
			TaiaoBlocks.CABBAGE_TREE_LOG,
			TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG,
			TaiaoBlocks.CABBAGE_TREE_WOOD,
			TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD,
			TaiaoBlockTags.CABBAGE_TREE_LOGS,
			TaiaoItemTags.CABBAGE_TREE_LOGS,
			"Cabbage Tree"
		);

		// Other blocks
		builder.add(TaiaoBlocks.RAUPOO, "Cattail");

		// Items
		builder.add(TaiaoItems.CONIFER_FRUIT, "Conifer Fruit");

		// Animals
		EnglishUsLangProvider.addAnimal(
			builder,
			TaiaoEntities.AUSTRALASIAN_BITTERN,
			TaiaoItems.AUSTRALASIAN_BITTERN_SPAWN_EGG,
			TaiaoItemTags.AUSTRALASIAN_BITTERN_FOOD,
			"Australasian Bittern"
		);

		// Chest loot tables
		EnglishUsLangProvider.addEmiLootChestLootTable(
			builder,
			TaiaoLootTables.VILLAGE_MARAE_HOUSE_CHEST,
			"Marae House Chest"
		);
	}
}
