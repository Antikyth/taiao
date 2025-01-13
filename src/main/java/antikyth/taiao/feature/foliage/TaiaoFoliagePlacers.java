package antikyth.taiao.feature.foliage;

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
