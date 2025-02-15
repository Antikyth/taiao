// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.emi.stack;

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
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TallBlockStatesEmiStack extends BlockEmiStack {
	protected final LinkedHashMap<BlockPos, BlockState> states;

	protected Vector3f offset;
	@Nullable
	protected BlockState describeSingleState = null;

	protected float scale = 1f;

	/**
	 * Creates a {@link TallBlockStatesEmiStack} of the given {@code block}.
	 * <p>
	 * All {@code states} must be of the given {@code block}; providing any {@link BlockState}s not of the same
	 * {@code block} is undefined behavior.
	 * <p>
	 * Block positions are automatically offset based on the center of all positions. To prevent this or provide a
	 * custom offset, use {@link TallBlockStatesEmiStack#TallBlockStatesEmiStack(Block, LinkedHashMap, Vector3f)}.
	 * <p>
	 * For a single {@link BlockState}, see {@link BlockStateEmiStack}.
	 *
	 * @param states a map of positions relative to the {@link BlockPos#ORIGIN} to the corresponding {@link BlockState}
	 *               at that position
	 */
	public TallBlockStatesEmiStack(
		Block block,
		@NotNull LinkedHashMap<BlockPos, BlockState> states
	) {
		super(block);

		this.states = states;

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

	/**
	 * Creates a {@link TallBlockStatesEmiStack} of the given {@code block}.
	 * <p>
	 * All {@code states} must be of the given {@code block}; providing any {@link BlockState}s not of the same
	 * {@code block} is undefined behavior.
	 * <p>
	 * To have an offset automatically determined to center the states, use
	 * {@link TallBlockStatesEmiStack#TallBlockStatesEmiStack(Block, LinkedHashMap)}.
	 * <p>
	 * For a single {@link BlockState}, see {@link BlockStateEmiStack}.
	 *
	 * @param states a map of positions relative to the {@link BlockPos#ORIGIN} to the corresponding {@link BlockState}
	 *               at that position
	 * @param offset an offset to shift all the block positions by
	 */
	public TallBlockStatesEmiStack(Block block, LinkedHashMap<BlockPos, BlockState> states, Vector3f offset) {
		super(block);

		this.states = states;
		this.offset = offset;
	}

	/**
	 * Scale the size of the blocks rendered by {@code scale}.
	 * <p>
	 * {@code 1f} is the default scaling factor.
	 */
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
	protected void addTooltipComponents(List<TooltipComponent> components) {
		if (this.describeSingleState != null) {
			components.addAll(this.createPropertyComponents(this.describeSingleState));
		} else {
			// Add info for each state
			for (Map.Entry<BlockPos, BlockState> part : this.states.entrySet()) {
				BlockPos pos = part.getKey();
				BlockState state = part.getValue();

				List<TooltipComponent> properties = this.createPropertyComponents(state);

				if (!properties.isEmpty()) {
					// Blank line
					components.add(TooltipComponent.of(Text.empty().asOrderedText()));

					// Position text
					components.add(TooltipComponent.of(
						Text.literal(pos.toShortString() + ":")
							.formatted(Formatting.DARK_GRAY)
							.asOrderedText()
					));

					components.addAll(properties);
				}
			}
		}
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
