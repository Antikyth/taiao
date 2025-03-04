// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.language;

import antikyth.taiao.Taiao;
import antikyth.taiao.TaiaoBuiltinResourcePacks;
import antikyth.taiao.TriConsumer;
import antikyth.taiao.advancement.TaiaoAdvancements;
import antikyth.taiao.banner.Banner;
import antikyth.taiao.banner.TaiaoBannerPatterns;
import antikyth.taiao.banner.TaiaoBanners;
import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.boat.TaiaoBoats;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.entity.damage.TaiaoDamageTypes;
import antikyth.taiao.item.TaiaoItemGroups;
import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.loot.TaiaoLootTables;
import antikyth.taiao.sound.TaiaoSoundEvents;
import antikyth.taiao.stat.TaiaoStats;
import antikyth.taiao.world.gen.biome.TaiaoBiomes;
import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class EnglishUsLangProvider extends FabricLanguageProvider {
	public EnglishUsLangProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generateTranslations(@NotNull TranslationBuilder builder) {
		addBuiltinResourcePack(builder, TaiaoBuiltinResourcePacks.ENGLISH_NAMES, "English Names");

		// Item groups
		builder.add(TaiaoItemGroups.MAIN, Taiao.MOD_NAME);

		// Advancements
		addAdvancement(
			builder,
			TaiaoAdvancements.ROOT,
			Taiao.MOD_NAME,
			"The beautiful environment of Aotearoa New Zealand"
		);

		addAdvancement(
			builder,
			TaiaoAdvancements.BOOM_BOOM,
			"Boom Boom",
			"Tame a kākāpō"
		);

		addAdvancement(
			builder,
			TaiaoAdvancements.TOHUNGA_WHAKAIRO,
			"Tohunga Whakairo",
			"Craft a carved wooden block"
		);

		addAdvancement(
			builder,
			TaiaoAdvancements.HARAKEKE,
			"Whakatika",
			"Harvest harakeke with shears without any harmful status effects active"
		);
		addAdvancement(
			builder,
			TaiaoAdvancements.BIGGER_ON_THE_INSIDE,
			"Bigger on the Inside",
			"Put more than one full stack's worth of items in a kete"
		);
		addAdvancement(
			builder,
			TaiaoAdvancements.EFFICIENT_CONSTRUCTION,
			"Efficient Construction",
			"Place a block directly from a kete"
		);

		// Kauri
		addTreeBlocks(
			builder,
			TaiaoBlocks.KAURI_SAPLING,
			TaiaoBlocks.POTTED_KAURI_SAPLING,
			TaiaoBlocks.KAURI_LEAVES,
			TaiaoBlocks.KAURI_LOG,
			TaiaoBlocks.STRIPPED_KAURI_LOG,
			TaiaoBlocks.KAURI_WOOD,
			TaiaoBlocks.STRIPPED_KAURI_WOOD,
			TaiaoBlockTags.KAURI_LOGS,
			TaiaoItemTags.KAURI_LOGS,
			"Kauri"
		);
		addWoodFamily(builder, TaiaoBlocks.WoodFamily.KAURI.getBlockFamily(), "Kauri");
		addBoat(builder, TaiaoBoats.KAURI, "Kauri");
		// Kahikatea
		addTreeBlocks(
			builder,
			TaiaoBlocks.KAHIKATEA_SAPLING,
			TaiaoBlocks.POTTED_KAHIKATEA_SAPLING,
			TaiaoBlocks.KAHIKATEA_LEAVES,
			TaiaoBlocks.KAHIKATEA_LOG,
			TaiaoBlocks.STRIPPED_KAHIKATEA_LOG,
			TaiaoBlocks.KAHIKATEA_WOOD,
			TaiaoBlocks.STRIPPED_KAHIKATEA_WOOD,
			TaiaoBlockTags.KAHIKATEA_LOGS,
			TaiaoItemTags.KAHIKATEA_LOGS,
			"Kahikatea"
		);
		addWoodFamily(builder, TaiaoBlocks.WoodFamily.KAHIKATEA.getBlockFamily(), "Kahikatea");
		addBoat(builder, TaiaoBoats.KAHIKATEA, "Kahikatea");
		// Rimu
		addTreeBlocks(
			builder,
			TaiaoBlocks.RIMU_SAPLING,
			TaiaoBlocks.POTTED_RIMU_SAPLING,
			TaiaoBlocks.RIMU_LEAVES,
			TaiaoBlocks.RIMU_LOG,
			TaiaoBlocks.STRIPPED_RIMU_LOG,
			TaiaoBlocks.RIMU_WOOD,
			TaiaoBlocks.STRIPPED_RIMU_WOOD,
			TaiaoBlockTags.RIMU_LOGS,
			TaiaoItemTags.RIMU_LOGS,
			"Rimu"
		);
		builder.add(TaiaoBlocks.CHISELED_STRIPPED_RIMU_LOG, "Chiseled Stripped Rimu Log");
		builder.add(TaiaoBlocks.CHISELED_STRIPPED_RIMU_WOOD, "Chiseled Stripped Rimu Wood");
		addWoodFamily(builder, TaiaoBlocks.WoodFamily.RIMU.getBlockFamily(), "Rimu");
		addBoat(builder, TaiaoBoats.RIMU, "Rimu");
		// Tī kōuka/cabbage tree
		addTreeBlocks(
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
			"Tī Kōuka"
		);
		// Mamaku
		addTreeBlocks(
			builder,
			TaiaoBlocks.MAMAKU_SAPLING,
			TaiaoBlocks.POTTED_MAMAKU_SAPLING,
			TaiaoBlocks.MAMAKU_LEAVES,
			TaiaoBlocks.MAMAKU_LOG,
			TaiaoBlocks.STRIPPED_MAMAKU_LOG,
			TaiaoBlocks.MAMAKU_WOOD,
			TaiaoBlocks.STRIPPED_MAMAKU_WOOD,
			TaiaoBlockTags.MAMAKU_LOGS,
			TaiaoItemTags.MAMAKU_LOGS,
			"Mamaku"
		);
		addWoodFamily(builder, TaiaoBlocks.WoodFamily.MAMAKU.getBlockFamily(), "Mamaku");
		addBoat(builder, TaiaoBoats.MAMAKU, "Mamaku");
		// Whekī ponga
		addTreeBlocks(
			builder,
			TaiaoBlocks.WHEKII_PONGA_SAPLING,
			TaiaoBlocks.POTTED_WHEKII_PONGA_SAPLING,
			TaiaoBlocks.WHEKII_PONGA_LEAVES,
			TaiaoBlocks.WHEKII_PONGA_LOG,
			TaiaoBlocks.STRIPPED_WHEKII_PONGA_LOG,
			TaiaoBlocks.WHEKII_PONGA_WOOD,
			TaiaoBlocks.STRIPPED_WHEKII_PONGA_WOOD,
			TaiaoBlockTags.WHEKII_PONGA_LOGS,
			TaiaoItemTags.WHEKII_PONGA_LOGS,
			"Whekī Ponga"
		);

		// Other blocks
		builder.add(TaiaoBlocks.HARAKEKE_MAT, "Harakeke Mat");
		builder.add(TaiaoBlocks.THATCH_ROOF, "Raupō Thatched Roof");
		builder.add(TaiaoBlocks.THATCH_ROOF_TOP, "Raupō Thatched Roof Top");
		// Plants
		builder.add(TaiaoBlocks.GIANT_CANE_RUSH, "Giant Cane Rush");
		builder.add(TaiaoBlocks.RAUPOO, "Raupō");
		builder.add(TaiaoBlocks.HARAKEKE, "Harakeke");

		// Kete
		builder.add(TaiaoItems.KETE, "Kete");
		builder.add(TaiaoItems.KETE.getTranslationKey() + ".filled", "Kete of %s");
		builder.add(TaiaoItems.KETE.getTranslationKey() + ".fullness", "%s/%s");

		addDescription(builder, TaiaoItems.KETE, "place1", "Use on Block:");
		addDescription(builder, TaiaoItems.KETE, "place2", "Places %s");
		// Other items
		addBannerPatternItem(builder, TaiaoItems.KIWI_BANNER_PATTERN, "Kiwi");
		builder.add(TaiaoItems.CONIFER_FRUIT, "Huarākau");

		// Birds
		addAnimal(builder, TaiaoEntities.KIWI, TaiaoItems.KIWI_SPAWN_EGG, TaiaoItemTags.KIWI_FOOD, "Kiwi");
		addAnimal(builder, TaiaoEntities.PUUKEKO, TaiaoItems.PUUKEKO_SPAWN_EGG, TaiaoItemTags.PUUKEKO_FOOD, "Pūkeko");
		addAnimal(
			builder,
			TaiaoEntities.HAASTS_EAGLE,
			TaiaoItems.HAASTS_EAGLE_SPAWN_EGG,
			TaiaoItemTags.HAASTS_EAGLE_FOOD,
			"Hokioi"
		);
		addAnimal(builder, TaiaoEntities.MOA, TaiaoItems.MOA_SPAWN_EGG, TaiaoItemTags.MOA_FOOD, "Moa");
		addAnimal(
			builder,
			TaiaoEntities.KAAKAAPOO,
			TaiaoItems.KAAKAAPOO_SPAWN_EGG,
			TaiaoItemTags.KAAKAAPOO_FOOD,
			"Kākāpō"
		);
		addAnimal(
			builder,
			TaiaoEntities.AUSTRALASIAN_BITTERN,
			TaiaoItems.AUSTRALASIAN_BITTERN_SPAWN_EGG,
			TaiaoItemTags.AUSTRALASIAN_BITTERN_FOOD,
			"Matuku-Hūrepo"
		);
		addAnimal(
			builder,
			TaiaoEntities.WEETAA,
			TaiaoItems.WEETAA_SPAWN_EGG,
			null,
			"Wētā"
		);
		builder.add(TaiaoItems.WEETAA, "Wētā");
		// Eels
		addEelTranslations(builder);

		// Biomes
		addBiome(builder, TaiaoBiomes.NATIVE_FOREST, "Aotearoa Native Forest");
		addBiome(builder, TaiaoBiomes.KAHIKATEA_SWAMP, "Aotearoa Kahikatea Swamp");

		// Item tags
		addItemTag(builder, TaiaoItemTags.CARVINGS, "Carvings");
		addItemTag(builder, TaiaoItemTags.FERNS, "Ferns");

		// Block tags
		addBlockAndItemTag(builder, TaiaoBlockTags.THIN_LOGS, TaiaoItemTags.THIN_LOGS, "Thin Logs");
		addBlockTag(builder, TaiaoBlockTags.THIN_LOG_CONNECTION_OVERRIDE, "Thin Logs Connection Override");
		addBlockTag(builder, TaiaoBlockTags.DIRECTIONAL_LEAVES, "Directional Leaves");

		// Conventional tags
		addItemTag(builder, TaiaoItemTags.CONVENTIONAL_BERRIES, "Berries");
		addItemTag(builder, TaiaoItemTags.CONVENTIONAL_VINES, "Vines");

		// Subtitles
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_KIWI_AMBIENT, "Kiwi chirps");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_KIWI_DEATH, "Kiwi dies");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_KIWI_HURT, "Kiwi hurts");

		addSubtitles(builder, TaiaoSoundEvents.ENTITY_PUUKEKO_BABY_AMBIENT, "Baby pūkeko squawks");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_PUUKEKO_ADULT_AMBIENT, "Pūkeko squawks");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_PUUKEKO_BABY_DEATH, "Baby pūkeko dies");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_PUUKEKO_ADULT_DEATH, "Pūkeko dies");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_PUUKEKO_BABY_HURT, "Baby pūkeko hurts");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_PUUKEKO_ADULT_HURT, "Pūkeko hurts");

		addSubtitles(builder, TaiaoSoundEvents.ENTITY_KAAKAAPOO_AMBIENT, "Kākāpō booms");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_KAAKAAPOO_DEATH, "Kākāpō dies");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_KAAKAAPOO_HURT, "Kākāpō hurts");

		// Tupuna tukutuku
		addBanner(builder, TaiaoBanners.KIWI_TUPUNA_TUKUTUKU, "Kiwi Tupuna Tukutuku");

		addBanner(builder, TaiaoBanners.POUTAMA_TUPUNA_TUKUTUKU_LEFT, "Poutama Tupuna Tukutuku Left");
		addBanner(builder, TaiaoBanners.POUTAMA_TUPUNA_TUKUTUKU_RIGHT, "Poutama Tupuna Tukutuku Right");

		addBanner(builder, TaiaoBanners.PAATIKI_TUPUNA_TUKUTUKU, "Pātiki Tupuna Tukutuku");
		addBanner(builder, TaiaoBanners.PAATIKI_TUPUNA_TUKUTUKU_TOP, "Pātiki Tupuna Tukutuku Top");
		addBanner(builder, TaiaoBanners.PAATIKI_TUPUNA_TUKUTUKU_BOTTOM, "Pātiki Tupuna Tukutuku Bottom");

		addBanner(builder, TaiaoBanners.KAOKAO_TUPUNA_TUKUTUKU, "Kaokao Tupuna Tukutuku");

		// Banner patterns
		addBannerPatterns(builder, EnglishUsLangProvider::addBannerPattern);

		// Chest loot tables
		addEmiLootChestLootTable(builder, TaiaoLootTables.VILLAGE_MARAE_HOUSE_CHEST, "Marae Whare Chest");
		addEmiLootChestLootTable(builder, TaiaoLootTables.VILLAGE_MARAE_PAATAKA_KAI_CHEST, "Marae Pātaka Kai Chest");
		addEmiLootChestLootTable(builder, TaiaoLootTables.VILLAGE_MARAE_PAA_HARAKEKE_CHEST, "Marae Pā Harakeke Chest");
		addEmiLootChestLootTable(builder, TaiaoLootTables.VILLAGE_MARAE_KAAUTA_CHEST, "Marae Kāuta Chest");
		addEmiLootChestLootTable(builder, TaiaoLootTables.VILLAGE_MARAE_FISHER_CHEST, "Marae Fisherman's Chest");

		// Hīnaki descriptions
		addDescription(builder, TaiaoBlocks.HIINAKI, "hurt1", "Interact:");
		addDescription(builder, TaiaoBlocks.HIINAKI, "hurt2", "Attacks Trapped Animal");

		addDescription(builder, TaiaoBlocks.HIINAKI, "free1", "Break:");
		addDescription(builder, TaiaoBlocks.HIINAKI, "free2", "Frees Trapped Animal");

		addDescription(builder, TaiaoBlocks.HIINAKI, "activate1", "Place in Water:");
		addDescription(builder, TaiaoBlocks.HIINAKI, "activate2", "Attracts Prey");

		addDescription(builder, TaiaoBlocks.HIINAKI, "remove_bait1", "Interact:");
		addDescription(builder, TaiaoBlocks.HIINAKI, "remove_bait2", "Removes Bait");

		addDescription(builder, TaiaoBlocks.HIINAKI, "add_bait1", "Interact with Bait:");
		addDescription(builder, TaiaoBlocks.HIINAKI, "add_bait2", "Sets Bait");

		addDescription(builder, TaiaoBlocks.HIINAKI, "activate_with_bait1", "Place in Water with Bait:");
		addDescription(builder, TaiaoBlocks.HIINAKI, "activate_with_bait2", "Attracts Prey");
	}

	/**
	 * Adds translations for {@linkplain TaiaoBannerPatterns banner patterns}.
	 * <p>
	 * This exists to easily add English banner pattern translations where the dye colors vary by region (i.e., gray
	 * vs. grey).
	 *
	 * @param add the method used to add each translation
	 */
	public static void addBannerPatterns(
		TranslationBuilder builder,
		@NotNull TriConsumer<TranslationBuilder, RegistryKey<BannerPattern>, String> add
	) {
		add.accept(builder, TaiaoBannerPatterns.KIWI, "Kiwi");

		add.accept(builder, TaiaoBannerPatterns.POUTAMA_LEFT_PRIMARY, "Poutama");
		add.accept(builder, TaiaoBannerPatterns.POUTAMA_LEFT_SECONDARY, "Poutama Shifted");
		add.accept(builder, TaiaoBannerPatterns.POUTAMA_RIGHT_PRIMARY, "Poutama Inverted");
		add.accept(builder, TaiaoBannerPatterns.POUTAMA_RIGHT_SECONDARY, "Poutama Shifted Inverted");

		add.accept(builder, TaiaoBannerPatterns.PAATIKI_PRIMARY, "Pātiki Outer");
		add.accept(builder, TaiaoBannerPatterns.PAATIKI_SECONDARY, "Pātiki Inner");

		add.accept(builder, TaiaoBannerPatterns.KAOKAO_UP_PRIMARY, "Kaokao Thick");
		add.accept(builder, TaiaoBannerPatterns.KAOKAO_UP_SECONDARY, "Kaokao Thin");
		add.accept(builder, TaiaoBannerPatterns.KAOKAO_DOWN_PRIMARY, "Kaokao Thick Inverted");
		add.accept(builder, TaiaoBannerPatterns.KAOKAO_DOWN_SECONDARY, "Kaokao Thin Inverted");
	}

	public static void addEelTranslations(@NotNull TranslationBuilder builder) {
		// Advancements
		addAdvancement(
			builder,
			TaiaoAdvancements.SIT_BACK_AND_RELAX,
			"Sit Back and Relax",
			"Wait for an eel trap to trap an animal, then interact with the trap to hurt or kill it"
		);
		addAdvancement(
			builder,
			TaiaoAdvancements.A_KIND_HEART,
			"A Kind Heart",
			"Break an eel trap to free the animal trapped inside"
		);
		addAdvancement(
			builder,
			TaiaoAdvancements.A_LITTLE_TRIP,
			"A Little Trip, You and Me",
			"Break an eel trap with silk touch while an animal is inside"
		);

		// Other blocks
		builder.add(TaiaoBlocks.HIINAKI, "Eel Trap");

		// Item tags
		EnglishUsLangProvider.addItemTag(builder, TaiaoItemTags.HIINAKI_BAIT, "Eel Trap Bait");

		// Fishes
		EnglishUsLangProvider.addAnimal(builder, TaiaoEntities.EEL, TaiaoItems.EEL_SPAWN_EGG, null, "Eel");
		builder.add(TaiaoItems.EEL, "Raw Eel");
		builder.add(TaiaoItems.COOKED_EEL, "Cooked Eel");
		builder.add(TaiaoItems.EEL_BUCKET, "Bucket of Eel");

		// Subtitles
		EnglishUsLangProvider.addSubtitles(builder, TaiaoSoundEvents.ENTITY_EEL_DEATH, "Eel dies");
		EnglishUsLangProvider.addSubtitles(builder, TaiaoSoundEvents.ENTITY_EEL_FLOP, "Eel flops");
		EnglishUsLangProvider.addSubtitles(builder, TaiaoSoundEvents.ENTITY_EEL_HURT, "Eel hurts");

		// Damage types
		addDamageType(
			builder,
			TaiaoDamageTypes.HIINAKI,
			"%1$s died in an eel trap",
			null,
			"%1$s died in an eel trap at the hands of %2$s"
		);

		// Stats
		addStat(builder, TaiaoStats.HIINAKI_BAIT_ADDED, "Eel Traps Baited");
		addStat(builder, TaiaoStats.HIINAKI_TRAPPED_ENTITY_HARMED, "Eel Traps Used");
		addStat(builder, TaiaoStats.HIINAKI_TRAPPED_ENTITY_FREED, "Animals Freed from Eel Traps");
	}

	public static void addBannerPatternItem(
		@NotNull TranslationBuilder builder,
		@NotNull Item item,
		String name
	) {
		String translationKey = item.getTranslationKey();

		builder.add(translationKey, "Banner Pattern");
		builder.add(translationKey + ".desc", name);
	}

	public static void addDescription(
		@NotNull TranslationBuilder builder,
		@NotNull ItemConvertible item,
		String descriptionKey,
		String text
	) {
		builder.add(item.asItem().getTranslationKey() + ".desc." + descriptionKey, text);
	}

	public static void addStat(@NotNull TranslationBuilder builder, @NotNull Identifier stat, String name) {
		builder.add(stat.toTranslationKey("stat"), name);
	}

	public static void addDamageType(
		@NotNull TranslationBuilder builder,
		RegistryKey<DamageType> damageType,
		String text,
		@Nullable String itemText,
		@Nullable String playerText
	) {
		String messageId;

		DamageType type = TaiaoDamageTypes.DAMAGE_TYPES.get(damageType);
		if (type != null) {
			messageId = type.msgId();
		} else {
			messageId = damageType.getValue().toShortTranslationKey();
		}

		addDamageType(builder, messageId, text, itemText, playerText);
	}

	public static void addDamageType(
		@NotNull TranslationBuilder builder,
		String messageId,
		String text,
		@Nullable String itemText,
		@Nullable String playerText
	) {
		String translationKey = "death.attack." + messageId;

		builder.add(translationKey, text);
		if (itemText != null) builder.add(translationKey + ".item", itemText);
		if (playerText != null) builder.add(translationKey + ".player", playerText);
	}

	public static void addAdvancement(
		@NotNull TranslationBuilder builder,
		Identifier id,
		@Nullable String title,
		@Nullable String description
	) {
		if (title != null) builder.add(TaiaoAdvancements.titleTranslationKey(id), title);
		if (description != null) builder.add(TaiaoAdvancements.descriptionTranslationKey(id), description);
	}

	public static void addBuiltinResourcePack(
		@NotNull TranslationBuilder builder,
		@NotNull Identifier resourcePackId,
		String name
	) {
		builder.add(
			TaiaoBuiltinResourcePacks.translationKey(resourcePackId),
			String.format("%s %s", Taiao.MOD_NAME_SHORT, name)
		);
	}

	public static void addEmiLootChestLootTable(
		@NotNull TranslationBuilder builder,
		Identifier lootTable,
		String name
	) {
		String translationKey = String.format(
			"%s.%s",
			new Identifier("emi_loot", "chest").toTranslationKey(),
			lootTable
		);

		builder.add(translationKey, name);
	}

	public static void addBannerPattern(
		@NotNull TranslationBuilder builder,
		@NotNull RegistryKey<BannerPattern> pattern,
		String name
	) {
		String translationKey = String.format("block.minecraft.banner.%s", pattern.getValue().toShortTranslationKey());
		builder.add(translationKey, name);

		Function<DyeColor, String> getDyedTranslation = dyeColor -> String.format(
			"%s.%s",
			translationKey,
			dyeColor.getName()
		);

		builder.add(getDyedTranslation.apply(DyeColor.BLACK), String.format("Black %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.BLUE), String.format("Blue %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.BROWN), String.format("Brown %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.CYAN), String.format("Cyan %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.GRAY), String.format("Gray %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.GREEN), String.format("Green %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.LIGHT_BLUE), String.format("Light Blue %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.LIGHT_GRAY), String.format("Light Gray %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.LIME), String.format("Lime %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.MAGENTA), String.format("Magenta %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.ORANGE), String.format("Orange %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.PINK), String.format("Pink %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.PURPLE), String.format("Purple %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.RED), String.format("Red %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.WHITE), String.format("White %s", name));
		builder.add(getDyedTranslation.apply(DyeColor.YELLOW), String.format("Yellow %s", name));
	}

	public static void addBanner(TranslationBuilder builder, @NotNull Banner banner, String name) {
		Text displayName = banner.getCustomName();

		if (displayName != null && displayName.getContent() instanceof TranslatableTextContent translatable) {
			builder.add(translatable.getKey(), name);
		} else {
			Taiao.LOGGER.warn("Tried to add translation for non-translatable banner");
		}
	}

	public static void addSubtitles(@NotNull TranslationBuilder builder, @NotNull SoundEvent soundEvent, String name) {
		builder.add(soundEvent.getId().toTranslationKey("subtitles"), name);
	}

	public static void addBlockAndItemTag(
		TranslationBuilder builder,
		TagKey<Block> blockTag,
		TagKey<Item> itemTag,
		String name
	) {
		addBlockTag(builder, blockTag, name);
		addItemTag(builder, itemTag, name);
	}

	public static void addBlockTag(TranslationBuilder builder, TagKey<Block> blockTag, String name) {
		addTag(builder, blockTag, "block", name);
	}

	public static void addItemTag(TranslationBuilder builder, TagKey<Item> itemTag, String name) {
		addTag(builder, itemTag, "item", name);
	}

	public static <T> void addTag(
		@NotNull TranslationBuilder builder,
		@NotNull TagKey<T> tag,
		String tagType,
		String name
	) {
		builder.add(tag.id().toTranslationKey(String.format("tag.%s", tagType)), name);
	}

	public static void addBiome(@NotNull TranslationBuilder builder, @NotNull RegistryKey<Biome> biome, String name) {
		builder.add(biome.getValue().toTranslationKey("biome"), name);
	}

	public static void addAnimal(
		@NotNull TranslationBuilder builder,
		EntityType<?> entity,
		Item spawnEgg,
		TagKey<Item> foodTag,
		String name
	) {
		builder.add(entity, name);
		builder.add(spawnEgg, String.format("%s Spawn Egg", name));
		if (foodTag != null) addItemTag(builder, foodTag, String.format("%s Food", name));
	}

	public static void addBoat(
		@NotNull TranslationBuilder builder,
		@NotNull RegistryKey<TerraformBoatType> boatKey,
		String name
	) {
		TerraformBoatType boat = TerraformBoatTypeRegistry.INSTANCE.get(boatKey);

		if (boat == null) {
			Taiao.LOGGER.warn("Tried to add translations for non-existent boat type '{}'", boatKey.getValue());

			return;
		}

		if (boat.isRaft()) {
			builder.add(boat.getItem(), String.format("%s Raft", name));
			builder.add(boat.getChestItem(), String.format("%s Raft with Chest", name));
		} else {
			builder.add(boat.getItem(), String.format("%s Boat", name));
			builder.add(boat.getChestItem(), String.format("%s Boat with Chest", name));
		}
	}

	public static void addTreeBlocks(
		TranslationBuilder builder,
		Block sapling,
		Block pottedSapling,
		Block leaves,
		Block log,
		Block strippedLog,
		Block wood,
		Block strippedWood,
		TagKey<Block> blockTag,
		TagKey<Item> itemTag,
		String name
	) {
		addSapling(builder, sapling, pottedSapling, name);
		addLeaves(builder, leaves, name);

		addLogs(builder, log, strippedLog, wood, strippedWood, blockTag, itemTag, name);
	}

	public static void addLogs(
		@NotNull TranslationBuilder builder,
		Block log,
		Block strippedLog,
		Block wood,
		Block strippedWood,
		@NotNull TagKey<Block> blockTag,
		@NotNull TagKey<Item> itemTag,
		String name
	) {
		builder.add(log, String.format("%s Log", name));
		builder.add(strippedLog, String.format("Stripped %s Log", name));

		builder.add(wood, String.format("%s Wood", name));
		builder.add(strippedWood, String.format("Stripped %s Wood", name));

		addBlockAndItemTag(builder, blockTag, itemTag, String.format("%s Logs", name));
	}

	public static void addSapling(@NotNull TranslationBuilder builder, Block sapling, Block potted, String name) {
		builder.add(sapling, String.format("%s Sapling", name));
		builder.add(potted, String.format("Potted %s Sapling", name));
	}

	public static void addLeaves(@NotNull TranslationBuilder builder, Block leaves, String name) {
		builder.add(leaves, String.format("%s Leaves", name));
	}

	public static void addWoodFamily(
		@NotNull TranslationBuilder builder,
		@NotNull BlockFamily family,
		String familyName
	) {
		builder.add(family.getBaseBlock(), String.format("%s Planks", familyName));

		BiConsumer<BlockFamily.Variant, String> addVariant = (variant, variantName) -> {
			Block block = family.getVariant(variant);

			if (block != null) builder.add(block, String.format("%s %s", familyName, variantName));
		};

		addVariant.accept(BlockFamily.Variant.BUTTON, "Button");
		addVariant.accept(BlockFamily.Variant.PRESSURE_PLATE, "Pressure Plate");

		addVariant.accept(BlockFamily.Variant.DOOR, "Door");
		addVariant.accept(BlockFamily.Variant.TRAPDOOR, "Trapdoor");

		addVariant.accept(BlockFamily.Variant.STAIRS, "Stairs");
		addVariant.accept(BlockFamily.Variant.SLAB, "Slab");

		addVariant.accept(BlockFamily.Variant.FENCE, "Fence");
		addVariant.accept(BlockFamily.Variant.CUSTOM_FENCE, "Fence");
		addVariant.accept(BlockFamily.Variant.FENCE_GATE, "Fence Gate");
		addVariant.accept(BlockFamily.Variant.CUSTOM_FENCE_GATE, "Fence Gate");
	}
}
