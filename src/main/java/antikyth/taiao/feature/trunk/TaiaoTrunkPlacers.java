package antikyth.taiao.feature.trunk;

import antikyth.taiao.Taiao;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class TaiaoTrunkPlacers {
    public static final TrunkPlacerType<BranchingTrunkPlacer> BRANCHING_TRUNK_PLACER = register(
            "branching_trunk_placer",
            BranchingTrunkPlacer.CODEC
    );

    public static <P extends TrunkPlacer> TrunkPlacerType<P> register(String name, Codec<P> codec) {
        return Registry.register(Registries.TRUNK_PLACER_TYPE, Taiao.id(name), new TrunkPlacerType<>(codec));
    }

    public static void initialize() {
    }
}