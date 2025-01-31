// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaiaoPlacedFeatures {
    protected static Map<RegistryKey<PlacedFeature>, Pair<RegistryKey<ConfiguredFeature<?, ?>>, List<PlacementModifier>>> TO_REGISTER = new HashMap<>();

    // Trees
    public static final RegistryKey<PlacedFeature> KAURI_TREE_CHECKED = register(
            Taiao.id("kauri_tree_checked"),
            TaiaoConfiguredFeatures.KAURI_TREE,
            PlacedFeatures.wouldSurvive(TaiaoBlocks.KAURI_SAPLING)
    );
    public static final RegistryKey<PlacedFeature> YOUNG_KAHIKATEA_TREE_CHECKED = register(
            Taiao.id("kahikatea_tree_checked"),
            TaiaoConfiguredFeatures.YOUNG_KAHIKATEA_TREE,
            PlacedFeatures.wouldSurvive(TaiaoBlocks.KAHIKATEA_SAPLING)
    );
    public static final RegistryKey<PlacedFeature> OLD_KAHIKATEA_TREE_CHECKED = register(
            Taiao.id("mega_kahikatea_tree_checked"),
            TaiaoConfiguredFeatures.OLD_KAHIKATEA_TREE,
            PlacedFeatures.wouldSurvive(TaiaoBlocks.KAHIKATEA_SAPLING)
    );
    public static final RegistryKey<PlacedFeature> RIMU_TREE_CHECKED = register(
            Taiao.id("mega_rimu_tree_checked"),
            TaiaoConfiguredFeatures.RIMU_TREE,
            PlacedFeatures.wouldSurvive(TaiaoBlocks.RIMU_SAPLING)
    );
    public static final RegistryKey<PlacedFeature> CABBAGE_TREE_CHECKED = register(
            Taiao.id("cabbage_tree_checked"),
            TaiaoConfiguredFeatures.CABBAGE_TREE,
            PlacedFeatures.wouldSurvive(TaiaoBlocks.CABBAGE_TREE_SAPLING)
    );
    public static final RegistryKey<PlacedFeature> MAMAKU_TREE_CHECKED = register(
            Taiao.id("mamaku_tree_checked"),
            TaiaoConfiguredFeatures.MAMAKU_TREE,
            PlacedFeatures.wouldSurvive(TaiaoBlocks.MAMAKU_SAPLING)
    );
    public static final RegistryKey<PlacedFeature> WHEKII_PONGA_TREE_CHECKED = register(
            Taiao.id("whekii_ponga_tree_checked"),
            TaiaoConfiguredFeatures.WHEKII_PONGA_TREE,
            PlacedFeatures.wouldSurvive(TaiaoBlocks.WHEKII_PONGA_SAPLING)
    );

    // Tree selectors
    public static final RegistryKey<PlacedFeature> NATIVE_FOREST_TREES = register(
            TaiaoConfiguredFeatures.NATIVE_FOREST_TREES.getValue(),
            TaiaoConfiguredFeatures.NATIVE_FOREST_TREES,
            treeModifiers(PlacedFeatures.createCountExtraModifier(10, 0.1f, 1), 0)
    );
    public static final RegistryKey<PlacedFeature> NATIVE_SWAMP_TREES = register(
            TaiaoConfiguredFeatures.NATIVE_SWAMP_TREES.getValue(),
            TaiaoConfiguredFeatures.NATIVE_SWAMP_TREES,
            treeModifiers(PlacedFeatures.createCountExtraModifier(2, 0.1f, 1), 2)
    );

    // Ground foliage patches
    public static final RegistryKey<PlacedFeature> NATIVE_FOREST_GRASS_PATCH = register(
            TaiaoConfiguredFeatures.NATIVE_FOREST_GRASS_PATCH.getValue(),
            TaiaoConfiguredFeatures.NATIVE_FOREST_GRASS_PATCH,
            VegetationPlacedFeatures.modifiers(7)
    );
    public static final RegistryKey<PlacedFeature> RAUPOO_PATCH = register(
            TaiaoConfiguredFeatures.RAUPOO_PATCH.getValue(),
            TaiaoConfiguredFeatures.RAUPOO_PATCH,
            // Modifiers
            RarityFilterPlacementModifier.of(3),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of()
    );

    public static void bootstrap(@NotNull Registerable<PlacedFeature> registerable) {
        Taiao.LOGGER.debug("Registering placed features");

        RegistryEntryLookup<ConfiguredFeature<?, ?>> lookup = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        for (Map.Entry<RegistryKey<PlacedFeature>, Pair<RegistryKey<ConfiguredFeature<?, ?>>, List<PlacementModifier>>> entry : TO_REGISTER.entrySet()) {
            Pair<RegistryKey<ConfiguredFeature<?, ?>>, List<PlacementModifier>> pair = entry.getValue();

            registerable.register(
                    entry.getKey(),
                    new PlacedFeature(lookup.getOrThrow(pair.getLeft()), pair.getRight())
            );
        }
    }

    public static @NotNull @Unmodifiable List<PlacementModifier> treeModifiers(
            PlacementModifier countModifier,
            int maxWaterDepth
    ) {
        return treeModifiersBuilder(countModifier, maxWaterDepth).build();
    }

    public static @NotNull @Unmodifiable List<PlacementModifier> treeModifiersWithWouldSurvive(
            PlacementModifier countModifier,
            int maxWaterDepth,
            Block block
    ) {
        return treeModifiersBuilder(countModifier, maxWaterDepth).add(PlacedFeatures.wouldSurvive(block)).build();
    }

    private static ImmutableList.@NotNull Builder<PlacementModifier> treeModifiersBuilder(
            PlacementModifier countModifier,
            int maxWaterDepth
    ) {
        return ImmutableList.<PlacementModifier>builder()
                .add(countModifier)
                .add(SquarePlacementModifier.of())
                .add(SurfaceWaterDepthFilterPlacementModifier.of(maxWaterDepth))
                .add(PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP)
                .add(BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> register(
            Identifier id,
            RegistryKey<ConfiguredFeature<?, ?>> configuredFeature,
            PlacementModifier... modifiers
    ) {
        return register(id, configuredFeature, List.of(modifiers));
    }

    public static RegistryKey<PlacedFeature> register(
            Identifier id,
            RegistryKey<ConfiguredFeature<?, ?>> configuredFeature,
            List<PlacementModifier> modifiers
    ) {
        RegistryKey<PlacedFeature> key = RegistryKey.of(RegistryKeys.PLACED_FEATURE, id);

        TO_REGISTER.put(key, new Pair<>(configuredFeature, List.copyOf(modifiers)));

        return key;
    }
}
