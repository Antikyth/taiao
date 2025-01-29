// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.ModelWithWaterPatch;
import net.minecraft.entity.vehicle.BoatEntity;

// Made with Blockbench 4.12.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class WakaEntityModel<E extends BoatEntity> extends CompositeEntityModel<E> implements ModelWithWaterPatch {
    private final ModelPart body;
    private final ModelPart waterPatch;

    public WakaEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.waterPatch = root.getChild("water_patch");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(
                "body",
                ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -23.0F, 0.0F, 20.0F, 46.0F, 3.0F, new Dilation(0.0F))
                        .uv(46, 45).cuboid(8.0F, -23.0F, 3.0F, 2.0F, 46.0F, 6.0F, new Dilation(0.0F))
                        .uv(0, 49).cuboid(-10.0F, -23.0F, 3.0F, 2.0F, 46.0F, 6.0F, new Dilation(0.0F))
                        .uv(62, 45).cuboid(-8.0F, 21.0F, 3.0F, 16.0F, 2.0F, 6.0F, new Dilation(0.0F))
                        .uv(62, 53).cuboid(-8.0F, -23.0F, 3.0F, 16.0F, 2.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 27.0F, 0.0F, 1.5708F, 0.0F, 0.0F)
        );

        modelPartData.addChild(
                "water_patch",
                ModelPartBuilder.create().uv(46, 0).cuboid(-8.0F, -21.0F, 0.0F, 16.0F, 42.0F, 3.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 24.0F, 0.0F, 1.5708F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(modelData, 128, 128);
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
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public ModelPart getWaterPatch() {
        return this.waterPatch;
    }
}