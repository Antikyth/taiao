// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.biome;

import antikyth.taiao.Taiao;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import org.jetbrains.annotations.NotNull;

public class TaiaoSurfaceRules {
    public static MaterialRules.@NotNull MaterialRule createSurfaceRules() {
        Taiao.LOGGER.debug("Creating world gen surface rules");

        return MaterialRules.sequence();
    }
}
