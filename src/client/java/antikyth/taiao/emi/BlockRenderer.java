// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.emi;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

public class BlockRenderer {
	protected final MinecraftClient client;
	protected final BlockColors colors;

	public BlockRenderer(@NotNull MinecraftClient client) {
		this.client = client;
		this.colors = client.getBlockColors();
	}

	public void renderBlock(
		MatrixStack matrices,
		VertexConsumerProvider vertexConsumers,
		Transformation transformation,
		BlockState state,
		float scale
	) {
		this.renderBlock(
			matrices,
			vertexConsumers,
			transformation,
			state,
			LightmapTextureManager.MAX_LIGHT_COORDINATE,
			OverlayTexture.DEFAULT_UV,
			scale
		);
	}

	public void renderBlock(
		@NotNull MatrixStack matrices,
		@NotNull VertexConsumerProvider vertexConsumers,
		@NotNull Transformation transformation,
		BlockState state,
		int light,
		int overlay,
		float scale
	) {
		BakedModel model = this.client.getBlockRenderManager().getModel(state);

		Block block = state.getBlock();
		boolean direct = !(block instanceof TransparentBlock || block instanceof StainedGlassPaneBlock);
		RenderLayer layer = RenderLayers.getEntityBlockLayer(state, direct);

		matrices.push();

		// Transformations
		transformation.apply(false, matrices);
		matrices.scale(scale, scale, scale);
		matrices.translate(-0.5f, -0.5f, -0.5f);

		this.renderBakedBlockModel(matrices, vertexConsumers.getBuffer(layer), state, light, overlay, model);

		matrices.pop();
	}

	/**
	 * Renders a set of blocks.
	 *
	 * @param transformation the transformation to apply for the blocks ("viewing angle")
	 * @param offset         an extra offset to apply (units of blocks/meters)
	 * @param states         map entries of positions (relative to 0,0,0 at the center) to states to render
	 * @param scale          a scale factor to apply ({@code 1f} for default)
	 */
	public void renderBlocks(
		MatrixStack matrices,
		VertexConsumerProvider vertexConsumers,
		Transformation transformation,
		Vector3f offset,
		Iterable<Map.Entry<BlockPos, BlockState>> states,
		float scale
	) {
		this.renderBlocks(
			matrices,
			vertexConsumers,
			transformation,
			offset,
			states,
			LightmapTextureManager.MAX_LIGHT_COORDINATE,
			OverlayTexture.DEFAULT_UV,
			scale
		);
	}

	/**
	 * Renders a set of blocks.
	 *
	 * @param transformation the transformation to apply for the blocks ("viewing angle")
	 * @param offset         an extra offset to apply (units of blocks/meters)
	 * @param states         map entries of positions (relative to 0,0,0 at the center) to states to render
	 * @param scale          a scale factor to apply ({@code 1f} for default)
	 */
	public void renderBlocks(
		@NotNull MatrixStack matrices,
		VertexConsumerProvider vertexConsumers,
		@NotNull Transformation transformation,
		@NotNull Vector3f offset,
		@NotNull Iterable<Map.Entry<BlockPos, BlockState>> states,
		int light,
		int overlay,
		float scale
	) {
		matrices.push();

		transformation.apply(false, matrices);
		matrices.scale(scale, scale, scale);
		matrices.translate(-0.5f, -0.5f, -0.5f);

		matrices.translate(offset.x, offset.y, offset.z);

		for (Map.Entry<BlockPos, BlockState> entry : states) {
			BlockPos pos = entry.getKey();
			BlockState state = entry.getValue();

			Block block = state.getBlock();
			boolean direct = !(block instanceof TransparentBlock || block instanceof StainedGlassPaneBlock);
			RenderLayer layer = RenderLayers.getEntityBlockLayer(state, direct);

			BakedModel model = this.client.getBlockRenderManager().getModel(state);

			matrices.push();

			matrices.translate(pos.getX(), pos.getY(), pos.getZ());
			this.renderBakedBlockModel(matrices, vertexConsumers.getBuffer(layer), state, light, overlay, model);

			matrices.pop();
		}

		matrices.pop();
	}

	protected void renderBakedBlockModel(
		MatrixStack matrices,
		VertexConsumer vertices,
		BlockState state,
		int light,
		int overlay,
		BakedModel model
	) {
		Random random = Random.create();
		long seed = 42L;

		for (Direction direction : Direction.values()) {
			random.setSeed(seed);
			this.renderBakedBlockQuads(
				matrices,
				vertices,
				model.getQuads(state, direction, random),
				state,
				light,
				overlay
			);
		}

		random.setSeed(seed);
		this.renderBakedBlockQuads(matrices, vertices, model.getQuads(state, null, random), state, light, overlay);
	}

	protected void renderBakedBlockQuads(
		@NotNull MatrixStack matrices,
		VertexConsumer vertices,
		@NotNull List<BakedQuad> quads,
		BlockState state,
		int light,
		int overlay
	) {
		MatrixStack.Entry entry = matrices.peek();

		for (BakedQuad quad : quads) {
			int color = -1;
			if (quad.hasColor()) {
				color = this.colors.getColor(state, null, null, quad.getColorIndex());
			}

			float red = (float) ((color >> 16) & 0xff) / 255f;
			float green = (float) ((color >> 8) & 0xff) / 255f;
			float blue = (float) (color & 0xff) / 255f;

			vertices.quad(entry, quad, red, green, blue, light, overlay);
		}
	}
}
