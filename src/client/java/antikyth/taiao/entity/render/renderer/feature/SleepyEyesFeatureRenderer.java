// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.renderer.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * An overlay layer rendered when an entity is sleeping.
 */
public abstract class SleepyEyesFeatureRenderer<E extends LivingEntity, M extends EntityModel<E>> extends
	FeatureRenderer<E, M> {
	public SleepyEyesFeatureRenderer(FeatureRendererContext<E, M> context) {
		super(context);
	}

	@Override
	public void render(
		MatrixStack matrices,
		VertexConsumerProvider vertexConsumers,
		int light,
		@NotNull E entity,
		float limbAngle,
		float limbDistance,
		float tickDelta,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		if (!entity.isInvisible() && entity.isSleeping()) {
			M model = this.getContextModel();
			Identifier texture = this.getTexture(entity);

			renderModel(model, texture, matrices, vertexConsumers, light, entity, 1f, 1f, 1f);
		}
	}

	/**
	 * Returns the overlay texture for when the entity is sleeping.
	 */
	public abstract Identifier getTexture(E entity);
}
