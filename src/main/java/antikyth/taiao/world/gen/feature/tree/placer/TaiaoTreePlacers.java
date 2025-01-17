// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.placer;

import antikyth.taiao.Taiao;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class TaiaoTreePlacers {
    public static final TrunkPlacerType<ThinSplittingTrunkPlacer> THIN_SPLITTING_TRUNK_PLACER = registerTrunkPlacer(
            Taiao.id("thin_splitting_trunk_placer"),
            ThinSplittingTrunkPlacer.CODEC
    );

    public static final FoliagePlacerType<SphericalFoliagePlacer> SPHERICAL_FOLIAGE_PLACER = registerFoliagePlacer(
            Taiao.id("spherical_foliage_placer"),
            SphericalFoliagePlacer.CODEC
    );

    public static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunkPlacer(Identifier id, Codec<P> codec) {
        return Registry.register(Registries.TRUNK_PLACER_TYPE, id, new TrunkPlacerType<>(codec));
    }

    public static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliagePlacer(Identifier id, Codec<P> codec) {
        return Registry.register(Registries.FOLIAGE_PLACER_TYPE, id, new FoliagePlacerType<>(codec));
    }

    public static void initialize() {
    }
}
