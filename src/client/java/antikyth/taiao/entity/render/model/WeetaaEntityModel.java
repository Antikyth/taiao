package antikyth.taiao.entity.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
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
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild(
			"body",
			ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -9.0F, -1.0F, 6.0F, 6.0F, 16.0F, new Dilation(0.0F)),
			ModelTransform.pivot(0.0F, 25.0F, -5.0F)
		);

		ModelPartData neck = modelPartData.addChild(
			"neck",
			ModelPartBuilder.create().uv(28, 22).cuboid(-3.0F, 0.0F, -5.0F, 6.0F, 3.0F, 5.0F, new Dilation(0.0F)),
			ModelTransform.pivot(0.0F, 16.0F, -6.0F)
		);

		ModelPartData head = modelPartData.addChild(
			"head",
			ModelPartBuilder.create().uv(28, 30).cuboid(-3.0F, 0.0F, -2.0F, 6.0F, 6.0F, 3.0F, new Dilation(0.0F)),
			ModelTransform.pivot(0.0F, 16.0F, -12.0F)
		);

		ModelPartData antennae = head.addChild(
			"antennae",
			ModelPartBuilder.create().uv(0, 22).cuboid(-6.0F, -11.0F, 1.0F, 12.0F, 12.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.pivot(0.0F, 2.0F, -5.0F)
		);

		ModelPartData rear_left_leg = modelPartData.addChild(
			"rear_left_leg",
			ModelPartBuilder.create().uv(0, 36).cuboid(0.0F, -12.0F, 0.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(3.0F, 22.0F, 4.0F, -0.4363F, 0.2618F, 0.0F)
		);

		ModelPartData rear_left_leg_lower = rear_left_leg.addChild(
			"rear_left_leg_lower",
			ModelPartBuilder.create().uv(44, 0).cuboid(0.0F, 0.0F, -2.0F, 1.0F, 12.0F, 2.0F, new Dilation(0.0F))
				.uv(8, 42).cuboid(0.5F, 0.0F, 0.0F, 0.0F, 12.0F, 3.0F, new Dilation(0.0F)),
			ModelTransform.of(0.5F, -12.0F, 2.0F, 0.6109F, 0.0F, 0.0F)
		);

		ModelPartData rear_left_foot_r1 = rear_left_leg_lower.addChild(
			"rear_left_foot_r1",
			ModelPartBuilder.create().uv(8, 36).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 6.0F, new Dilation(0.0F)),
			ModelTransform.of(0.5F, 12.0F, -1.0F, -0.1745F, 0.0F, 0.0F)
		);

		ModelPartData rear_right_leg = modelPartData.addChild(
			"rear_right_leg",
			ModelPartBuilder.create().uv(24, 39).cuboid(-2.0F, -12.0F, 0.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(-3.0F, 22.0F, 4.0F, -0.4363F, -0.2618F, 0.0F)
		);

		ModelPartData rear_right_leg_lower = rear_right_leg.addChild(
			"rear_right_leg_lower",
			ModelPartBuilder.create().uv(32, 45).cuboid(0.0F, 0.0F, -2.0F, 1.0F, 12.0F, 2.0F, new Dilation(0.0F))
				.uv(14, 42).cuboid(0.5F, 0.0F, 0.0F, 0.0F, 12.0F, 3.0F, new Dilation(0.0F)),
			ModelTransform.of(-1.5F, -12.0F, 2.0F, 0.6109F, 0.0F, 0.0F)
		);

		ModelPartData rear_right_foot_r1 = rear_right_leg_lower.addChild(
			"rear_right_foot_r1",
			ModelPartBuilder.create().uv(32, 39).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 6.0F, new Dilation(0.0F)),
			ModelTransform.of(0.5F, 12.0F, -1.0F, -0.1745F, 0.0F, 0.0F)
		);
		return TexturedModelData.of(modelData, 64, 64);
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
	public void render(
		MatrixStack matrices,
		VertexConsumer vertexConsumer,
		int light,
		int overlay,
		float red,
		float green,
		float blue,
		float alpha
	) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		neck.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rear_left_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rear_right_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}