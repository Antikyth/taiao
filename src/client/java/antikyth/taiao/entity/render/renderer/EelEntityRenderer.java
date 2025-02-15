// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.renderer;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.EelEntity;
import antikyth.taiao.entity.render.TaiaoEntityModels;
import antikyth.taiao.entity.render.model.EelEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class EelEntityRenderer extends MobEntityRenderer<EelEntity, EelEntityModel<EelEntity>> {
	public static final Identifier TEXTURE = Taiao.id("textures/entity/eel.png");

	public EelEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new EelEntityModel<>(context.getPart(TaiaoEntityModels.EEL)), 0.6f);
	}

	@Override
	public Identifier getTexture(EelEntity entity) {
		return TEXTURE;
	}

	@Override
	protected void setupTransforms(
		EelEntity entity,
		MatrixStack matrices,
		float animationProgress,
		float bodyYaw,
		float tickDelta
	) {
		super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);

		float yaw = entity.getYawMultiplier() * 4.3f * MathHelper.sin(entity.getYawAngleMultiplier() * 0.6f * animationProgress);
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw));

		// Flip to side when on land
		if (!entity.isTouchingWater()) {
			matrices.translate(0.2f, 0.1f, 0f);
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90f));
		}
	}
}
