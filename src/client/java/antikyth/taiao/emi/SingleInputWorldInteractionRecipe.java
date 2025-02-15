// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SingleInputWorldInteractionRecipe implements EmiRecipe {
	protected static final int SLOT_WIDTH = 18;

	protected final Identifier id;
	protected final List<EmiIngredient> inputs;
	protected final List<EmiStack> outputs;

	protected int width = 125;

	public SingleInputWorldInteractionRecipe(
		Identifier id,
		List<EmiIngredient> inputs,
		List<EmiStack> outputs
	) {
		this.id = id;

		this.inputs = inputs;
		this.outputs = outputs;
	}

	@Override
	public EmiRecipeCategory getCategory() {
		return VanillaEmiRecipeCategories.WORLD_INTERACTION;
	}

	@Override
	public @Nullable Identifier getId() {
		return this.id;
	}

	@Override
	public List<EmiIngredient> getInputs() {
		return this.inputs;
	}

	@Override
	public List<EmiStack> getOutputs() {
		return this.outputs;
	}

	@Override
	public int getDisplayWidth() {
		return this.width;
	}

	@SuppressWarnings("SuspiciousNameCombination")
	@Override
	public int getDisplayHeight() {
		return SLOT_WIDTH;
	}

	@Override
	public void addWidgets(@NotNull WidgetHolder widgets) {
		int inputsRightEdge = this.inputs.size() * SLOT_WIDTH;
		int outputsLeftEdge = this.width - (this.outputs.size() * SLOT_WIDTH);

		int center = (inputsRightEdge + outputsLeftEdge) / 2;

		widgets.addTexture(EmiTexture.EMPTY_ARROW, center - (EmiTexture.EMPTY_ARROW.width / 2), 1);

		// Input slots
		for (int i = 0; i < this.inputs.size(); i++) {
			EmiIngredient input = this.inputs.get(i);

			widgets.add(new SlotWidget(input, i * SLOT_WIDTH, 0));
		}

		// Output slots
		for (int i = 0; i < this.outputs.size(); i++) {
			EmiStack output = this.outputs.get(i);

			widgets.add(new SlotWidget(output, outputsLeftEdge + (i * SLOT_WIDTH), 0).recipeContext(this));
		}
	}
}
