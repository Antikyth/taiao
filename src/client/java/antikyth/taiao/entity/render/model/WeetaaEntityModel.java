// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

// Made with Blockbench 4.12.2
public class WeetaaEntityModel<E extends Entity> extends SinglePartEntityModel<E> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart antennae;
	private final ModelPart rear_left_leg;
	private final ModelPart rear_left_leg_lower;
	private final ModelPart rear_right_leg;
	private final ModelPart rear_right_leg_lower;
	private final ModelPart middle_left_leg;
	private final ModelPart middle_right_leg;
	private final ModelPart front_left_leg;
	private final ModelPart front_right_leg;

	public WeetaaEntityModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.neck = root.getChild("neck");
		this.head = root.getChild("head");
		this.antennae = this.head.getChild("antennae");
		this.rear_left_leg = root.getChild("rear_left_leg");
		this.rear_left_leg_lower = this.rear_left_leg.getChild("rear_left_leg_lower");
		this.rear_right_leg = root.getChild("rear_right_leg");
		this.rear_right_leg_lower = this.rear_right_leg.getChild("rear_right_leg_lower");
		this.middle_left_leg = root.getChild("middle_left_leg");
		this.middle_right_leg = root.getChild("middle_right_leg");
		this.front_left_leg = root.getChild("front_left_leg");
		this.front_right_leg = root.getChild("front_right_leg");
	}

	public static @NotNull TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		root.addChild(
			"body",
			ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -9.0F, -1.0F, 6.0F, 6.0F, 14.0F, new Dilation(0.0F)),
			ModelTransform.pivot(0.0F, 25.0F, -2.0F)
		);

		root.addChild(
			"neck",
			ModelPartBuilder.create().uv(28, 31).cuboid(-3.0F, 0.0F, -4.0F, 6.0F, 3.0F, 4.0F, new Dilation(0.0F)),
			ModelTransform.pivot(0.0F, 16.0F, -3.0F)
		);

		ModelPartData head = root.addChild(
			"head",
			ModelPartBuilder.create().uv(28, 20).cuboid(-3.0F, 0.0F, -4.0F, 6.0F, 7.0F, 4.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 16.0F, -7.0F, -0.3927F, 0.0F, 0.0F)
		);
		head.addChild(
			"antennae",
			ModelPartBuilder.create().uv(0, 20).cuboid(-6.0F, -11.0F, -2.0F, 12.0F, 12.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.pivot(0.0F, 2.0F, -4.0F)
		);

		ModelPartData rear_left_leg = root.addChild(
			"rear_left_leg",
			ModelPartBuilder.create()
				.uv(0, 34)
				.cuboid(0.0F, -13.8126F, -0.8452F, 2.0F, 14.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(3.0F, 22.0F, 3.0F, -0.4363F, 0.2618F, 0.0F)
		);
		ModelPartData rear_left_leg_lower = rear_left_leg.addChild(
			"rear_left_leg_lower",
			ModelPartBuilder.create().uv(28, 38).cuboid(0.0F, 0.0F, -2.0F, 1.0F, 14.0F, 2.0F, new Dilation(0.0F))
				.uv(16, 34).cuboid(0.5F, 0.0F, 0.0F, 0.0F, 14.0F, 3.0F, new Dilation(0.0F)),
			ModelTransform.of(0.5F, -13.8126F, 1.1548F, 0.6109F, 0.0F, 0.0F)
		);
		rear_left_leg_lower.addChild(
			"rear_left_foot",
			ModelPartBuilder.create().uv(40, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 6.0F, new Dilation(0.0F)),
			ModelTransform.of(0.5F, 14.0F, -1.0F, -0.1745F, 0.0F, 0.0F)
		);

		ModelPartData rear_right_leg = root.addChild(
			"rear_right_leg",
			ModelPartBuilder.create()
				.uv(8, 34)
				.cuboid(-2.0F, -13.8126F, -0.8452F, 2.0F, 14.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(-3.0F, 22.0F, 3.0F, -0.4363F, -0.2618F, 0.0F)
		);
		ModelPartData rear_right_leg_lower = rear_right_leg.addChild(
			"rear_right_leg_lower",
			ModelPartBuilder.create().uv(34, 38).cuboid(0.0F, 0.0F, -2.0F, 1.0F, 14.0F, 2.0F, new Dilation(0.0F))
				.uv(22, 34).cuboid(0.5F, 0.0F, 0.0F, 0.0F, 14.0F, 3.0F, new Dilation(0.0F)),
			ModelTransform.of(-1.5F, -13.8126F, 1.1548F, 0.6109F, 0.0F, 0.0F)
		);
		rear_right_leg_lower.addChild(
			"rear_right_foot",
			ModelPartBuilder.create().uv(40, 6).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 6.0F, new Dilation(0.0F)),
			ModelTransform.of(0.5F, 14.0F, -1.0F, -0.1745F, 0.0F, 0.0F)
		);

		ModelPartData middle_left_leg = root.addChild(
			"middle_left_leg",
			ModelPartBuilder.create().uv(48, 48).cuboid(6.0F, -7.0F, -0.5F, 1.0F, 9.0F, 1.0F, new Dilation(0.0F))
				.uv(12, 51).cuboid(7.0F, 2.0F, -0.5F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(3.0F, 22.0F, -4.0F, 0.0F, -0.3491F, 0.0F)
		);
		middle_left_leg.addChild(
			"cube_r1",
			ModelPartBuilder.create().uv(40, 38).cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(7.0F, -7.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
		);

		ModelPartData middle_right_leg = root.addChild(
			"middle_right_leg",
			ModelPartBuilder.create().uv(0, 50).cuboid(6.0F, -7.0F, -0.5F, 1.0F, 9.0F, 1.0F, new Dilation(0.0F))
				.uv(12, 52).cuboid(7.0F, 2.0F, -0.5F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(-3.0F, 22.0F, -4.0F, 0.0F, -2.7925F, 0.0F)
		);
		middle_right_leg.addChild(
			"cube_r2",
			ModelPartBuilder.create().uv(48, 12).cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(7.0F, -7.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
		);

		ModelPartData front_left_leg = root.addChild(
			"front_left_leg",
			ModelPartBuilder.create().uv(52, 48).cuboid(10.75F, 3.0F, -0.5F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(3.0F, 21.0F, -7.0F, 0.0F, 0.5236F, 0.0F)
		);
		front_left_leg.addChild(
			"cube_r3",
			ModelPartBuilder.create().uv(4, 50).cuboid(-1.0F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(7.0F, -4.0F, 0.0F, 0.0F, 0.0F, -0.6109F)
		);
		front_left_leg.addChild(
			"cube_r4",
			ModelPartBuilder.create().uv(48, 24).cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(7.0F, -4.0F, 0.0F, 0.0F, 0.0F, 1.0472F)
		);

		ModelPartData front_right_leg = root.addChild(
			"front_right_leg",
			ModelPartBuilder.create().uv(52, 49).cuboid(10.75F, 3.0F, -0.5F, 4.0F, 0.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(-3.0F, 21.0F, -7.0F, 0.0F, 2.618F, 0.0F)
		);
		front_right_leg.addChild(
			"cube_r5",
			ModelPartBuilder.create().uv(8, 50).cuboid(-1.0F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(7.0F, -4.0F, 0.0F, 0.0F, 0.0F, -0.6109F)
		);
		front_right_leg.addChild(
			"cube_r6",
			ModelPartBuilder.create().uv(48, 36).cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(7.0F, -4.0F, 0.0F, 0.0F, 0.0F, 1.0472F)
		);

		return TexturedModelData.of(data, 64, 64);
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
	public ModelPart getPart() {
		return this.root;
	}
}