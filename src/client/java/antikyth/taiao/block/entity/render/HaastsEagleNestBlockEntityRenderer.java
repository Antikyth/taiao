// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity.render;

import antikyth.taiao.block.HaastsEagleNestBlock;
import antikyth.taiao.block.entity.HaastsEagleNestBlockEntity;
import antikyth.taiao.block.state.HorizontalDoubleSquareBlockPart;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class HaastsEagleNestBlockEntityRenderer implements BlockEntityRenderer<HaastsEagleNestBlockEntity> {
	protected final EntityRenderDispatcher entityRenderDispatcher;

	public HaastsEagleNestBlockEntityRenderer(BlockEntityRendererFactory.@NotNull Context ctx) {
		this.entityRenderDispatcher = ctx.getEntityRenderDispatcher();
	}

	@Override
	public void render(
		@NotNull HaastsEagleNestBlockEntity blockEntity,
		float tickDelta,
		MatrixStack matrices,
		VertexConsumerProvider vertexConsumers,
		int light,
		int overlay
	) {
		World world = blockEntity.getWorld();
		Entity chick = blockEntity.getOrCreateRenderedEntity(world);

		if (chick != null) {
			BlockState state = blockEntity.getCachedState();

			HorizontalDoubleSquareBlockPart part = state.get(HaastsEagleNestBlock.PART);
			float yaw = -90f * part.ordinal() + 45f;

			matrices.push();

			// Center of the block
			matrices.translate(0.5f, 2f / 16f, 0.5f);
			// Rotation
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw));
			// Shift forward
			matrices.translate(0f, 0f, 0.2f);
			// Scale
			matrices.scale(0.65f, 0.65f, 0.65f);

			this.entityRenderDispatcher.render(chick, 0d, 0d, 0d, 0f, 0f, matrices, vertexConsumers, light);

			matrices.pop();
		}
	}

	@Override
	public boolean rendersOutsideBoundingBox(HaastsEagleNestBlockEntity blockEntity) {
		// chicks are rendered outside the bounding box
		return true;
	}
}
