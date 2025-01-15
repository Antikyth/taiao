// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.trunk;

import antikyth.taiao.Taiao;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class TaiaoTrunkPlacers {
    public static final TrunkPlacerType<BranchingTrunkPlacer> BRANCHING_TRUNK_PLACER = register(
            Taiao.id("branching_trunk_placer"),
            BranchingTrunkPlacer.CODEC
    );

    public static <P extends TrunkPlacer> TrunkPlacerType<P> register(Identifier id, Codec<P> codec) {
        return Registry.register(Registries.TRUNK_PLACER_TYPE, id, new TrunkPlacerType<>(codec));
    }

    public static void initialize() {
    }
}
