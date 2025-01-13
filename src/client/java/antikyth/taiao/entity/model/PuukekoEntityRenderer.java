// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.model;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.PuukekoEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.util.Identifier;

public class PuukekoEntityRenderer extends LivingEntityRenderer<PuukekoEntity, PuukekoEntityModel> {
    public PuukekoEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PuukekoEntityModel(context.getPart(TaiaoEntityModels.PUUKEKO_MODEL_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(PuukekoEntity entity) {
        return Taiao.id("textures/entity/puukeko.png");
    }
}
