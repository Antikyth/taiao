// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public abstract class BigLeggedAnimalModel<E extends Entity> extends AnimalModel<E> {
    private final boolean legsScaled;
    private final float childLegsYOffset;
    private final float childLegsZOffset;
    private final float invertedChildLegsScale;

    protected BigLeggedAnimalModel() {
        this(false, 5f, 2f, false, 5f, 0f, 24f);
    }

    protected BigLeggedAnimalModel(
            boolean headScaled,
            float childHeadYOffset,
            float childHeadZOffset,
            boolean legsScaled,
            float childLegsYOffset,
            float childLegsZOffset,
            float childBodyYOffset
    ) {
        this(
                headScaled,
                childHeadYOffset,
                childHeadZOffset,
                legsScaled,
                childLegsYOffset,
                childLegsZOffset,
                2f,
                2f,
                2f,
                childBodyYOffset
        );
    }

    protected BigLeggedAnimalModel(
            boolean headScaled,
            float childHeadYOffset,
            float childHeadZOffset,
            boolean legsScaled,
            float childLegsYOffset,
            float childLegsZOffset,
            float invertedChildHeadScale,
            float invertedChildLegsScale,
            float invertedChildBodyScale,
            float childBodyYOffset
    ) {
        this(
                RenderLayer::getEntityCutoutNoCull,
                headScaled,
                childHeadYOffset,
                childHeadZOffset,
                legsScaled,
                childLegsYOffset,
                childLegsZOffset,
                invertedChildHeadScale,
                invertedChildLegsScale,
                invertedChildBodyScale,
                childBodyYOffset
        );
    }

    protected BigLeggedAnimalModel(
            Function<Identifier, RenderLayer> renderLayerFactory,
            boolean headScaled,
            float childHeadYOffset,
            float childHeadZOffset,
            boolean legsScaled,
            float childLegsYOffset,
            float childLegsZOffset,
            float invertedChildHeadScale,
            float invertedChildLegsScale,
            float invertedChildBodyScale,
            float childBodyYOffset
    ) {
        super(
                renderLayerFactory,
                headScaled,
                childHeadYOffset,
                childHeadZOffset,
                invertedChildHeadScale,
                invertedChildBodyScale,
                childBodyYOffset
        );

        this.legsScaled = legsScaled;
        this.childLegsYOffset = childLegsYOffset;
        this.childLegsZOffset = childLegsZOffset;

        this.invertedChildLegsScale = invertedChildLegsScale;
    }

    @Override
    public void render(
            MatrixStack matrices,
            VertexConsumer vertices,
            int light,
            int overlay,
            float red,
            float green,
            float blue,
            float alpha
    ) {
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha);

        if (this.child) {
            matrices.push();

            if (this.legsScaled) {
                float legScaleFactor = 1.5f / this.invertedChildLegsScale;
                matrices.scale(legScaleFactor, legScaleFactor, legScaleFactor);
            }

            matrices.translate(0f, this.childLegsYOffset / 16f, this.childLegsZOffset / 16f);
            this.getLegParts()
                    .forEach(legPart -> legPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));

            matrices.pop();
        } else {
            this.getLegParts()
                    .forEach(legPart -> legPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
        }
    }

    protected abstract Iterable<ModelPart> getLegParts();
}
