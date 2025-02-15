// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.emi;

import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.block.Block;
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
import net.minecraft.util.math.BlockPos;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TallBlockStatesEmiStack extends AbstractBlockStateEmiStack {
	protected final Block block;
	protected final LinkedHashMap<BlockPos, BlockState> states;

	protected Vector3f offset;
	@Nullable
	protected BlockState describeSingleState = null;

	protected float scale = 1f;

	public TallBlockStatesEmiStack(
		Block block,
		LinkedHashMap<BlockPos, BlockState> states
	) {
		this(block, states, new Vector3f());

		// Determine 'center of mass'
		BlockPos.Mutable sum = new BlockPos.Mutable();

		for (BlockPos pos : states.keySet()) {
			sum.set(sum, pos);
		}

		this.offset = new Vector3f(
			-(float) sum.getX() / (float) states.size(),
			-(float) sum.getY() / (float) states.size(),
			-(float) sum.getZ() / (float) states.size()
		);
	}

	public TallBlockStatesEmiStack(Block block, LinkedHashMap<BlockPos, BlockState> states, Vector3f offset) {
		this.block = block;
		this.states = states;
		this.offset = offset;
	}

	public TallBlockStatesEmiStack scale(float scale) {
		this.scale = scale;
		return this;
	}

	/**
	 * Whether to offset the rotation slightly (useful for making cross models look less weird).
	 */
	public TallBlockStatesEmiStack offsetRotation(boolean offsetRotation) {
		this.offsetRotation = offsetRotation;
		return this;
	}

	/**
	 * A single {@link BlockState} to describe in the tooltip.
	 * <p>
	 * If {@code null} (the default), all states will be described.
	 */
	public TallBlockStatesEmiStack describeSingleState(@Nullable BlockState state) {
		this.describeSingleState = state;
		return this;
	}

	/**
	 * {@link BlockState} properties to show in the tooltip.
	 */
	public TallBlockStatesEmiStack showProperties(Property<?>... shownProperties) {
		return this.showProperties(List.of(shownProperties));
	}

	/**
	 * {@link BlockState} properties to show in the tooltip.
	 */
	public TallBlockStatesEmiStack showProperties(List<Property<?>> shownProperties) {
		this.shownProperties = shownProperties;
		return this;
	}

	@Override
	public EmiStack copy() {
		return new TallBlockStatesEmiStack(this.block, this.states, this.offset)
			.scale(this.scale)
			.offsetRotation(this.offsetRotation)
			.describeSingleState(this.describeSingleState)
			.showProperties(this.shownProperties);
	}

	@Override
	public boolean isEmpty() {
		return this.states.isEmpty();
	}

	@Override
	public NbtCompound getNbt() {
		return null;
	}

	@Override
	public Object getKey() {
		return this.block;
	}

	@Override
	public Text getName() {
		return this.block.getName();
	}

	@Override
	public Identifier getId() {
		return Registries.BLOCK.getId(this.block);
	}

	@Override
	public List<Text> getTooltipText() {
		return List.of(this.getName());
	}

	@Override
	public List<TooltipComponent> getTooltip() {
		List<TooltipComponent> list = Lists.newArrayList();

		if (!this.isEmpty()) {
			// Name
			list.add(TooltipComponent.of(this.getName().asOrderedText()));
			// Technical name
			if (this.client.options.advancedItemTooltips) {
				Text technicalName = Text.literal(this.getId().toString()).formatted(Formatting.DARK_GRAY);

				list.add(TooltipComponent.of(technicalName.asOrderedText()));
			}

			if (this.describeSingleState != null) {
				List<Text> propertyTexts = this.createPropertyTexts(this.describeSingleState);

				for (Text text : propertyTexts) {
					list.add(TooltipComponent.of(text.asOrderedText()));
				}
			} else {
				// Add info for each state
				for (Map.Entry<BlockPos, BlockState> part : this.states.entrySet()) {
					BlockPos pos = part.getKey();
					BlockState state = part.getValue();

					List<Text> propertyTexts = this.createPropertyTexts(state);

					if (!propertyTexts.isEmpty()) {
						list.add(TooltipComponent.of(Text.empty().asOrderedText()));

						// Position text
						list.add(TooltipComponent.of(Text.literal(pos.toShortString() + ":")
							.formatted(Formatting.DARK_GRAY)
							.asOrderedText()));

						for (Text propertyText : propertyTexts) {
							list.add(TooltipComponent.of(propertyText.asOrderedText()));
						}
					}
				}
			}

			list.addAll(super.getTooltip());
		}

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

		// Set up transformations for the blocks themselves
		transformation.apply(false, matrices);
		matrices.scale(this.scale, this.scale, this.scale);
		matrices.translate(-0.5f, -0.5f, -0.5f);

		matrices.translate(this.offset.x, this.offset.y, this.offset.z);

		// Render all blocks
		for (Map.Entry<BlockPos, BlockState> entry : this.states.entrySet()) {
			BlockPos pos = entry.getKey();
			BlockState state = entry.getValue();

			matrices.push();

			// Render block
			matrices.translate(pos.getX(), pos.getY(), pos.getZ());
			this.blockRenderManager.renderBlockAsEntity(
				state,
				matrices,
				vertices,
				LightmapTextureManager.MAX_LIGHT_COORDINATE,
				OverlayTexture.DEFAULT_UV
			);

			matrices.pop();
		}

		matrices.pop();

		draw.draw();
	}
}
