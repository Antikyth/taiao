// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.emi;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.log.Strippable;
import antikyth.taiao.block.plant.HarvestableTripleTallPlantBlock;
import antikyth.taiao.block.plant.TripleBlockPart;
import antikyth.taiao.block.plant.TripleTallPlantBlock;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class TaiaoEmiPlugin implements EmiPlugin {
	@Override
	public void register(@NotNull EmiRegistry registry) {
		Taiao.LOGGER.debug("Registering EMI recipes");

		// Harvesting recipes
		registry.addRecipe(triplePlantHarvestRecipe(TaiaoBlocks.HARAKEKE, EmiStack.of(TaiaoBlocks.HARAKEKE, 3)));

		// Stripping recipes
		for (Map.Entry<Block, Block> entry : Strippable.STRIPPED_BLOCKS.get().entrySet()) {
			registry.addRecipe(strippingRecipe(entry.getKey(), entry.getValue()));
		}
	}

	/**
	 * Creates a {@linkplain EmiWorldInteractionRecipe world interaction recipe} for shearing a
	 * {@link HarvestableTripleTallPlantBlock}.
	 *
	 * @param output the itemStack dropped after harvesting
	 */
	protected static EmiRecipe triplePlantHarvestRecipe(Block block, EmiStack output) {
		Identifier recipeId = Taiao.id("/world/shearing/" + Taiao.toPath(Registries.BLOCK.getId(block)));

		BlockState unharvestedState = block.getDefaultState().with(HarvestableTripleTallPlantBlock.HARVESTED, false);
		BlockState harvestedState = block.getDefaultState().with(HarvestableTripleTallPlantBlock.HARVESTED, true);

		TallBlockEmiStack unharvestedStack = tripleTallPlantStack(block, unharvestedState);
		TallBlockEmiStack harvestedStack = tripleTallPlantStack(block, harvestedState);

		EmiIngredient shears = damaged(EmiIngredient.of(ConventionalItemTags.SHEARS), 1);

		return EmiWorldInteractionRecipe.builder()
			.id(recipeId)
			.leftInput(unharvestedStack)
			.rightInput(shears, true)
			.output(output)
			.output(harvestedStack)
			.build();
	}

	/**
	 * Creates a {@linkplain EmiWorldInteractionRecipe world interaction recipe} for stripping the {@code unstripped}
	 * block into the {@code stripped} one.
	 * <p>
	 * This exists to support the non-standard stripping recipes added by {@link Strippable}.
	 */
	protected static EmiRecipe strippingRecipe(Block unstripped, Block stripped) {
		Identifier recipeId = Taiao.id("/world/stripping/" + Taiao.toPath(Registries.BLOCK.getId(unstripped)));

		EmiIngredient axes = damaged(EmiIngredient.of(ItemTags.AXES), 1);

		return EmiWorldInteractionRecipe.builder()
			.id(recipeId)
			.leftInput(EmiStack.of(unstripped))
			.rightInput(axes, true)
			.output(EmiStack.of(stripped))
			.build();
	}

	/**
	 * Adds the given amount of {@code damage} to all {@link ItemStack}s in the given {@code ingredient}.
	 *
	 * @return the {@code ingredient} after {@code damage} is applied
	 */
	@Contract("_, _ -> param1")
	protected static EmiIngredient damaged(@NotNull EmiIngredient ingredient, int damage) {
		// Add damage
		for (EmiStack stack : ingredient.getEmiStacks()) {
			ItemStack itemStack = stack.getItemStack().copy();
			itemStack.setDamage(itemStack.getDamage() + damage);

			stack.setRemainder(EmiStack.of(itemStack));
		}

		return ingredient;
	}

	/**
	 * Creates a {@link TallBlockEmiStack} for a {@link TripleTallPlantBlock}.
	 *
	 * @param block     the {@link TripleTallPlantBlock}
	 * @param baseState the {@link BlockState} to base each {@linkplain TripleTallPlantBlock#PART part} on
	 */
	@Contract("_, _ -> new")
	protected static TallBlockEmiStack tripleTallPlantStack(Block block, @NotNull BlockState baseState) {
		return new TallBlockEmiStack(
			block,
			List.of(
				new Pair<>(new BlockPos(0, 0, 0), baseState.with(TripleTallPlantBlock.PART, TripleBlockPart.LOWER)),
				new Pair<>(new BlockPos(0, 1, 0), baseState.with(TripleTallPlantBlock.PART, TripleBlockPart.MIDDLE)),
				new Pair<>(new BlockPos(0, 2, 0), baseState.with(TripleTallPlantBlock.PART, TripleBlockPart.UPPER))
			)
		)
			.center(new BlockPos(0, 1, 0))
			.scale(0.5f)
			.describeAllStates(false)
			.hiddenProperties(TripleTallPlantBlock.PART);
	}
}
