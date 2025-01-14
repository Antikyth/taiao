// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.MoaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class MoaEntityRenderer extends MobEntityRenderer<MoaEntity, MoaEntityModel<MoaEntity>> {
    private static final Identifier TEXTURE = Taiao.id("textures/entity/moa.png");

    public MoaEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MoaEntityModel<>(context.getPart(TaiaoEntityModels.MOA)), 1f);
    }

    @Override
    public Identifier getTexture(MoaEntity entity) {
        return TEXTURE;
    }
}
