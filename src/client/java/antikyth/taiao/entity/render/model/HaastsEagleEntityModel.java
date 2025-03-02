// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.model;

import antikyth.taiao.Taiao;
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
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		root.addChild(
			"body",
			ModelPartBuilder.create()
				.uv(48, 19)
				.cuboid(-3.5F, -10f, 1f, 7f, 20f, 7f),
			ModelTransform.of(0f, 19f, 0f, Taiao.degreesToRadians(90f), 0f, 0f)
		);
		root.addChild(
			"head",
			ModelPartBuilder.create()
				.uv(51, 7)
				.cuboid(-2.5F, -6f, -3f, 5f, 6f, 6f)
				// Beak
				.uv(56, 0)
				.cuboid(-1f, -9f, -3f, 2f, 3f, 4f),
			ModelTransform.of(0f, 13f, -10f, Taiao.degreesToRadians(90f), 0f, 0f)
		);

		root.addChild(
			"tail",
			ModelPartBuilder.create()
				.uv(48, 46)
				.cuboid(-7f, 0f, 0f, 14f, 14f, 0f)
				// Base of the tail
				.uv(30, 51)
				.cuboid(-3f, 0f, -3f, 6f, 6f, 3f),
			ModelTransform.of(0f, 12f, 10f, Taiao.degreesToRadians(90f), 0f, 0f)
		);

		ModelPartData leftWing = root.addChild(
			"left_wing",
			ModelPartBuilder.create()
				.uv(26, 26)
				.cuboid(0f, 0f, -2f, 9f, 14f, 2f),
			ModelTransform.of(3.5F, 12f, -8f, Taiao.degreesToRadians(90f), 0f, 0f)
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
				.uv(0, 34)
				.cuboid(0f, 0f, 0f, 3f, 8f, 0f),
			ModelTransform.pivot(9f, 3f, 0f)
		);

		ModelPartData rightWing = root.addChild(
			"right_wing",
			ModelPartBuilder.create()
				.uv(76, 26)
				.cuboid(-9f, 0f, -2f, 9f, 14f, 2f),
			ModelTransform.of(-3.5F, 12f, -8f, Taiao.degreesToRadians(90f), 0f, 0f)
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
				.uv(118, 34)
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
		return List.of(this.body, this.tail, this.leftLeg, this.rightLeg, this.leftWing, this.rightWing);
	}

	@Override
	protected Iterable<ModelPart> getHeadParts() {
		return List.of(this.head);
	}
}