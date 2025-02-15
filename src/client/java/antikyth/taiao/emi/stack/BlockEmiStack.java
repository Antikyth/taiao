// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.emi.stack;

import antikyth.taiao.Taiao;
import antikyth.taiao.TaiaoClient;
import dev.emi.emi.api.render.EmiTooltipComponents;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

public abstract class BlockEmiStack extends EmiStack {
	/**
	 * The default block item GUI transformation.
	 */
	protected static final Transformation TRANSFORMATION = new Transformation(
		new Vector3f(30f, 225f, 0f),
		new Vector3f(),
		new Vector3f(0.625f)
	);
	/**
	 * The default block item GUI transformation with a slight rotational offset.
	 */
	protected static final Transformation TRANSFORMATION_OFFSET = new Transformation(
		new Vector3f(30f, 210f, 0f),
		new Vector3f(),
		new Vector3f(0.625f)
	);

	protected final MinecraftClient client = MinecraftClient.getInstance();
	protected final BlockRenderManager blockRenderManager = client.getBlockRenderManager();

	protected final Block block;

	protected List<Property<?>> shownProperties = List.of();
	protected boolean offsetRotation = false;

	/**
	 * @param block the block that all block states in this stack are of
	 */
	protected BlockEmiStack(Block block) {
		this.block = block;
	}

	@Override
	public Object getKey() {
		return this.block;
	}

	@Override
	public Identifier getId() {
		return Registries.BLOCK.getId(this.block);
	}

	@Override
	public Text getName() {
		return this.block.getName();
	}

	@Override
	public List<Text> getTooltipText() {
		return List.of(this.getName());
	}

	@Override
	public NbtCompound getNbt() {
		return null;
	}

	/**
	 * Adds {@link TooltipComponent}s to the stack's tooltip.
	 * <p>
	 * Override to add information specific to this stack.
	 */
	protected void addTooltipComponents(List<TooltipComponent> components) {
	}

	@Override
	public List<TooltipComponent> getTooltip() {
		List<TooltipComponent> components = Lists.newArrayList();

		// Name
		components.add(TooltipComponent.of(this.getName().asOrderedText()));

		this.addTooltipComponents(components);

		// Technical name
		if (this.client.options.advancedItemTooltips) {
			Text technicalName = Text.literal(this.getId().toString()).formatted(Formatting.DARK_GRAY);

			components.add(TooltipComponent.of(technicalName.asOrderedText()));
		}

		EmiTooltipComponents.appendModName(components, Taiao.MOD_ID);

		components.addAll(super.getTooltip());

		return components;
	}

	/**
	 * Returns a list of {@link TooltipComponent}s of the {@link BlockEmiStack#shownProperties} for the given
	 * {@code state}.
	 */
	protected List<TooltipComponent> createPropertyComponents(@NotNull BlockState state) {
		List<TooltipComponent> components = Lists.newArrayList();

		for (Map.Entry<Property<?>, Comparable<?>> entry : state.getEntries().entrySet()) {
			Property<?> property = entry.getKey();
			Comparable<?> value = entry.getValue();

			if (this.shownProperties.contains(property)) {
				components.add(TooltipComponent.of(
					TaiaoClient.propertyText(property, value)
						.formatted(Formatting.GRAY)
						.asOrderedText()
				));
			}
		}

		return components;
	}
}
