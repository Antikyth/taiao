package antikyth.taiao.entity.render;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class MoaEntityModel<E extends AnimalEntity> extends AnimalModel<E> {
    private final ModelPart body;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart head;

    public MoaEntityModel(@NotNull ModelPart root) {
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
                        .cuboid(-9.0F, -34.0F, -12.0F, 18.0F, 16.0F, 28.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 25.0F, 0.0F)
        );

        modelPartData.addChild(
                "left_leg",
                ModelPartBuilder.create().uv(16, 52).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 17.0F, 4.0F, new Dilation(0.0F))
                        .uv(8, 44).cuboid(-4.0F, 17.0F, -7.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.pivot(4.0F, 7.0F, -2.0F)
        );
        modelPartData.addChild(
                "right_leg",
                ModelPartBuilder.create().uv(32, 52).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 17.0F, 4.0F, new Dilation(0.0F))
                        .uv(24, 44).cuboid(-4.0F, 17.0F, -7.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-4.0F, 7.0F, -2.0F)
        );

        modelPartData.addChild(
                "head",
                ModelPartBuilder.create().uv(0, 44).cuboid(-2.0F, -19.0F, -4.0F, 4.0F, 25.0F, 4.0F, new Dilation(0.0F))
                        .uv(48, 44).cuboid(-2.0F, -18.0F, -8.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, -2.0F, -12.0F)
        );

        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(
            E entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float headYaw,
            float headPitch
    ) {
        headYaw = MathHelper.clamp(headYaw, -30f, 30f);
        headPitch = MathHelper.clamp(headPitch, -25f, 45f);

        this.head.yaw = headYaw * (float) (Math.PI / 180.0);
        this.head.pitch = headPitch * (float) (Math.PI / 180.0);

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