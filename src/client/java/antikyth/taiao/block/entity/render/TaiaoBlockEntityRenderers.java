// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity.render;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.entity.TaiaoBlockEntities;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class TaiaoBlockEntityRenderers {
	public static void initialize() {
		Taiao.LOGGER.debug("Registering block entity renderers");

		BlockEntityRendererFactories.register(TaiaoBlockEntities.HIINAKI, HiinakiBlockEntityRenderer::new);
	}
}
