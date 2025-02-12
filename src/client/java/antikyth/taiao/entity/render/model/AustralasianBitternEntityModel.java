// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.model;

import antikyth.taiao.TaiaoClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// Made with Blockbench 4.12.2
public class AustralasianBitternEntityModel<E extends Entity> extends AnimalModel<E> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart neck;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public AustralasianBitternEntityModel(@NotNull ModelPart root) {
		super(true, 9f, 0.75f);

		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.neck = root.getChild("neck");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
	}

	public static @NotNull TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild(
			"body",
			ModelPartBuilder.create().uv(0, 0).cuboid(-2.5f, -7f, -4f, 5f, 4f, 8f),
			ModelTransform.pivot(0f, 24f, 2f)
		);

		modelPartData.addChild(
			"head",
			ModelPartBuilder.create().uv(0, 20).cuboid(-1f, -6f, -3f, 2f, 3f, 3f)
				.uv(10, 24).cuboid(-1f, -5f, -6f, 2f, 1f, 3f),
			ModelTransform.pivot(0f, 19f, -2f)
		);
		modelPartData.addChild(
			"neck",
			ModelPartBuilder.create().uv(0, 12).cuboid(-1.5f, -3f, -3f, 3f, 5f, 3f),
			ModelTransform.pivot(0f, 19f, -2f)
		);

		modelPartData.addChild(
			"left_leg",
			ModelPartBuilder.create().uv(12, 18).cuboid(-1.5f, 0f, -3f, 3f, 3f, 3f),
			ModelTransform.pivot(1.5f, 21f, 1f)
		);
		modelPartData.addChild(
			"right_leg",
			ModelPartBuilder.create().uv(12, 12).cuboid(-1.5f, 0f, -3f, 3f, 3f, 3f),
			ModelTransform.pivot(-1.5f, 21f, 1f)
		);

		return TexturedModelData.of(modelData, 32, 32);
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
		this.head.pitch = TaiaoClient.degreesToRadians(headPitchDegrees);
		this.head.yaw = TaiaoClient.degreesToRadians(headYawDegrees);

		this.neck.pitch = this.head.pitch;
		this.neck.yaw = this.head.yaw;

		this.rightLeg.pitch = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
		this.leftLeg.pitch = MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount;
	}

	@Override
	protected Iterable<ModelPart> getHeadParts() {
		return List.of(this.head, this.neck);
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return List.of(this.body, this.leftLeg, this.rightLeg);
	}
}