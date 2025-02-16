// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity.render;

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
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
		matrices.push();

		matrices.translate(0f, 0.4f, 0f);
		matrices.scale(0.5f, 0.5f, 0.5f);

		World world = blockEntity.getWorld();
		BlockPos pos = blockEntity.getPos();
		BlockState state = blockEntity.getCachedState();

		// TODO: positioning and rotation based on facing property

		if (blockEntity.hasTrappedEntity()) {
			Entity trappedEntity = Objects.requireNonNull(blockEntity.getTrappedEntity());

			this.entityRenderDispatcher.render(
				trappedEntity,
				0d,
				0d,
				0d,
				0f,
				tickDelta,
				matrices,
				vertexConsumers,
				light
			);
		} else if (blockEntity.hasBait()) {
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
		// renders across the two blocks that hiinaki takes up
		return true;
	}
}
