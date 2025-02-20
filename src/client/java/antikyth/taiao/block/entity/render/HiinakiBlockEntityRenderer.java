// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity.render;

import antikyth.taiao.block.HiinakiBlock;
import antikyth.taiao.block.entity.HiinakiBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class HiinakiBlockEntityRenderer implements BlockEntityRenderer<HiinakiBlockEntity> {
	protected final EntityRenderDispatcher entityRenderDispatcher;
	protected final ItemRenderer itemRenderer;

	public HiinakiBlockEntityRenderer(BlockEntityRendererFactory.@NotNull Context context) {
		this.entityRenderDispatcher = context.getEntityRenderDispatcher();
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(
		@NotNull HiinakiBlockEntity blockEntity,
		float tickDelta,
		@NotNull MatrixStack matrices,
		VertexConsumerProvider vertexConsumers,
		int light,
		int overlay
	) {
		World world = blockEntity.getWorld();
		BlockPos pos = blockEntity.getPos();
		BlockState state = blockEntity.getCachedState();

		matrices.push();

		// Center of front block
		matrices.translate(0.5f, 0.4f, 0.5f);
		// Yaw
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(HiinakiBlock.getYaw(state.get(HiinakiBlock.FACING))));
		// Shift towards the back of the hīnaki
		matrices.translate(0f, 0f, HiinakiBlock.getContentsOffset());

		Entity trappedEntity = blockEntity.getRenderedEntity();
		if (trappedEntity != null) {
			matrices.scale(0.75f, 0.75f, 0.75f);

			this.entityRenderDispatcher.render(
				trappedEntity,
				0d,
				0d,
				0d,
				0f,
				0f,
				matrices,
				vertexConsumers,
				light
			);
		} else if (blockEntity.hasBait()) {
			matrices.scale(0.5f, 0.5f, 0.5f);

			this.itemRenderer.renderItem(
				blockEntity.getBait(),
				ModelTransformationMode.FIXED,
				light,
				overlay,
				matrices,
				vertexConsumers,
				world,
				(int) state.getRenderingSeed(pos)
			);
		}

		matrices.pop();
	}

	@Override
	public boolean rendersOutsideBoundingBox(HiinakiBlockEntity blockEntity) {
		// renders across the two blocks that hīnaki takes up
		return true;
	}
}
