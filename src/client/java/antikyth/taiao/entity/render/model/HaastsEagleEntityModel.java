// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// Made with Blockbench 4.12.2
public class HaastsEagleEntityModel<E extends Entity> extends AnimalModel<E> {
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
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.tail = root.getChild("tail");
		this.leftWing = root.getChild("left_wing");
		this.leftWingLower = this.leftWing.getChild("left_wing_lower");
		this.leftWingEnd = this.leftWingLower.getChild("left_wing_end");
		this.rightWing = root.getChild("right_wing");
		this.rightWingLower = this.rightWing.getChild("right_wing_lower");
		this.rightWingEnd = this.rightWingLower.getChild("right_wing_end");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
	}

	public static @NotNull TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild(
			"body",
			ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -10.0F, 1.0F, 7.0F, 20.0F, 7.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 19.0F, 0.0F, 1.5708F, 0.0F, 0.0F)
		);

		ModelPartData head = modelPartData.addChild(
			"head",
			ModelPartBuilder.create().uv(28, 32).cuboid(-2.5F, -6.0F, -3.0F, 5.0F, 6.0F, 6.0F, new Dilation(0.0F))
				.uv(50, 10).cuboid(-1.0F, -9.0F, -3.0F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 13.0F, -10.0F, 1.5708F, 0.0F, 0.0F)
		);

		ModelPartData tail = modelPartData.addChild(
			"tail",
			ModelPartBuilder.create().uv(0, 27).cuboid(-7.0F, 0.0F, 0.0F, 14.0F, 14.0F, 0.0F, new Dilation(0.0F))
				.uv(40, 44).cuboid(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 3.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 12.0F, 10.0F, 1.5708F, 0.0F, 0.0F)
		);

		ModelPartData left_wing = modelPartData.addChild(
			"left_wing",
			ModelPartBuilder.create().uv(28, 16).cuboid(0.0F, 0.0F, -2.0F, 9.0F, 14.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(3.5F, 12.0F, -8.0F, 1.5708F, 0.0F, 0.0F)
		);

		ModelPartData left_wing_lower = left_wing.addChild(
			"left_wing_lower",
			ModelPartBuilder.create().uv(20, 44).cuboid(0.0F, 0.0F, -1.0F, 9.0F, 12.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.pivot(9.0F, 2.0F, 0.0F)
		);

		ModelPartData left_wing_end = left_wing_lower.addChild(
			"left_wing_end",
			ModelPartBuilder.create().uv(40, 53).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 8.0F, 0.0F, new Dilation(0.0F)),
			ModelTransform.pivot(9.0F, 3.0F, 0.0F)
		);

		ModelPartData right_wing = modelPartData.addChild(
			"right_wing",
			ModelPartBuilder.create().uv(28, 0).cuboid(-9.0F, 0.0F, -2.0F, 9.0F, 14.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(-3.5F, 12.0F, -8.0F, 1.5708F, 0.0F, 0.0F)
		);

		ModelPartData right_wing_lower = right_wing.addChild(
			"right_wing_lower",
			ModelPartBuilder.create().uv(0, 41).cuboid(-9.0F, 0.0F, -1.0F, 9.0F, 12.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.pivot(-9.0F, 2.0F, 0.0F)
		);

		ModelPartData right_wing_end = right_wing_lower.addChild(
			"right_wing_end",
			ModelPartBuilder.create().uv(50, 35).cuboid(-3.0F, 0.0F, 0.0F, 3.0F, 8.0F, 0.0F, new Dilation(0.0F)),
			ModelTransform.pivot(-9.0F, 3.0F, 0.0F)
		);

		ModelPartData left_leg = modelPartData.addChild(
			"left_leg",
			ModelPartBuilder.create().uv(50, 17).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F))
				.uv(50, 0).cuboid(-2.0F, 7.0F, -4.0F, 4.0F, 0.0F, 5.0F, new Dilation(0.0F)),
			ModelTransform.of(2.0F, 17.0F, 3.0F, -0.3927F, 0.0F, 0.0F)
		);

		ModelPartData right_leg = modelPartData.addChild(
			"right_leg",
			ModelPartBuilder.create().uv(50, 26).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F))
				.uv(50, 5).cuboid(-2.0F, 7.0F, -4.0F, 4.0F, 0.0F, 5.0F, new Dilation(0.0F)),
			ModelTransform.of(-2.0F, 17.0F, 3.0F, -0.3927F, 0.0F, 0.0F)
		);
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(
		E entity,
		float limbSwing,
		float limbSwingAmount,
		float ageInTicks,
		float netHeadYaw,
		float headPitch
	) {
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return List.of(this.body, this.leftLeg, this.rightLeg, this.leftWing, this.rightWing);
	}

	@Override
	protected Iterable<ModelPart> getHeadParts() {
		return List.of(this.head);
	}
}