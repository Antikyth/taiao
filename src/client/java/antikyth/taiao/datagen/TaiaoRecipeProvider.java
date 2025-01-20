// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.item.TaiaoItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class TaiaoRecipeProvider extends FabricRecipeProvider {
    public TaiaoRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        generateFamily(exporter, TaiaoBlocks.WoodFamily.KAURI.getBlockFamily());

        // Kauri planks
        offerPlanksRecipe(exporter, TaiaoBlocks.KAURI_PLANKS, TaiaoItemTags.KAURI_LOGS, 4);
        // Kauri wood
        offerBarkBlockRecipe(exporter, TaiaoBlocks.KAURI_WOOD, TaiaoBlocks.KAURI_LOG);
        offerBarkBlockRecipe(exporter, TaiaoBlocks.STRIPPED_KAURI_WOOD, TaiaoBlocks.STRIPPED_KAURI_LOG);

        // Tī kōuka oak planks - makes 2 as the logs are thinner than vanilla
        offerPlanksRecipe(exporter, Blocks.OAK_PLANKS, TaiaoItemTags.CABBAGE_TREE_LOGS, 2);
        // Tī kōuka wood
        offerBarkBlockRecipe(exporter, TaiaoBlocks.CABBAGE_TREE_WOOD, TaiaoBlocks.CABBAGE_TREE_LOG);
        offerBarkBlockRecipe(exporter, TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD, TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG);

        // Tī kōuka oak hanging sign
        offerHangingSignRecipe(exporter, Items.OAK_HANGING_SIGN, TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG);

        // Mamaku wood
        offerBarkBlockRecipe(exporter, TaiaoBlocks.MAMAKU_WOOD, TaiaoBlocks.MAMAKU_LOG);
    }
}
