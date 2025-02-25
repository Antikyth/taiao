// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.boat.TaiaoBoats;
import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.item.TaiaoItems;
import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TaiaoRecipeProvider extends FabricRecipeProvider {
	public TaiaoRecipeProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		offerHiinakiRecipe(exporter, TaiaoItemTags.FERNS);
		offerHiinakiRecipe(exporter, TaiaoItemTags.CONVENTIONAL_VINES);

		// Kete
		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, TaiaoItems.KETE)
			.input('#', TaiaoBlocks.HARAKEKE)
			.pattern(" # ")
			.pattern("# #")
			.pattern("###")
			.criterion("has_harakeke", conditionsFromItem(TaiaoBlocks.HARAKEKE))
			.offerTo(exporter);

		generateFamily(exporter, TaiaoBlocks.WoodFamily.KAURI.getBlockFamily());
		// Kauri planks
		offerPlanksRecipe(exporter, TaiaoBlocks.KAURI_PLANKS, TaiaoItemTags.KAURI_LOGS, 4);
		// Kauri wood
		offerBarkBlockRecipe(exporter, TaiaoBlocks.KAURI_WOOD, TaiaoBlocks.KAURI_LOG);
		offerBarkBlockRecipe(exporter, TaiaoBlocks.STRIPPED_KAURI_WOOD, TaiaoBlocks.STRIPPED_KAURI_LOG);
		// Kauri boats
		offerBoatRecipes(exporter, TaiaoBoats.KAURI);

		generateFamily(exporter, TaiaoBlocks.WoodFamily.KAHIKATEA.getBlockFamily());
		// Kahikatea planks
		offerPlanksRecipe(exporter, TaiaoBlocks.KAHIKATEA_PLANKS, TaiaoItemTags.KAHIKATEA_LOGS, 4);
		// Kahikatea wood
		offerBarkBlockRecipe(exporter, TaiaoBlocks.KAHIKATEA_WOOD, TaiaoBlocks.KAHIKATEA_LOG);
		offerBarkBlockRecipe(exporter, TaiaoBlocks.STRIPPED_KAHIKATEA_WOOD, TaiaoBlocks.STRIPPED_KAHIKATEA_LOG);
		// Kahikatea boats
		offerBoatRecipes(exporter, TaiaoBoats.KAHIKATEA);

		generateFamily(exporter, TaiaoBlocks.WoodFamily.RIMU.getBlockFamily());
		// Rimu planks
		offerPlanksRecipe(exporter, TaiaoBlocks.RIMU_PLANKS, TaiaoItemTags.RIMU_LOGS, 4);
		// Rimu wood
		offerBarkBlockRecipe(exporter, TaiaoBlocks.RIMU_WOOD, TaiaoBlocks.RIMU_LOG);
		offerBarkBlockRecipe(exporter, TaiaoBlocks.STRIPPED_RIMU_WOOD, TaiaoBlocks.STRIPPED_RIMU_LOG);
		// Rimu boats
		offerBoatRecipes(exporter, TaiaoBoats.RIMU);
		// Chiseled rimu wood
		offerChiseledBlockRecipe(
			exporter,
			RecipeCategory.BUILDING_BLOCKS,
			TaiaoBlocks.STRIPPED_RIMU_LOG,
			TaiaoBlocks.CHISELED_STRIPPED_RIMU_LOG,
			2
		);
		offerChiseledBlockRecipe(
			exporter,
			RecipeCategory.BUILDING_BLOCKS,
			TaiaoBlocks.STRIPPED_RIMU_WOOD,
			TaiaoBlocks.CHISELED_STRIPPED_RIMU_WOOD,
			2
		);
		offerBarkBlockRecipe(
			exporter,
			TaiaoBlocks.CHISELED_STRIPPED_RIMU_WOOD,
			TaiaoBlocks.CHISELED_STRIPPED_RIMU_LOG,
			Taiao.id("chiseled_stripped_rimu_wood_bark")
		);

		// Tī kōuka oak planks - makes 2 as the logs are thinner than vanilla
		offerPlanksRecipe(exporter, Blocks.OAK_PLANKS, TaiaoItemTags.CABBAGE_TREE_LOGS, 2);
		// Tī kōuka wood
		offerBarkBlockRecipe(exporter, TaiaoBlocks.CABBAGE_TREE_WOOD, TaiaoBlocks.CABBAGE_TREE_LOG);
		offerBarkBlockRecipe(exporter, TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD, TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG);

		// Tī kōuka oak hanging sign
		offerHangingSignRecipe(exporter, Items.OAK_HANGING_SIGN, TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG);

		generateFamily(exporter, TaiaoBlocks.WoodFamily.MAMAKU.getBlockFamily());
		// Mamaku planks
		offerPlanksRecipe(exporter, TaiaoBlocks.MAMAKU_PLANKS, TaiaoItemTags.MAMAKU_LOGS, 2);
		// Mamaku wood
		offerBarkBlockRecipe(exporter, TaiaoBlocks.MAMAKU_WOOD, TaiaoBlocks.MAMAKU_LOG);
		offerBarkBlockRecipe(exporter, TaiaoBlocks.STRIPPED_MAMAKU_WOOD, TaiaoBlocks.STRIPPED_MAMAKU_LOG);
		// Mamaku rafts
		offerBoatRecipes(exporter, TaiaoBoats.MAMAKU);

		// Whekī ponga wood
		offerBarkBlockRecipe(exporter, TaiaoBlocks.WHEKII_PONGA_WOOD, TaiaoBlocks.WHEKII_PONGA_LOG);
		offerBarkBlockRecipe(exporter, TaiaoBlocks.STRIPPED_WHEKII_PONGA_WOOD, TaiaoBlocks.STRIPPED_WHEKII_PONGA_LOG);

		// Harakeke
		offerCarpetRecipe(exporter, TaiaoBlocks.HARAKEKE_MAT, TaiaoBlocks.HARAKEKE);

		createStairsRecipe(TaiaoBlocks.THATCH_ROOF, Ingredient.ofItems(TaiaoBlocks.RAUPOO))
			.criterion("has_raupoo", conditionsFromItem(TaiaoBlocks.RAUPOO))
			.offerTo(exporter);
		createSlabRecipe(
			RecipeCategory.BUILDING_BLOCKS,
			TaiaoBlocks.THATCH_ROOF_TOP,
			Ingredient.ofItems(TaiaoBlocks.RAUPOO)
		)
			.criterion("has_raupoo", conditionsFromItem(TaiaoBlocks.RAUPOO))
			.offerTo(exporter);

		// Cooking
		generateCookingRecipes(exporter);
	}

	public static void generateCookingRecipes(Consumer<RecipeJsonProvider> exporter) {
		float xp = 0.35f;

		offerFoodCookingRecipe(exporter, TaiaoItems.EEL, TaiaoItems.COOKED_EEL, xp);
	}

	public static void offerFoodCookingRecipe(
		Consumer<RecipeJsonProvider> exporter,
		ItemConvertible input,
		ItemConvertible output,
		float xp
	) {
		CookingRecipeJsonBuilder.createSmoking(Ingredient.ofItems(input), RecipeCategory.FOOD, output, xp, 100)
			.criterion(hasItem(input), conditionsFromItem(input))
			.offerTo(exporter, getItemPath(output) + "_from_smoking");
		CookingRecipeJsonBuilder.createCampfireCooking(Ingredient.ofItems(input), RecipeCategory.FOOD, output, xp, 600)
			.criterion(hasItem(input), conditionsFromItem(input))
			.offerTo(exporter, getItemPath(output) + "_from_campfire_cooking");
		CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(input), RecipeCategory.FOOD, output, xp, 200)
			.criterion(hasItem(input), conditionsFromItem(input))
			.offerTo(exporter);
	}

	public static void offerHiinakiRecipe(Consumer<RecipeJsonProvider> exporter, @NotNull TagKey<Item> inputs) {
		ItemConvertible output = TaiaoBlocks.HIINAKI;
		Identifier itemId = Registries.ITEM.getId(output.asItem());
		Identifier tagId = inputs.id();
		Identifier recipeId = itemId.withPath(path -> path + "_" + tagId.toUnderscoreSeparatedString());

		ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output)
			.input('#', inputs)
			.pattern("###")
			.pattern("# #")
			.pattern("###")
			.criterion("has_" + tagId.getPath(), conditionsFromTag(inputs))
			.offerTo(exporter, recipeId);
	}

	public static void offerChiseledBlockRecipe(
		Consumer<RecipeJsonProvider> exporter,
		RecipeCategory category,
		ItemConvertible input,
		ItemConvertible output,
		int count
	) {
		ShapedRecipeJsonBuilder.create(category, output, count)
			.input('#', input)
			.pattern("#")
			.pattern("#")
			.criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
	}

	public static void offerBoatRecipes(
		Consumer<RecipeJsonProvider> exporter,
		@NotNull RegistryKey<TerraformBoatType> boatType
	) {
		TerraformBoatType boat = TerraformBoatTypeRegistry.INSTANCE.get(boatType);

		if (boat != null) {
			offerBoatRecipe(exporter, boat.getItem(), boat.getPlanks());
			offerChestBoatRecipe(exporter, boat.getChestItem(), boat.getItem());
		} else {
			Taiao.LOGGER.warn(
				"Boat type '{}' was not registered when generating recipes; skipping",
				boatType.getValue()
			);
		}
	}

	public static void offerBarkBlockRecipe(
		Consumer<RecipeJsonProvider> exporter,
		ItemConvertible output,
		ItemConvertible input,
		Identifier recipeId
	) {
		ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 3)
			.input('#', input)
			.pattern("##")
			.pattern("##")
			.group("bark")
			.criterion("has_log", conditionsFromItem(input))
			.offerTo(exporter, recipeId);
	}
}
