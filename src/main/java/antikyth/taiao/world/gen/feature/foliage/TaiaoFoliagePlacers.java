// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.foliage;

import antikyth.taiao.Taiao;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class TaiaoFoliagePlacers {
    public static final FoliagePlacerType<SingleFoliagePlacer> SINGLE_FOLIAGE_PLACER = register(
            "single_foliage_placer",
            SingleFoliagePlacer.CODEC
    );

    public static <P extends FoliagePlacer> FoliagePlacerType<P> register(String name, Codec<P> codec) {
        return Registry.register(Registries.FOLIAGE_PLACER_TYPE, Taiao.id(name), new FoliagePlacerType<>(codec));
    }

    public static void initialize() {
    }
}
