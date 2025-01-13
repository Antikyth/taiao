// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.model;

import antikyth.taiao.entity.TaiaoEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class TaiaoEntityModels {
    public static void initialize() {
        EntityRendererRegistry.register(TaiaoEntities.PUUKEKO, PuukekoEntityRenderer::new);
    }

    public static EntityModelLayer registerModelLayer(
            EntityModelLayer layer,
            EntityModelLayerRegistry.TexturedModelDataProvider texturedModelDataProvider
    ) {
        EntityModelLayerRegistry.registerModelLayer(layer, texturedModelDataProvider);

        return layer;
    }
}
