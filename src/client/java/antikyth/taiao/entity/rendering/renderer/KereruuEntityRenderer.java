// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.rendering.renderer;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.KereruuEntity;
import antikyth.taiao.entity.rendering.TaiaoEntityModels;
import antikyth.taiao.entity.rendering.model.KereruuEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class KereruuEntityRenderer extends MobEntityRenderer<KereruuEntity, KereruuEntityModel<KereruuEntity>> {
	public static final Identifier TEXTURE = Taiao.id("textures/entity/kereruu.png");

	public KereruuEntityRenderer(EntityRendererFactory.Context context) {
		super(
			context,
			new KereruuEntityModel<>(context.getPart(TaiaoEntityModels.KERERUU)),
			0.25f
		);
	}

	@Override
	public Identifier getTexture(KereruuEntity entity) {
		return TEXTURE;
	}
}
