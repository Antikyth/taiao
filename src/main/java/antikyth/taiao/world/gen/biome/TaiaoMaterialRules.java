// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.biome;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.MaterialRules.MaterialRule;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TaiaoMaterialRules {
    protected static final int SEA_LEVEL = 63;

    /**
     * A top layer of coarse dirt.
     */
    public static final MaterialRule COARSE_DIRT_SURFACE = floor(block(Blocks.COARSE_DIRT));
    /**
     * A top layer of podzol (where there is no water).
     */
    public static final MaterialRule PODZOL_SURFACE = floor(noWater(block(Blocks.PODZOL)));

    /**
     * Podzol and coarse dirt mixed into the existing surface.
     */
    public static final MaterialRule PARTIAL_PODZOL_COARSE_DIRT_SURFACE = MaterialRules.sequence(
            surfaceNoiseAbove(1.75, COARSE_DIRT_SURFACE),
            surfaceNoiseAbove(-0.95, PODZOL_SURFACE)
    );
    /**
     * Swamp water mixed into the existing surface.
     */
    public static final MaterialRule SWAMP_SURFACE = yBetween(
            SEA_LEVEL - 1,
            SEA_LEVEL,
            MaterialRules.condition(
                    MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE_SWAMP, 0d),
                    block(Blocks.WATER)
            )
    );

    /**
     * The forest floor of the {@link TaiaoBiomes#NATIVE_FOREST}.
     */
    public static final MaterialRule NATIVE_FOREST = biomeSurface(
            TaiaoBiomes.NATIVE_FOREST,
            PARTIAL_PODZOL_COARSE_DIRT_SURFACE
    );
    /**
     * The swampy water of the {@link TaiaoBiomes#NATIVE_SWAMP}.
     */
    public static final MaterialRule NATIVE_SWAMP = biomeSurface(TaiaoBiomes.NATIVE_SWAMP, SWAMP_SURFACE);

    public static final MaterialRule ALL = MaterialRules.sequence(NATIVE_FOREST, NATIVE_SWAMP);

    // =================================================================================================================
    // Utility methods
    // =================================================================================================================

    /**
     * Applies within the estimated surface zone of the given {@code biome}.
     */
    @Contract("_, _ -> new")
    public static @NotNull MaterialRule biomeSurface(RegistryKey<Biome> biome, MaterialRule rule) {
        return MaterialRules.condition(
                MaterialRules.biome(biome),
                MaterialRules.condition(MaterialRules.surface(), rule)
        );
    }

    /**
     * Applies on the top layer of the surface.
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MaterialRule floor(MaterialRule rule) {
        return MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, rule);
    }

    /**
     * Applies where no water is present.
     */
    @Contract("_ -> new")
    public static @NotNull MaterialRule noWater(MaterialRule rule) {
        return MaterialRules.condition(MaterialRules.water(0, 0), rule);
    }

    /**
     * Applies where the surface noise is above the given {@code min}.
     */
    @Contract("_, _ -> new")
    public static @NotNull MaterialRule surfaceNoiseAbove(double min, MaterialRule rule) {
        return surfaceNoiseBand(min, Double.MAX_VALUE, rule);
    }

    /**
     * Applies where the surface noise is between the given {@code min} and {@code max}.
     */
    @Contract("_, _, _ -> new")
    public static @NotNull MaterialRule surfaceNoiseBand(double min, double max, MaterialRule rule) {
        return MaterialRules.condition(
                MaterialRules.noiseThreshold(
                        NoiseParametersKeys.SURFACE,
                        min / 8.25,
                        max / 8.25
                ),
                rule
        );
    }

    @Contract("_, _, _ -> new")
    public static @NotNull MaterialRule yBetween(int min, int max, MaterialRule rule) {
        return MaterialRules.condition(
                MaterialRules.aboveY(YOffset.fixed(min), 0),
                MaterialRules.condition(MaterialRules.not(MaterialRules.aboveY(YOffset.fixed(max), 0)), rule)
        );
    }

    /**
     * Applies the given {@code block}.
     */
    @Contract("_ -> new")
    public static @NotNull MaterialRule block(@NotNull Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
