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
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TaiaoPlacedFeatures {
    public static final RegistryKey<PlacedFeature> KAURI_TREE_CHECKED = registryKey(Taiao.id("kauri_tree_checked"));
    public static final RegistryKey<PlacedFeature> CABBAGE_TREE_CHECKED = registryKey(
            Taiao.id("cabbage_tree_checked")
    );
    public static final RegistryKey<PlacedFeature> MAMAKU_TREE_CHECKED = registryKey(
            Taiao.id("mamaku_tree_checked")
    );
    public static final RegistryKey<PlacedFeature> WHEKII_PONGA_TREE_CHECKED = registryKey(
            Taiao.id("whekii_ponga_tree_checked")
    );

    public static final RegistryKey<PlacedFeature> NATIVE_FOREST_TREES = registryKey(Taiao.id("trees_native_forest"));

    public static void bootstrap(@NotNull Registerable<PlacedFeature> context) {
        Taiao.LOGGER.debug("Registering placed features");

        RegistryEntryLookup<ConfiguredFeature<?, ?>> lookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        // Trees
        register(
                context,
                KAURI_TREE_CHECKED,
                lookup.getOrThrow(TaiaoConfiguredFeatures.KAURI_TREE),
                PlacedFeatures.wouldSurvive(TaiaoBlocks.KAURI_SAPLING)
        );
        register(
                context, CABBAGE_TREE_CHECKED, lookup.getOrThrow(TaiaoConfiguredFeatures.CABBAGE_TREE),
                PlacedFeatures.wouldSurvive(TaiaoBlocks.CABBAGE_TREE_SAPLING)
        );
        register(
                context,
                MAMAKU_TREE_CHECKED,
                lookup.getOrThrow(TaiaoConfiguredFeatures.MAMAKU_TREE),
                PlacedFeatures.wouldSurvive(TaiaoBlocks.MAMAKU_SAPLING)
        );
        register(
                context,
                WHEKII_PONGA_TREE_CHECKED,
                lookup.getOrThrow(TaiaoConfiguredFeatures.WHEKII_PONGA_TREE),
                PlacedFeatures.wouldSurvive(TaiaoBlocks.WHEKII_PONGA_SAPLING)
        );

        register(
                context,
                NATIVE_FOREST_TREES,
                lookup.getOrThrow(TaiaoConfiguredFeatures.NATIVE_FOREST_TREES),
                VegetationPlacedFeatures.treeModifiers(PlacedFeatures.createCountExtraModifier(10, 0.1f, 1))
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

    public static RegistryKey<PlacedFeature> registryKey(Identifier id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, id);
    }
}
