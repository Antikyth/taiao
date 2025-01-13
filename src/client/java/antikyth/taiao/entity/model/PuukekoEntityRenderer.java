// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.model;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.PuukekoEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class PuukekoEntityRenderer extends MobEntityRenderer<PuukekoEntity, ChickenEntityModel<PuukekoEntity>> {
    private static final Identifier TEXTURE = Taiao.id("textures/entity/puukeko.png");

    public PuukekoEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ChickenEntityModel<>(context.getPart(EntityModelLayers.CHICKEN)), 0.3f);
    }

    @Override
    public Identifier getTexture(PuukekoEntity entity) {
        return TEXTURE;
    }

    @Override
    protected float getAnimationProgress(PuukekoEntity entity, float tickDelta) {
        float flapProgress = MathHelper.lerp(tickDelta, entity.prevFlapProgress, entity.flapProgress);
        float maxWingDeviation = MathHelper.lerp(tickDelta, entity.prevMaxWingDeviation, entity.maxWingDeviation);

        return (MathHelper.sin(flapProgress) + 1.0F) * maxWingDeviation;
    }
}
