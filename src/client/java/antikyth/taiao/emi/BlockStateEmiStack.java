// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.emi;

import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.List;

public class BlockStateEmiStack extends AbstractBlockStateEmiStack {
	protected final BlockState state;

	public BlockStateEmiStack(BlockState state) {
		this.state = state;
	}

	/**
	 * Whether to offset the rotation slightly (useful for making cross models look less weird).
	 */
	public BlockStateEmiStack offsetRotation(boolean offsetRotation) {
		this.offsetRotation = offsetRotation;
		return this;
	}

	/**
	 * {@link BlockState} properties to show in the tooltip.
	 */
	public BlockStateEmiStack showProperties(Property<?>... shownProperties) {
		return this.showProperties(List.of(shownProperties));
	}

	/**
	 * {@link BlockState} properties to show in the tooltip.
	 */
	public BlockStateEmiStack showProperties(List<Property<?>> shownProperties) {
		this.shownProperties = shownProperties;
		return this;
	}

	@Override
	public EmiStack copy() {
		return new BlockStateEmiStack(this.state)
			.offsetRotation(this.offsetRotation)
			.showProperties(this.shownProperties);
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public NbtCompound getNbt() {
		return null;
	}

	@Override
	public Object getKey() {
		return this.state;
	}

	@Override
	public Identifier getId() {
		return Registries.BLOCK.getId(this.state.getBlock());
	}

	@Override
	public Text getName() {
		return this.state.getBlock().getName();
	}

	@Override
	public List<Text> getTooltipText() {
		return List.of(this.getName());
	}

	@Override
	public List<TooltipComponent> getTooltip() {
		List<TooltipComponent> list = Lists.newArrayList();

		// Name
		list.add(TooltipComponent.of(this.getName().asOrderedText()));
		// Technical name
		if (this.client.options.advancedItemTooltips) {
			Text technicalName = Text.literal(this.getId().toString()).formatted(Formatting.DARK_GRAY);

			list.add(TooltipComponent.of(technicalName.asOrderedText()));
		}

		// Property descriptions
		for (Text text : this.createPropertyTexts(this.state)) {
			list.add(TooltipComponent.of(text.asOrderedText()));
		}

		list.addAll(super.getTooltip());

		return list;
	}

	@Override
	public void render(@NotNull DrawContext draw, int x, int y, float delta, int flags) {
		MatrixStack matrices = draw.getMatrices();
		VertexConsumerProvider vertices = draw.getVertexConsumers();

		matrices.push();

		// Transforms, the same applied to items when rendering in GUIs
		matrices.translate(x + 8f, y + 8f, 150);
		matrices.multiplyPositionMatrix(new Matrix4f().scaling(1f, -1f, 1f));
		matrices.scale(16f, 16f, 16f);

		Transformation transformation = this.offsetRotation ? TRANSFORMATION_OFFSET : TRANSFORMATION;

		// Set up transformations for the block itself
		transformation.apply(false, matrices);
		matrices.translate(-0.5f, -0.5f, -0.5f);

		// Render block
		this.blockRenderManager.renderBlockAsEntity(
			this.state,
			matrices,
			vertices,
			LightmapTextureManager.MAX_LIGHT_COORDINATE,
			OverlayTexture.DEFAULT_UV
		);

		matrices.pop();

		draw.draw();
	}
}
