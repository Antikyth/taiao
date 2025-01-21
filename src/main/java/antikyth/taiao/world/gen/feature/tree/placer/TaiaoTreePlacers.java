// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.placer;

import antikyth.taiao.Taiao;
import antikyth.taiao.world.gen.feature.tree.placer.foliage.PalmFoliagePlacer;
import antikyth.taiao.world.gen.feature.tree.placer.foliage.SphericalFoliagePlacer;
import antikyth.taiao.world.gen.feature.tree.placer.trunk.ThinSplittingTrunkPlacer;
import antikyth.taiao.world.gen.feature.tree.placer.trunk.ThinStraightTrunkPlacer;
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
    public static final TrunkPlacerType<ThinStraightTrunkPlacer> THIN_STRAIGHT_TRUNK_PLACER = registerTrunkPlacer(
            Taiao.id("thin_straight_trunk_placer"),
            ThinStraightTrunkPlacer.CODEC
    );

    public static final FoliagePlacerType<SphericalFoliagePlacer> SPHERICAL_FOLIAGE_PLACER = registerFoliagePlacer(
            Taiao.id("spherical_foliage_placer"),
            SphericalFoliagePlacer.CODEC
    );
    public static final FoliagePlacerType<PalmFoliagePlacer> PALM_FOLIAGE_PLACER = registerFoliagePlacer(
            Taiao.id("palm_foliage_placer"),
            PalmFoliagePlacer.CODEC
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
