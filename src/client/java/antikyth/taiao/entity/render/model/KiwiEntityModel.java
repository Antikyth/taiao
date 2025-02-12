// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.model;

import antikyth.taiao.Taiao;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

// Made with Blockbench 4.12.1
public class KiwiEntityModel<E extends LivingEntity> extends AnimalModel<E> {
	/**
	 * The default pitch of the kiwi's head in degrees.
	 */
	private static final float HEAD_PITCH = 30f;

	private final ModelPart body;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart head;

	public KiwiEntityModel(@NotNull ModelPart root) {
		super(true, 9.5f, 1f);

		this.body = root.getChild("body");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
		this.head = root.getChild("head");
	}

	public static @NotNull TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild(
			"body",
			ModelPartBuilder.create().uv(0, 0).cuboid(-5f, -8f, -3f, 6f, 6f, 7f),
			ModelTransform.pivot(2f, 24f, 0f)
		);

		ModelPartBuilder leg = ModelPartBuilder.create().uv(0, 19).cuboid(-1.5f, 0f, -1f, 3f, 2f, 2f);

		modelPartData.addChild("left_leg", leg, ModelTransform.pivot(1.5f, 22f, 0f));
		modelPartData.addChild("right_leg", leg, ModelTransform.pivot(-1.5f, 22f, 0f));

		modelPartData.addChild(
			"head",
			ModelPartBuilder.create().uv(0, 13).cuboid(-1.5f, -1.5f, -3f, 3f, 3f, 3f)
				.uv(12, 13).cuboid(-0.5f, -0.5f, -8f, 1f, 1f, 5f),
			ModelTransform.pivot(0f, 18f, -3f)
		);

		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void animateModel(@NotNull E entity, float limbAngle, float limbDistance, float tickDelta) {
		if (!entity.isSleeping()) {
			this.body.resetTransform();
			this.head.resetTransform();
		} else {
			this.body.pivotY = this.body.getDefaultTransform().pivotY + 2f;
			this.head.pivotY = this.head.getDefaultTransform().pivotY + 2f;
		}
	}

	@Override
	public void setAngles(
		E entity,
		float limbSwing,
		float limbSwingAmount,
		float ageInTicks,
		float headYawDegrees,
		float headPitchDegrees
	) {
		this.head.pitch = Taiao.degreesToRadians(headPitchDegrees + HEAD_PITCH);
		this.head.yaw = Taiao.degreesToRadians(headYawDegrees);

		this.rightLeg.pitch = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
		this.leftLeg.pitch = MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount;
	}

	@Override
	protected Iterable<ModelPart> getHeadParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return ImmutableList.of(this.body, this.leftLeg, this.rightLeg);
	}
}