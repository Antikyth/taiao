// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.renderer;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.KiwiEntity;
import antikyth.taiao.entity.render.TaiaoEntityModels;
import antikyth.taiao.entity.render.model.KiwiEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class KiwiEntityRenderer extends MobEntityRenderer<KiwiEntity, KiwiEntityModel<KiwiEntity>> {
    public static final Identifier TEXTURE = Taiao.id("textures/entity/kiwi.png");

    public KiwiEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new KiwiEntityModel<>(context.getPart(TaiaoEntityModels.KIWI)), 0.3f);
    }

    @Override
    public Identifier getTexture(KiwiEntity entity) {
        return TEXTURE;
    }
}
