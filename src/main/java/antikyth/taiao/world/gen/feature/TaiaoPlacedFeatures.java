// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
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
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import org.jetbrains.annotations.NotNull;

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

    // Patches
    public static final RegistryKey<PlacedFeature> NATIVE_FOREST_TREES = register(
            TaiaoConfiguredFeatures.NATIVE_FOREST_TREES.getValue(),
            TaiaoConfiguredFeatures.NATIVE_FOREST_TREES,
            VegetationPlacedFeatures.treeModifiers(PlacedFeatures.createCountExtraModifier(10, 0.1f, 1))
    );
    public static final RegistryKey<PlacedFeature> NATIVE_FOREST_GRASS_PATCH = register(
            TaiaoConfiguredFeatures.NATIVE_FOREST_GRASS_PATCH.getValue(),
            TaiaoConfiguredFeatures.NATIVE_FOREST_GRASS_PATCH,
            VegetationPlacedFeatures.modifiers(7)
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
