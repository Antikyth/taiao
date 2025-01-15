// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class PuukekoEntityModel<E extends Entity> extends BigLeggedAnimalModel<E> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart beak;

    public PuukekoEntityModel(@NotNull ModelPart root) {
        super(false, 3.75f, 2f, true, 8f, 0.75f, 21.5f);

        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.beak = root.getChild(EntityModelPartNames.BEAK);
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG);
        this.leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightWing = root.getChild(EntityModelPartNames.RIGHT_WING);
        this.leftWing = root.getChild(EntityModelPartNames.LEFT_WING);
    }

    public static @NotNull TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(
                EntityModelPartNames.HEAD,
                ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F),
                ModelTransform.pivot(0.0F, 15.0F, -4.0F)
        );
        modelPartData.addChild(
                EntityModelPartNames.BEAK,
                ModelPartBuilder.create().uv(14, 0).cuboid(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F),
                ModelTransform.pivot(0.0F, 15.0F, -4.0F)
        );

        modelPartData.addChild(
                EntityModelPartNames.BODY,
                ModelPartBuilder.create().uv(0, 9).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F),
                ModelTransform.of(0.0F, 16.0F, 0.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
        );

        ModelPartBuilder legModel = ModelPartBuilder.create()
                .uv(26, 0)
                .cuboid(0f, 0.0F, -3.0F, 1.0f, 5.0F, 0f)
                .uv(28, 0)
                .cuboid(-2f, 5f, -6f, 5f, 0f, 5f);
        modelPartData.addChild(
                EntityModelPartNames.RIGHT_LEG,
                legModel,
                ModelTransform.pivot(-2.0F, 19.0F, 1.0F)
        );
        modelPartData.addChild(
                EntityModelPartNames.LEFT_LEG,
                legModel,
                ModelTransform.pivot(1.0F, 19.0F, 1.0F)
        );

        modelPartData.addChild(
                EntityModelPartNames.RIGHT_WING,
                ModelPartBuilder.create().uv(24, 13).cuboid(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F),
                ModelTransform.pivot(-4.0F, 13.0F, 0.0F)
        );
        modelPartData.addChild(
                EntityModelPartNames.LEFT_WING,
                ModelPartBuilder.create().uv(24, 13).cuboid(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F),
                ModelTransform.pivot(4.0F, 13.0F, 0.0F)
        );

        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    protected Iterable<ModelPart> getLegParts() {
        return ImmutableList.of(this.leftLeg, this.rightLeg);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head, this.beak);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.leftWing, this.rightWing);
    }

    @Override
    public void setAngles(
            E entity,
            float limbAngle,
            float limbDistance,
            float animationProgress,
            float headYaw,
            float headPitch
    ) {
        this.head.pitch = headPitch * (float) (Math.PI / 180.0);
        this.head.yaw = headYaw * (float) (Math.PI / 180.0);
        this.beak.pitch = this.head.pitch;
        this.beak.yaw = this.head.yaw;
        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * 1.4F * limbDistance;
        this.rightWing.roll = animationProgress;
        this.leftWing.roll = -animationProgress;
    }
}
