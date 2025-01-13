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
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TaiaoPlacedFeatures {
    public static final RegistryKey<PlacedFeature> CABBAGE_TREE_CHECKED = Taiao.createRegistryKey(
            "cabbage_tree_checked",
            RegistryKeys.PLACED_FEATURE
    );

    public static void bootstrapPlacedFeatures(@NotNull Registerable<PlacedFeature> registerable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> lookup = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(
                registerable, CABBAGE_TREE_CHECKED, lookup.getOrThrow(TaiaoConfiguredFeatures.CABBAGE_TREE),
                PlacedFeatures.wouldSurvive(TaiaoBlocks.CABBAGE_TREE_SAPLING)
        );
    }

    public static void register(
            @NotNull Registerable<PlacedFeature> registerable,
            RegistryKey<PlacedFeature> key,
            RegistryEntry<ConfiguredFeature<?, ?>> configuredFeature,
            PlacementModifier... modifiers
    ) {
        register(registerable, key, configuredFeature, List.of(modifiers));
    }

    public static void register(
            @NotNull Registerable<PlacedFeature> registerable,
            RegistryKey<PlacedFeature> key,
            RegistryEntry<ConfiguredFeature<?, ?>> configuredFeature,
            List<PlacementModifier> modifiers
    ) {
        registerable.register(key, new PlacedFeature(configuredFeature, List.copyOf(modifiers)));
    }
}
