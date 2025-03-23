// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.model;

import antikyth.taiao.Taiao;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// Made with Blockbench 4.12.2
public class HaastsEagleEntityModel<E extends LivingEntity> extends AnimalModel<E> {
	private final ModelPart root;

	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tail;

	private final ModelPart leftWing;
	private final ModelPart leftWingLower;
	private final ModelPart leftWingEnd;

	private final ModelPart rightWing;
	private final ModelPart rightWingLower;
	private final ModelPart rightWingEnd;

	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public HaastsEagleEntityModel(@NotNull ModelPart root) {
		super(true, 11.5f, 3.4f);

		this.root = root;

		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.tail = body.getChild("tail");

		this.leftWing = body.getChild("left_wing");
		this.leftWingLower = this.leftWing.getChild("left_wing_lower");
		this.leftWingEnd = this.leftWingLower.getChild("left_wing_end");

		this.rightWing = body.getChild("right_wing");
		this.rightWingLower = this.rightWing.getChild("right_wing_lower");
		this.rightWingEnd = this.rightWingLower.getChild("right_wing_end");

		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
	}

	public static @NotNull TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData body = root.addChild(
			"body",
			ModelPartBuilder.create()
				.uv(48, 19)
				.cuboid(-3.5f, -10f, 1f, 7f, 20f, 7f),
			ModelTransform.of(0f, 19f, 0f, Taiao.degreesToRadians(90f), 0f, 0f)
		);
		root.addChild(
			"head",
			ModelPartBuilder.create()
				.uv(51, 7)
				.cuboid(-2.5f, -3f, -6f, 5f, 6f, 6f)
				// Beak
				.uv(57, 0)
				.cuboid(-1f, -1f, -9f, 2f, 4f, 3f),
			ModelTransform.pivot(0f, 13f, -10f)
		);
		body.addChild(
			"tail",
			ModelPartBuilder.create()
				.uv(48, 46)
				.cuboid(-7f, 0f, 0f, 14f, 14f, 0f)
				// Base of the tail
				.uv(30, 51)
				.cuboid(-3f, 0f, -3f, 6f, 6f, 3f),
			ModelTransform.pivot(0f, 10f, 7f)
		);

		ModelPartData leftWing = body.addChild(
			"left_wing",
			ModelPartBuilder.create()
				.uv(26, 26)
				.cuboid(0f, 0f, -2f, 9f, 14f, 2f),
			ModelTransform.pivot(3.5f, -8f, 7f)
		);
		ModelPartData leftWingLower = leftWing.addChild(
			"left_wing_lower",
			ModelPartBuilder.create()
				.uv(6, 29)
				.cuboid(0f, 0f, -1f, 9f, 12f, 1f),
			ModelTransform.pivot(9f, 2f, 0f)
		);
		leftWingLower.addChild(
			"left_wing_end",
			ModelPartBuilder.create()
				.uv(0, 33)
				.cuboid(0f, 0f, 0f, 3f, 8f, 0f),
			ModelTransform.pivot(9f, 3f, 0f)
		);

		ModelPartData rightWing = body.addChild(
			"right_wing",
			ModelPartBuilder.create()
				.uv(76, 26)
				.cuboid(-9f, 0f, -2f, 9f, 14f, 2f),
			ModelTransform.pivot(-3.5f, -8f, 7f)
		);
		ModelPartData rightWingLower = rightWing.addChild(
			"right_wing_lower",
			ModelPartBuilder.create()
				.uv(98, 29)
				.cuboid(-9f, 0f, -1f, 9f, 12f, 1f),
			ModelTransform.pivot(-9f, 2f, 0f)
		);
		rightWingLower.addChild(
			"right_wing_end",
			ModelPartBuilder.create()
				.uv(118, 33)
				.cuboid(-3f, 0f, 0f, 3f, 8f, 0f),
			ModelTransform.pivot(-9f, 3f, 0f)
		);

		root.addChild(
			"left_leg",
			ModelPartBuilder.create()
				.uv(0, 0)
				.cuboid(-1f, 0f, -1f, 2f, 7f, 2f)
				// Foot
				.uv(-5, 9)
				.cuboid(-2f, 7f, -4f, 4f, 0f, 5f),
			ModelTransform.of(2f, 17f, 3f, Taiao.degreesToRadians(-22.5f), 0f, 0f)
		);
		root.addChild(
			"right_leg",
			ModelPartBuilder.create()
				.uv(10, 0)
				.cuboid(-1f, 0f, -1f, 2f, 7f, 2f)
				// Foot
				.uv(5, 9)
				.cuboid(-2f, 7f, -4f, 4f, 0f, 5f),
			ModelTransform.of(-2f, 17f, 3f, Taiao.degreesToRadians(-22.5f), 0f, 0f)
		);

		return TexturedModelData.of(data, 128, 64);
	}

	@Override
	public void animateModel(@NotNull E entity, float limbAngle, float limbDistance, float tickDelta) {
		this.root.resetTransform();

		this.head.resetTransform();
		this.body.resetTransform();

		this.leftWing.resetTransform();
		this.rightWing.resetTransform();

		this.leftLeg.resetTransform();
		this.rightLeg.resetTransform();

		this.tail.resetTransform();

		if (entity.isInPose(EntityPose.STANDING)) {
			// Standing

			this.body.pivotZ -= 3f;
			this.head.pivotZ -= 3f;

			this.leftLeg.pivotZ -= 4f;
			this.rightLeg.pivotZ -= 4f;

			this.leftWingLower.hidden = true;
			this.leftWingEnd.hidden = true;

			this.rightWingLower.hidden = true;
			this.rightWingEnd.hidden = true;

			this.leftWing.pivotX += 2f;
			this.rightWing.pivotX -= 2f;

			this.leftWing.yaw += Taiao.degreesToRadians(80f);
			this.rightWing.yaw -= Taiao.degreesToRadians(80f);

			this.leftLeg.pitch = 0f;
			this.rightLeg.pitch = 0f;

			this.body.pitch -= Taiao.degreesToRadians(40f);
			this.body.pivotY -= 2f;

			this.tail.pitch += Taiao.degreesToRadians(25f);

			if (entity.isBaby()) {
				this.head.pivotY -= 5f;
				this.head.pivotZ += 5f;
			} else {
				this.head.pivotY -= 7f;
				this.head.pivotZ += 6f;
			}

		} else {
			// Flying

			this.leftWingLower.hidden = false;
			this.leftWingEnd.hidden = false;

			this.rightWingLower.hidden = false;
			this.rightWingEnd.hidden = false;
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
		headYawDegrees = MathHelper.clamp(headYawDegrees, -45f, 45f);

		this.head.yaw += Taiao.degreesToRadians(headYawDegrees);
		this.head.pitch += Taiao.degreesToRadians(headPitchDegrees);
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return List.of(this.body, this.leftLeg, this.rightLeg);
	}

	@Override
	protected Iterable<ModelPart> getHeadParts() {
		return List.of(this.head);
	}
}