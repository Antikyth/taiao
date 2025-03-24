// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.rendering.model;

import antikyth.taiao.Taiao;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// Made with Blockbench 4.12.3
public class KereruuEntityModel<E extends Entity> extends AnimalModel<E> {
	private final ModelPart head;
	private final ModelPart body;

	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public KereruuEntityModel(@NotNull ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");

		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
	}

	public static @NotNull TexturedModelData getTexturedModelData() {
		ModelData model = new ModelData();
		ModelPartData root = model.getRoot();

		root.addChild(
			"head",
			ModelPartBuilder.create()
				.uv(10, 0)
				.cuboid(
					-1f, -2f, -2f,
					2f, 2f, 2.0F
				)
				// Beak
				.uv(12, 2)
				.cuboid(
					0f, -1.5f, -4f,
					0f, 1f, 2f
				),
			ModelTransform.pivot(0f, 17f, -1f)
		);

		ModelPartData body = root.addChild(
			"body",
			ModelPartBuilder.create()
				.uv(6, 6)
				.cuboid(
					-2f, -0.5f, -2.5f,
					4f, 6f, 4f
				),
			ModelTransform.of(
				0f, 17f, -1f,
				Taiao.degreesToRadians(30f), 0f, 0f
			)
		);
		body.addChild(
			"tail",
			ModelPartBuilder.create()
				.uv(0, 0)
				.cuboid(
					-2f, 0f, 0f,
					4f, 5f, 0.0F
				),
			ModelTransform.of(
				0f, 5.5f, 1.5f,
				Taiao.degreesToRadians(30f), 0f, 0f
			)
		);

		body.addChild(
			"left_wing",
			ModelPartBuilder.create()
				.uv(0, 7)
				.cuboid(
					0f, 0f, -1.5f,
					0f, 6f, 3f
				),
			ModelTransform.of(
				2f, 0.5f, 0f,
				0f, 0f, Taiao.degreesToRadians(-5f)
			)
		);
		body.addChild(
			"right_wing",
			ModelPartBuilder.create()
				.uv(22, 7)
				.cuboid(
					0f, 0f, -1.5f,
					0f, 6f, 3f
				),
			ModelTransform.of(
				-2f, 0.5f, 0f,
				0f, 0f, Taiao.degreesToRadians(5f)
			)
		);

		root.addChild(
			"left_leg",
			ModelPartBuilder.create()
				.uv(19, 0)
				.cuboid(
					-0.5f, 0f, -2f,
					1f, 2f, 2f
				),
			ModelTransform.pivot(1f, 22f, -1f)
		);
		root.addChild(
			"right_leg",
			ModelPartBuilder.create()
				.uv(26, 0)
				.cuboid(
					-0.5f, 0f, -2f,
					1f, 2f, 2f
				),
			ModelTransform.pivot(-1f, 22f, -1f)
		);

		return TexturedModelData.of(model, 32, 16);
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
		this.head.yaw = Taiao.degreesToRadians(headYawDegrees);
		this.head.pitch = Taiao.degreesToRadians(headPitchDegrees);

		this.leftLeg.pitch = MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount;
		this.rightLeg.pitch = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
	}

	@Override
	protected Iterable<ModelPart> getHeadParts() {
		return List.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return List.of(this.body, this.leftLeg, this.rightLeg);
	}
}