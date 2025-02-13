// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.language;

import antikyth.taiao.Taiao;
import antikyth.taiao.TaiaoBuiltinResourcePacks;
import antikyth.taiao.TriConsumer;
import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.item.*;
import antikyth.taiao.sound.TaiaoSoundEvents;
import antikyth.taiao.world.gen.biome.TaiaoBiomes;
import antikyth.taiao.world.gen.loot.TaiaoLootTables;
import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

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
		// Plants
		builder.add(TaiaoBlocks.GIANT_CANE_RUSH, "Giant Cane Rush");
		builder.add(TaiaoBlocks.RAUPOO, "Raupō");
		builder.add(TaiaoBlocks.HARAKEKE, "Harakeke");

		// Items
		builder.add(TaiaoItems.CONIFER_FRUIT, "Huarākau");

		// Animals
		addAnimal(builder, TaiaoEntities.KIWI, TaiaoItems.KIWI_SPAWN_EGG, TaiaoItemTags.KIWI_FOOD, "Kiwi");
		addAnimal(builder, TaiaoEntities.PUUKEKO, TaiaoItems.PUUKEKO_SPAWN_EGG, TaiaoItemTags.PUUKEKO_FOOD, "Pūkeko");
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

		// Biomes
		addBiome(builder, TaiaoBiomes.NATIVE_FOREST, "Aotearoa Native Forest");
		addBiome(builder, TaiaoBiomes.KAHIKATEA_SWAMP, "Aotearoa Kahikatea Swamp");

		// Block tags
		addBlockAndItemTag(builder, TaiaoBlockTags.THIN_LOGS, TaiaoItemTags.THIN_LOGS, "Thin Logs");
		addBlockTag(builder, TaiaoBlockTags.THIN_LOG_CONNECTION_OVERRIDE, "Thin Logs Connection Override");
		addBlockTag(builder, TaiaoBlockTags.DIRECTIONAL_LEAVES, "Directional Leaves");

		// Conventional tags
		addItemTag(builder, TaiaoItemTags.CONVENTIONAL_BERRIES, "Berries");
		addItemTag(builder, TaiaoItemTags.CONVENTIONAL_VINES, "Vines");

		// Subtitles
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_KIWI_CHIRP, "Kiwi chirps");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_PUUKEKO_BABY_AMBIENT, "Baby pūkeko squawks");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_PUUKEKO_ADULT_AMBIENT, "Pūkeko squawks");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_KAAKAAPOO_CHING, "Kākāpō chings");
		addSubtitles(builder, TaiaoSoundEvents.ENTITY_KAAKAAPOO_BOOM, "Kākāpō booms");

		// Tukutuku
		addItemStack(builder, TaiaoBannerPatterns.POUTAMA_TUKUTUKU_LEFT, "Poutama Tukutuku Left");
		addItemStack(builder, TaiaoBannerPatterns.POUTAMA_TUKUTUKU_RIGHT, "Poutama Tukutuku Right");

		addItemStack(builder, TaiaoBannerPatterns.PAATIKI_TUKUTUKU, "Pātiki Tukutuku");
		addItemStack(builder, TaiaoBannerPatterns.PAATIKI_TUKUTUKU_TOP, "Pātiki Tukutuku Top");
		addItemStack(builder, TaiaoBannerPatterns.PAATIKI_TUKUTUKU_BOTTOM, "Pātiki Tukutuku Bottom");

		addItemStack(builder, TaiaoBannerPatterns.KAOKAO_TUKUTUKU, "Kaokao Tukutuku");

		// Banner patterns
		addBannerPatterns(builder, EnglishUsLangProvider::addBannerPattern);

		// Chest loot tables
		addEmiLootChestLootTable(builder, TaiaoLootTables.VILLAGE_MARAE_HOUSE_CHEST, "Marae Whare Chest");
		addEmiLootChestLootTable(builder, TaiaoLootTables.VILLAGE_MARAE_PAATAKA_KAI_CHEST, "Marae Pātaka Kai Chest");
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

	public static void addItemStack(TranslationBuilder builder, @NotNull ItemStack stack, String name) {
		Text displayName = stack.getName();

		if (displayName.getContent() instanceof TranslatableTextContent translatable) {
			builder.add(translatable.getKey(), name);
		} else {
			Taiao.LOGGER.warn("Tried to add translation for non-translatable item stack '{}'", displayName.getString());
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
		addItemTag(builder, foodTag, String.format("%s Food", name));
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
