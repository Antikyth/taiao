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
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class TaiaoRecipeGenerator extends FabricRecipeProvider {
    public TaiaoRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        // Cabbage tree oak planks
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.OAK_PLANKS, 2)
                .group("planks")
                .input(TaiaoItemTags.CABBAGE_TREE_LOGS)
                .criterion(
                        FabricRecipeProvider.hasItem(TaiaoBlocks.CABBAGE_TREE_LOG.asItem()),
                        FabricRecipeProvider.conditionsFromTag(TaiaoItemTags.CABBAGE_TREE_LOGS)
                )
                .offerTo(exporter);
        // Cabbage tree oak hanging sign - makes 2 as the logs are thinner than vanilla
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.OAK_HANGING_SIGN, 2)
                .group("hanging_sign")
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .input('X', Items.CHAIN)
                .input('#', TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG.asItem())
                .criterion(
                        FabricRecipeProvider.hasItem(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG.asItem()),
                        FabricRecipeProvider.conditionsFromItem(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG.asItem())
                )
                .offerTo(exporter);

        // Cabbage tree wood
        createWoodRecipe(exporter, TaiaoBlocks.CABBAGE_TREE_LOG.asItem(), TaiaoBlocks.CABBAGE_TREE_WOOD.asItem());
        createWoodRecipe(
                exporter,
                TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG.asItem(),
                TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD.asItem()
        );
    }

    public static void createWoodRecipe(Consumer<RecipeJsonProvider> exporter, Item log, Item wood) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, wood, 3)
                .group("bark")
                .pattern("##")
                .pattern("##")
                .input('#', log)
                .criterion(FabricRecipeProvider.hasItem(log), FabricRecipeProvider.conditionsFromItem(log))
                .offerTo(exporter);
    }
}
