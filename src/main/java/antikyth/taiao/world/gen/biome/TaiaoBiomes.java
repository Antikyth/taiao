// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.biome;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.world.gen.feature.TaiaoPlacedFeatures;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class TaiaoBiomes {
    protected static Map<RegistryKey<Biome>, BiFunction<RegistryEntryLookup<PlacedFeature>, RegistryEntryLookup<ConfiguredCarver<?>>, Biome>> TO_REGISTER = new HashMap<>();

    public static final RegistryKey<Biome> NATIVE_FOREST = register(
            Taiao.id("native_forest"),
            TaiaoBiomes::nativeForest
    );

    public static Biome nativeForest(
            RegistryEntryLookup<PlacedFeature> featureLookup,
            RegistryEntryLookup<ConfiguredCarver<?>> carverLookup
    ) {
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(
                featureLookup,
                carverLookup
        );
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        addBasicFeatures(generationSettings);

        // Forest floor vegetation
        addVegetation(generationSettings, TaiaoPlacedFeatures.NATIVE_FOREST_GRASS_PATCH);
        DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);

        // Trees
        addVegetation(generationSettings, TaiaoPlacedFeatures.NATIVE_FOREST_TREES);

        // Spawns
        DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);
        spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(TaiaoEntities.KIWI, 10, 4, 4));
        spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(TaiaoEntities.PUUKEKO, 10, 4, 4));
        spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(TaiaoEntities.MOA, 8, 4, 4));
        spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(TaiaoEntities.KAAKAAPOO, 10, 1, 2));

        return OverworldBiomeCreator.createBiome(
                true,
                0.7f,
                0.8f,
                spawnSettings,
                generationSettings,
                MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_FOREST)
        );
    }

    public static void bootstrap(@NotNull Registerable<Biome> registerable) {
        Taiao.LOGGER.debug("Registering biomes");

        RegistryEntryLookup<PlacedFeature> featureLookup = registerable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntryLookup<ConfiguredCarver<?>> carverLookup = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);

        for (Map.Entry<RegistryKey<Biome>, BiFunction<RegistryEntryLookup<PlacedFeature>, RegistryEntryLookup<ConfiguredCarver<?>>, Biome>> entry : TO_REGISTER.entrySet()) {
            registerable.register(entry.getKey(), entry.getValue().apply(featureLookup, carverLookup));
        }
    }

    public static RegistryKey<Biome> register(
            Identifier id,
            BiFunction<RegistryEntryLookup<PlacedFeature>, RegistryEntryLookup<ConfiguredCarver<?>>, Biome> biomeFactory
    ) {
        RegistryKey<Biome> key = RegistryKey.of(RegistryKeys.BIOME, id);

        TO_REGISTER.put(key, biomeFactory);

        return key;
    }

    @Contract("_ -> param1")
    public static GenerationSettings.LookupBackedBuilder addBasicFeatures(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);

        return generationSettings;
    }

    @Contract("_, _ -> param1")
    public static GenerationSettings.@NotNull LookupBackedBuilder addVegetation(
            GenerationSettings.@NotNull LookupBackedBuilder generationSettings,
            RegistryKey<PlacedFeature> placedFeature
    ) {
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, placedFeature);

        return generationSettings;
    }
}
