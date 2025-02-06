// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.renderer;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.AustralasianBitternEntity;
import antikyth.taiao.entity.render.TaiaoEntityModels;
import antikyth.taiao.entity.render.model.AustralasianBitternEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class AustralasianBitternEntityRenderer extends
	MobEntityRenderer<AustralasianBitternEntity, AustralasianBitternEntityModel<AustralasianBitternEntity>> {
	public static final Identifier TEXTURE = Taiao.id("textures/entity/australasian_bittern.png");

	public AustralasianBitternEntityRenderer(EntityRendererFactory.Context context) {
		super(
			context,
			new AustralasianBitternEntityModel<>(context.getPart(TaiaoEntityModels.AUSTRALASIAN_BITTERN)),
			0.35f
		);
	}

	@Override
	public Identifier getTexture(AustralasianBitternEntity entity) {
		return TEXTURE;
	}
}
