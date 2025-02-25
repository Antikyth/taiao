// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.language.englishnames;

import antikyth.taiao.banner.TaiaoBanners;
import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.datagen.language.EnglishUsLangProvider;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.world.gen.biome.TaiaoBiomes;
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
		builder.add(TaiaoBlocks.HARAKEKE_MAT, "New Zealand Flax Mat");
		builder.add(TaiaoBlocks.THATCH_ROOF, "Cattail Thatched Roof");
		builder.add(TaiaoBlocks.THATCH_ROOF_TOP, "Cattail Thatched Roof Top");
		// Plants
		builder.add(TaiaoBlocks.RAUPOO, "Cattail");
		builder.add(TaiaoBlocks.HARAKEKE, "New Zealand Flax");

		// Kete
		builder.add(TaiaoItems.KETE, "Basket");
		builder.add(TaiaoItems.KETE.getTranslationKey() + ".filled", "Basket of %s");
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

		// Biomes
		EnglishUsLangProvider.addBiome(builder, TaiaoBiomes.NATIVE_FOREST, "New Zealand Native Forest");
		EnglishUsLangProvider.addBiome(builder, TaiaoBiomes.KAHIKATEA_SWAMP, "New Zealand Kahikatea Swamp");

		// Tukutuku panels
		EnglishUsLangProvider.addBanner(builder, TaiaoBanners.KIWI_TUPUNA_TUKUTUKU, "Kiwi Tukutuku Panel");

		EnglishUsLangProvider.addBanner(
			builder,
			TaiaoBanners.POUTAMA_TUPUNA_TUKUTUKU_LEFT,
			"Poutama Tukutuku Panel Left"
		);
		EnglishUsLangProvider.addBanner(
			builder,
			TaiaoBanners.POUTAMA_TUPUNA_TUKUTUKU_RIGHT,
			"Poutama Tukutuku Panel Right"
		);

		EnglishUsLangProvider.addBanner(
			builder,
			TaiaoBanners.PAATIKI_TUPUNA_TUKUTUKU,
			"Pātiki Tukutuku Panel"
		);
		EnglishUsLangProvider.addBanner(
			builder,
			TaiaoBanners.PAATIKI_TUPUNA_TUKUTUKU_TOP,
			"Pātiki Tukutuku Panel Top"
		);
		EnglishUsLangProvider.addBanner(
			builder,
			TaiaoBanners.PAATIKI_TUPUNA_TUKUTUKU_BOTTOM,
			"Pātiki Tukutuku Panel Bottom"
		);

		EnglishUsLangProvider.addBanner(builder, TaiaoBanners.KAOKAO_TUPUNA_TUKUTUKU, "Kaokao Tukutuku Panel");

		// Chest loot tables
		EnglishUsLangProvider.addEmiLootChestLootTable(
			builder,
			TaiaoLootTables.VILLAGE_MARAE_HOUSE_CHEST,
			"Marae House Chest"
		);
		EnglishUsLangProvider.addEmiLootChestLootTable(
			builder,
			TaiaoLootTables.VILLAGE_MARAE_PAATAKA_KAI_CHEST,
			"Marae Food Pantry Chest"
		);
		EnglishUsLangProvider.addEmiLootChestLootTable(
			builder,
			TaiaoLootTables.VILLAGE_MARAE_PAA_HARAKEKE_CHEST,
			"Marae New Zealand Flax Garden Chest"
		);
		EnglishUsLangProvider.addEmiLootChestLootTable(
			builder,
			TaiaoLootTables.VILLAGE_MARAE_KAAUTA_CHEST,
			"Marae Outdoor Kitchen Chest"
		);
	}
}
