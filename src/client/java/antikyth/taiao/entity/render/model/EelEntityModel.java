package antikyth.taiao.entity.render.model;

import antikyth.taiao.TaiaoClient;
import antikyth.taiao.entity.EelEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

// Made with Blockbench 4.12.2
public class EelEntityModel<E extends EelEntity> extends SinglePartEntityModel<E> {
	private final ModelPart root;

	private final ModelPart tail;

	public EelEntityModel(@NotNull ModelPart root) {
		this.root = root;
		this.tail = root.getChild("body_back");
	}

	public static @NotNull TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild(
			"body_back",
			ModelPartBuilder.create().uv(0, 0).cuboid(-1f, 0f, -1f, 2f, 14f, 2f)
				.uv(12, 0).cuboid(0f, 0f, 1f, 0f, 14f, 2f)
				.uv(8, 0).cuboid(0f, 0f, -3f, 0f, 14f, 2f),
			ModelTransform.of(0f, 21f, 0f, TaiaoClient.degreesToRadians(90f), 0f, 0f)
		);
		modelPartData.addChild(
			"body_front",
			ModelPartBuilder.create().uv(0, 16).cuboid(-1f, -9f, -1f, 2f, 9f, 2f)
				.uv(16, 7).cuboid(0f, -7f, 1f, 0f, 7f, 2f),
			ModelTransform.of(0f, 21f, 0f, TaiaoClient.degreesToRadians(90f), 0f, 0f)
		);

		modelPartData.addChild(
			"pectoral_fin_left",
			ModelPartBuilder.create().uv(12, 16).cuboid(0f, 0f, -1f, 0f, 3f, 2f),
			ModelTransform.of(1f, 21f, -9f, TaiaoClient.degreesToRadians(90f), TaiaoClient.degreesToRadians(20f), 0f)
		);
		modelPartData.addChild(
			"pectoral_fin_right",
			ModelPartBuilder.create().uv(8, 16).cuboid(0f, 0f, -1f, 0f, 3f, 2f),
			ModelTransform.of(-1f, 21f, -9f, TaiaoClient.degreesToRadians(90f), TaiaoClient.degreesToRadians(-20f), 0f)
		);

		modelPartData.addChild(
			"head",
			ModelPartBuilder.create().uv(16, 0).cuboid(-1f, -5f, -1f, 2f, 5f, 2f),
			ModelTransform.of(0f, 21f, -9f, TaiaoClient.degreesToRadians(90f), 0f, 0f)
		);

		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(
		@NotNull E entity,
		float limbSwing,
		float limbSwingAmount,
		float ageInTicks,
		float netHeadYaw,
		float headPitch
	) {
		this.tail.yaw = -entity.getYawMultiplier() * 0.25f * MathHelper.sin(entity.getYawAngleMultiplier() * 0.6f * ageInTicks);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}