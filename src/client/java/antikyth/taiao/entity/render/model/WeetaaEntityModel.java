// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.model;

import antikyth.taiao.Taiao;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

// Made with Blockbench 4.12.2
public class WeetaaEntityModel<E extends Entity> extends SinglePartEntityModel<E> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart rearLeftLeg;
	private final ModelPart rearRightLeg;
	private final ModelPart middleLeftLeg;
	private final ModelPart middleRightLeg;
	private final ModelPart frontLeftLeg;
	private final ModelPart frontRightLeg;

	public WeetaaEntityModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild("head");
		this.rearLeftLeg = root.getChild("rear_left_leg");
		this.rearRightLeg = root.getChild("rear_right_leg");
		this.middleLeftLeg = root.getChild("middle_left_leg");
		this.middleRightLeg = root.getChild("middle_right_leg");
		this.frontLeftLeg = root.getChild("front_left_leg");
		this.frontRightLeg = root.getChild("front_right_leg");
	}

	public static @NotNull TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		root.addChild(
			"body",
			ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -9.0F, -1.0F, 6.0F, 6.0F, 14.0F, new Dilation(0.0F)),
			ModelTransform.pivot(0.0F, 25.0F, -1.0F)
		);
		root.addChild(
			"neck",
			ModelPartBuilder.create().uv(28, 31).cuboid(-3.0F, 0.0F, -4.0F, 6.0F, 3.0F, 4.0F, new Dilation(0.0F)),
			ModelTransform.pivot(0.0F, 16.0F, -2.0F)
		);

		ModelPartData head = root.addChild(
			"head",
			ModelPartBuilder.create().uv(28, 20).cuboid(-3.0F, 0.0F, -4.0F, 6.0F, 7.0F, 4.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 16.0F, -6.0F, -0.3927F, 0.0F, 0.0F)
		);
		head.addChild(
			"antennae",
			ModelPartBuilder.create().uv(0, 20).cuboid(-6.0F, -11.0F, -2.0F, 12.0F, 12.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.pivot(0.0F, 1.0F, -4.0F)
		);

		ModelPartData rearLeftLeg = root.addChild(
			"rear_left_leg",
			ModelPartBuilder.create()
				.uv(0, 34)
				.cuboid(0.0F, -13.8126F, -0.8452F, 2.0F, 14.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(3.0F, 22.0F, 4.0F, -0.4363F, 0.2618F, 0.0F)
		);
		ModelPartData rearLeftLegLower = rearLeftLeg.addChild(
			"rear_left_leg_lower",
			ModelPartBuilder.create().uv(28, 38).cuboid(0.0F, 0.0F, -2.0F, 1.0F, 14.0F, 2.0F, new Dilation(0.0F))
				.uv(16, 34).cuboid(0.5F, 0.0F, 0.0F, 0.0F, 14.0F, 3.0F, new Dilation(0.0F)),
			ModelTransform.of(0.5F, -13.8126F, 1.1548F, 0.6109F, 0.0F, 0.0F)
		);
		rearLeftLegLower.addChild(
			"rear_left_foot_r1",
			ModelPartBuilder.create().uv(40, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 6.0F, new Dilation(0.0F)),
			ModelTransform.of(0.5F, 14.0F, -1.0F, -0.1745F, 0.0F, 0.0F)
		);

		ModelPartData rearRightLeg = root.addChild(
			"rear_right_leg",
			ModelPartBuilder.create()
				.uv(8, 34)
				.cuboid(-2.0F, -13.8126F, -0.8452F, 2.0F, 14.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(-3.0F, 22.0F, 4.0F, -0.4363F, -0.2618F, 0.0F)
		);
		ModelPartData rearRightLegLower = rearRightLeg.addChild(
			"rear_right_leg_lower",
			ModelPartBuilder.create().uv(34, 38).cuboid(0.0F, 0.0F, -2.0F, 1.0F, 14.0F, 2.0F, new Dilation(0.0F))
				.uv(22, 34).cuboid(0.5F, 0.0F, 0.0F, 0.0F, 14.0F, 3.0F, new Dilation(0.0F)),
			ModelTransform.of(-1.5F, -13.8126F, 1.1548F, 0.6109F, 0.0F, 0.0F)
		);
		rearRightLegLower.addChild(
			"rear_right_foot_r1",
			ModelPartBuilder.create().uv(40, 6).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 6.0F, new Dilation(0.0F)),
			ModelTransform.of(0.5F, 14.0F, -1.0F, -0.1745F, 0.0F, 0.0F)
		);

		ModelPartData middleLeftLeg = root.addChild(
			"middle_left_leg",
			ModelPartBuilder.create().uv(48, 48).cuboid(6.0F, -7.0F, -0.5F, 1.0F, 9.0F, 1.0F, new Dilation(0.0F))
				.uv(12, 51).cuboid(7.0F, 2.0F, -0.5F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(3.0F, 22.0F, -3.0F, 0.0F, -0.3491F, 0.0F)
		);
		middleLeftLeg.addChild(
			"cube_r1",
			ModelPartBuilder.create().uv(40, 38).cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(7.0F, -7.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
		);

		ModelPartData middleRightLeg = root.addChild(
			"middle_right_leg",
			ModelPartBuilder.create().uv(0, 50).cuboid(6.0F, -7.0F, -0.5F, 1.0F, 9.0F, 1.0F, new Dilation(0.0F))
				.uv(12, 52).cuboid(7.0F, 2.0F, -0.5F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(-3.0F, 22.0F, -3.0F, 0.0F, -2.7925F, 0.0F)
		);
		middleRightLeg.addChild(
			"cube_r2",
			ModelPartBuilder.create().uv(48, 12).cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(7.0F, -7.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
		);

		ModelPartData frontLeftLeg = root.addChild(
			"front_left_leg",
			ModelPartBuilder.create().uv(52, 48).cuboid(10.75F, 3.0F, -0.5F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(3.0F, 21.0F, -6.0F, 0.0F, 0.5236F, 0.0F)
		);
		frontLeftLeg.addChild(
			"cube_r3",
			ModelPartBuilder.create().uv(4, 50).cuboid(-1.0F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(7.0F, -4.0F, 0.0F, 0.0F, 0.0F, -0.6109F)
		);
		frontLeftLeg.addChild(
			"cube_r4",
			ModelPartBuilder.create().uv(48, 24).cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(7.0F, -4.0F, 0.0F, 0.0F, 0.0F, 1.0472F)
		);

		ModelPartData frontRightLeg = root.addChild(
			"front_right_leg",
			ModelPartBuilder.create().uv(52, 49).cuboid(10.75F, 3.0F, -0.5F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(-3.0F, 21.0F, -6.0F, 0.0F, 2.618F, 0.0F)
		);
		frontRightLeg.addChild(
			"cube_r5",
			ModelPartBuilder.create().uv(8, 50).cuboid(-1.0F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(7.0F, -4.0F, 0.0F, 0.0F, 0.0F, -0.6109F)
		);
		frontRightLeg.addChild(
			"cube_r6",
			ModelPartBuilder.create().uv(48, 36).cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(7.0F, -4.0F, 0.0F, 0.0F, 0.0F, 1.0472F)
		);

		return TexturedModelData.of(data, 64, 64);
	}

	@Override
	public void animateModel(E entity, float limbAngle, float limbDistance, float tickDelta) {
		this.head.resetTransform();

		this.frontLeftLeg.resetTransform();
		this.middleLeftLeg.resetTransform();
		this.rearLeftLeg.resetTransform();

		this.frontRightLeg.resetTransform();
		this.middleRightLeg.resetTransform();
		this.rearRightLeg.resetTransform();
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
		headYawDegrees = MathHelper.clamp(headYawDegrees, -30f, 30f);
		headPitchDegrees = MathHelper.clamp(headPitchDegrees, -22.5f, 45f);

		this.head.pitch += Taiao.degreesToRadians(headPitchDegrees);
		this.head.yaw += Taiao.degreesToRadians(headYawDegrees);

		float limbAngle1 = MathHelper.cos(limbSwing * 0.6662f * 2f) * 1.4f * limbSwingAmount * 0.5f;
		float limbAngle2 = MathHelper.cos(limbSwing * 0.6662f * 2f + (float) Math.PI) * 1.4f * limbSwingAmount * 0.5f;

		this.frontLeftLeg.yaw += limbAngle1;
		this.middleLeftLeg.yaw += limbAngle2;

		this.frontRightLeg.yaw += limbAngle1;
		this.middleRightLeg.yaw += limbAngle2;

		this.rearLeftLeg.pitch += limbAngle1 * 0.75f;
		this.rearRightLeg.pitch += limbAngle2 * 0.75f;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}