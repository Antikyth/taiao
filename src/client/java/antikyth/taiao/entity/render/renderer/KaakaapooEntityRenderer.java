// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.renderer;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.KaakaapooEntity;
import antikyth.taiao.entity.render.TaiaoEntityModels;
import antikyth.taiao.entity.render.model.KaakaapooEntityModel;
import antikyth.taiao.entity.render.renderer.feature.SleepyEyesFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class KaakaapooEntityRenderer extends MobEntityRenderer<KaakaapooEntity, KaakaapooEntityModel<KaakaapooEntity>> {
	protected static final Identifier TEXTURES = Taiao.id("textures/entity/kaakaapoo");

	public static final Identifier TEXTURE = TEXTURES.withPath(path -> String.format("%s/kaakaapoo.png", path));
	public static final Identifier SLEEPY_EYES_TEXTURE = TEXTURES.withPath(path -> String.format(
		"%s/sleepy_eyes.png",
		path
	));

	public KaakaapooEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new KaakaapooEntityModel<>(context.getPart(TaiaoEntityModels.KAAKAAPOO)), 0.45f);

		this.addFeature(new SleepyEyesFeatureRenderer<>(this) {
			@Override
			public Identifier getTexture(KaakaapooEntity entity) {
				return SLEEPY_EYES_TEXTURE;
			}
		});
	}

	@Override
	public Identifier getTexture(KaakaapooEntity entity) {
		return TEXTURE;
	}
}
