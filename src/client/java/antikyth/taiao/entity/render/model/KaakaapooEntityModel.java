// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

// Made with Blockbench 4.12.1
public class KaakaapooEntityModel<E extends Entity> extends AnimalModel<E> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public KaakaapooEntityModel(@NotNull ModelPart root) {
        super(false, 4f, 2f);

        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public static @NotNull TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(
                "head",
                ModelPartBuilder.create().uv(0, 18).cuboid(-2.5f, -3f, -5f, 5f, 5f, 5f)
                        .uv(20, 18).cuboid(-0.5f, 0f, -6f, 1f, 2f, 1f),
                ModelTransform.pivot(0f, 14f, -3f)
        );

        modelPartData.addChild(
                "body",
                ModelPartBuilder.create().uv(0, 0).cuboid(-2.5f, -7.5f, -1f, 7f, 7f, 11f),
                ModelTransform.of(-1f, 18f, -5f, -32.5f * (float) (Math.PI / 180.0), 0f, 0f)
        );
        modelPartData.addChild(
                "tail",
                ModelPartBuilder.create().uv(16, 18).cuboid(-3f, 0f, 0f, 6f, 0f, 8f),
                ModelTransform.pivot(0f, 19f, 6f)
        );

        modelPartData.addChild(
                "left_leg",
                ModelPartBuilder.create().uv(0, 28).cuboid(-1.5f, 0f, -3f, 3f, 4f, 3f),
                ModelTransform.pivot(2f, 20f, 0f)
        );
        modelPartData.addChild(
                "right_leg",
                ModelPartBuilder.create().uv(12, 28).cuboid(-1.5f, 0f, -3f, 3f, 4f, 3f),
                ModelTransform.pivot(-2f, 20f, 0f)
        );

        return TexturedModelData.of(modelData, 64, 64);
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
        this.head.pitch = headPitchDegrees * (float) (Math.PI / 180.0);
        this.head.yaw = headYawDegrees * (float) (Math.PI / 180.0);

        this.rightLeg.pitch = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
        this.leftLeg.pitch = MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount;
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.tail, this.leftLeg, this.rightLeg);
    }
}