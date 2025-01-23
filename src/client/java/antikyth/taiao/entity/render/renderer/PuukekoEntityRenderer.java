// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.renderer;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.PuukekoEntity;
import antikyth.taiao.entity.render.TaiaoEntityModels;
import antikyth.taiao.entity.render.model.PuukekoEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class PuukekoEntityRenderer extends MobEntityRenderer<PuukekoEntity, PuukekoEntityModel<PuukekoEntity>> {
    private static final Identifier ADULT_TEXTURE = Taiao.id("textures/entity/puukeko/adult.png");
    private static final Identifier BABY_TEXTURE = Taiao.id("textures/entity/puukeko/baby.png");

    public PuukekoEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PuukekoEntityModel<>(context.getPart(TaiaoEntityModels.PUUKEKO)), 0.3f);
    }

    @Override
    public Identifier getTexture(PuukekoEntity entity) {
        return entity.isBaby() ? BABY_TEXTURE : ADULT_TEXTURE;
    }

    @Override
    protected float getAnimationProgress(PuukekoEntity entity, float tickDelta) {
        float flapProgress = MathHelper.lerp(tickDelta, entity.prevFlapProgress, entity.flapProgress);
        float maxWingDeviation = MathHelper.lerp(tickDelta, entity.prevMaxWingDeviation, entity.maxWingDeviation);

        return (MathHelper.sin(flapProgress) + 1.0F) * maxWingDeviation;
    }
}
