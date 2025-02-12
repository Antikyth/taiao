// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.render.renderer;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.KiwiEntity;
import antikyth.taiao.entity.render.TaiaoEntityModels;
import antikyth.taiao.entity.render.model.KiwiEntityModel;
import antikyth.taiao.entity.render.renderer.feature.SleepyEyesFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class KiwiEntityRenderer extends MobEntityRenderer<KiwiEntity, KiwiEntityModel<KiwiEntity>> {
	public static final Identifier TEXTURES = Taiao.id("textures/entity/kiwi");

	public KiwiEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new KiwiEntityModel<>(context.getPart(TaiaoEntityModels.KIWI)), 0.3f);

		this.addFeature(new SleepyEyesFeatureRenderer<>(this) {
			@Override
			public Identifier getTexture(KiwiEntity kiwi) {
				return TEXTURES.withPath(path -> String.format(
					"%s/sleepy_eyes/%s.png",
					path,
					kiwi.getColor().asString()
				));
			}
		});
	}

	@Override
	public Identifier getTexture(@NotNull KiwiEntity kiwi) {
		return TEXTURES.withPath(path -> String.format("%s/%s.png", path, kiwi.getColor().asString()));
	}
}
