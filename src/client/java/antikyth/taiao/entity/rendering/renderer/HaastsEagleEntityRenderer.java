// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.rendering.renderer;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.HaastsEagleEntity;
import antikyth.taiao.entity.rendering.TaiaoEntityModels;
import antikyth.taiao.entity.rendering.model.HaastsEagleEntityModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class HaastsEagleEntityRenderer extends MobEntityRenderer<HaastsEagleEntity, HaastsEagleEntityModel<HaastsEagleEntity>> {
	private static final Identifier TEXTURE = Taiao.id("textures/entity/haasts_eagle.png");

	public HaastsEagleEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new HaastsEagleEntityModel<>(context.getPart(TaiaoEntityModels.HAASTS_EAGLE)), 1f);
	}

	@Override
	public Identifier getTexture(HaastsEagleEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(
		@NotNull HaastsEagleEntity eagle,
		float yaw,
		float tickDelta,
		MatrixStack matrices,
		VertexConsumerProvider vertexConsumers,
		int light
	) {
		this.shadowRadius = eagle.isInPose(EntityPose.STANDING) ? 0.6f : 1f;

		super.render(eagle, yaw, tickDelta, matrices, vertexConsumers, light);
	}
}
