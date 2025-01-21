// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

import antikyth.taiao.block.Strippable;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class TaiaoEmiPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry emiRegistry) {
        EmiIngredient axes = damagedTool(
                getPreferredTag(
                        List.of("minecraft:axes", "c:axes", "c:tools/axes", "fabric:axes", "forge:tools/axes"),
                        EmiStack.of(Items.IRON_AXE)
                ), 1
        );

        Taiao.LOGGER.debug("Registering custom stripping recipes for EMI");

        // Register stripping recipes
        for (Map.Entry<Block, Block> entry : Strippable.STRIPPED_BLOCKS.get().entrySet()) {
            try {
                Identifier unstrippedId = Registries.BLOCK.getKey(entry.getKey()).orElseThrow().getValue();
                Identifier recipeId = new Identifier(
                        "emi",
                        "/world/stripping/" + unstrippedId.getNamespace() + "/" + unstrippedId.getPath()
                );

                // Add world interaction recipe
                emiRegistry.addRecipe(basicWorldRecipe(
                        EmiStack.of(entry.getKey()),
                        axes,
                        EmiStack.of(entry.getValue()),
                        recipeId
                ));
            } catch (Exception e) {
                Taiao.LOGGER.warn("Exception thrown while parsing EMI recipe (no ID available)");
                Taiao.LOGGER.error(e.toString());
            }
        }
    }

    private static EmiRecipe basicWorldRecipe(EmiIngredient left, EmiIngredient right, EmiStack output, Identifier id) {
        return basicWorldRecipe(left, right, output, id, true);
    }

    private static EmiRecipe basicWorldRecipe(
            EmiIngredient left,
            EmiIngredient right,
            EmiStack output,
            Identifier id,
            boolean catalyst
    ) {
        return EmiWorldInteractionRecipe.builder()
                .id(id)
                .leftInput(left)
                .rightInput(right, catalyst)
                .output(output)
                .build();
    }

    @Contract("_, _ -> param1")
    private static EmiIngredient damagedTool(@NotNull EmiIngredient tool, int damage) {
        // Add damage
        for (EmiStack emiStack : tool.getEmiStacks()) {
            ItemStack stack = emiStack.getItemStack().copy();
            stack.setDamage(damage);

            emiStack.setRemainder(EmiStack.of(stack));
        }

        return tool;
    }

    private static EmiIngredient getPreferredTag(@NotNull List<String> candidates, EmiIngredient fallback) {
        for (String id : candidates) {
            EmiIngredient potential = EmiIngredient.of(TagKey.of(RegistryKeys.ITEM, new Identifier(id)));

            if (!potential.isEmpty()) return potential;
        }

        return fallback;
    }
}
