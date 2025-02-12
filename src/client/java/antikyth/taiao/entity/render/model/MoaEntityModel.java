// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.model;

import antikyth.taiao.TaiaoClient;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

// Made with Blockbench 4.11.2
public class MoaEntityModel<E extends AnimalEntity> extends AnimalModel<E> {
	private final ModelPart body;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart head;

	public MoaEntityModel(@NotNull ModelPart root) {
		super(true, 14.75f, 4f);

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
			ModelPartBuilder.create()
				.uv(0, 0)
				.cuboid(-9f, -34f, -12f, 18f, 16f, 28f),
			ModelTransform.pivot(0f, 25f, 0f)
		);

		modelPartData.addChild(
			"left_leg",
			ModelPartBuilder.create().uv(16, 52).cuboid(-2f, 0f, -2f, 4f, 17f, 4f)
				.uv(8, 44).cuboid(-4f, 17f, -7f, 8f, 0f, 8f),
			ModelTransform.pivot(4f, 7f, -2f)
		);
		modelPartData.addChild(
			"right_leg",
			ModelPartBuilder.create().uv(32, 52).cuboid(-2f, 0f, -2f, 4f, 17f, 4f)
				.uv(24, 44).cuboid(-4f, 17f, -7f, 8f, 0f, 8f),
			ModelTransform.pivot(-4f, 7f, -2f)
		);

		modelPartData.addChild(
			"head",
			ModelPartBuilder.create().uv(0, 44).cuboid(-2f, -19f, -4f, 4f, 25f, 4f)
				.uv(48, 44).cuboid(-2f, -18f, -8f, 4f, 3f, 4f),
			ModelTransform.pivot(0f, -2f, -12f)
		);

		return TexturedModelData.of(modelData, 128, 128);
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
		headPitchDegrees = MathHelper.clamp(headPitchDegrees, -25f, 45f);

		this.head.yaw = TaiaoClient.degreesToRadians(headYawDegrees);
		this.head.pitch = TaiaoClient.degreesToRadians(headPitchDegrees);

		this.leftLeg.pitch = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
		this.rightLeg.pitch = MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount;
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