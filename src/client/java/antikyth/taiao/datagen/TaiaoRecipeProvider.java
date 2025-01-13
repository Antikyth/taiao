// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.item.TaiaoItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Consumer;

public class TaiaoRecipeProvider extends FabricRecipeProvider {
    public TaiaoRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        // Kauri planks
        createPlanksRecipe(exporter, TaiaoItemTags.KAURI_LOGS, TaiaoBlocks.KAURI_LOG, TaiaoBlocks.KAURI_PLANKS, 4);
        // Kauri wood
        createWoodRecipe(exporter, TaiaoBlocks.KAURI_LOG, TaiaoBlocks.KAURI_WOOD);
        createWoodRecipe(exporter, TaiaoBlocks.STRIPPED_KAURI_LOG, TaiaoBlocks.STRIPPED_KAURI_WOOD);

        // Tī kōuka oak planks - makes 2 as the logs are thinner than vanilla
        createPlanksRecipe(
                exporter,
                TaiaoItemTags.CABBAGE_TREE_LOGS,
                TaiaoBlocks.CABBAGE_TREE_LOG,
                Items.OAK_PLANKS,
                2
        );
        // Tī kōuka wood
        createWoodRecipe(exporter, TaiaoBlocks.CABBAGE_TREE_LOG, TaiaoBlocks.CABBAGE_TREE_WOOD.asItem());
        createWoodRecipe(exporter, TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG, TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD);

        // Tī kōuka oak hanging sign - makes 2 as the logs are thinner than vanilla
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.OAK_HANGING_SIGN, 2)
                .group("hanging_sign")
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .input('X', Items.CHAIN)
                .input('#', TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG)
                .criterion(
                        FabricRecipeProvider.hasItem(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG),
                        FabricRecipeProvider.conditionsFromItem(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG)
                )
                .offerTo(exporter);
    }

    public static void createPlanksRecipe(
            Consumer<RecipeJsonProvider> exporter,
            TagKey<Item> logs,
            ItemConvertible defaultLog,
            ItemConvertible planks,
            int count
    ) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, planks, count)
                .group("planks")
                .input(logs)
                .criterion(FabricRecipeProvider.hasItem(defaultLog), FabricRecipeProvider.conditionsFromTag(logs))
                .offerTo(exporter);
    }

    public static void createWoodRecipe(
            Consumer<RecipeJsonProvider> exporter,
            ItemConvertible log,
            ItemConvertible wood
    ) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, wood, 3)
                .group("bark")
                .pattern("##")
                .pattern("##")
                .input('#', log)
                .criterion(FabricRecipeProvider.hasItem(log), FabricRecipeProvider.conditionsFromItem(log))
                .offerTo(exporter);
    }
}
