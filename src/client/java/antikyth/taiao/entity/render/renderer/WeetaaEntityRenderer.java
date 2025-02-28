// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.renderer;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.WeetaaEntity;
import antikyth.taiao.entity.render.TaiaoEntityModels;
import antikyth.taiao.entity.render.model.WeetaaEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class WeetaaEntityRenderer extends
	MobEntityRenderer<WeetaaEntity, WeetaaEntityModel<WeetaaEntity>> {
	public static final Identifier TEXTURE = Taiao.id("textures/entity/weetaa.png");

	public WeetaaEntityRenderer(EntityRendererFactory.Context context) {
		super(
			context,
			new WeetaaEntityModel<>(context.getPart(TaiaoEntityModels.WEETAA)),
			0.7f
		);
	}

	@Override
	public Identifier getTexture(WeetaaEntity entity) {
		return TEXTURE;
	}
}
