// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.biome;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.world.gen.feature.TaiaoPlacedFeatures;
import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.*;
import net.minecraft.world.biome.SpawnSettings.SpawnEntry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.OceanPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
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
	public static final RegistryKey<Biome> KAHIKATEA_SWAMP = register(
		Taiao.id("kahikatea_swamp"),
		TaiaoBiomes::kahikateaSwamp
	);

	public static void initializeBiolith() {
		Taiao.LOGGER.debug("Configuring biome placements and surface rules with Biolith");

		double forestProportion = 1d / 3.5d;
		BiomePlacement.replaceOverworld(BiomeKeys.FOREST, NATIVE_FOREST, forestProportion);
		BiomePlacement.replaceOverworld(BiomeKeys.FLOWER_FOREST, NATIVE_FOREST, forestProportion);
		BiomePlacement.replaceOverworld(BiomeKeys.BIRCH_FOREST, NATIVE_FOREST, forestProportion);
		BiomePlacement.replaceOverworld(BiomeKeys.OLD_GROWTH_BIRCH_FOREST, NATIVE_FOREST, forestProportion);

		double swampProportion = 1d / 2.5d;
		BiomePlacement.replaceOverworld(BiomeKeys.SWAMP, KAHIKATEA_SWAMP, swampProportion);
		BiomePlacement.replaceOverworld(BiomeKeys.MANGROVE_SWAMP, KAHIKATEA_SWAMP, swampProportion);

		// Add surface rules
		SurfaceGeneration.addOverworldSurfaceRules(Taiao.id("rules/overworld"), TaiaoMaterialRules.ALL);
	}

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
		DefaultBiomeFeatures.addLargeFerns(generationSettings);
		DefaultBiomeFeatures.addDefaultDisks(generationSettings);

		// Forest floor vegetation
		addVegetation(generationSettings, TaiaoPlacedFeatures.NATIVE_FOREST_GRASS_PATCH);
		DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
		addVegetation(generationSettings, TaiaoPlacedFeatures.HARAKEKE_PATCH);

		// Trees
		addVegetation(generationSettings, TaiaoPlacedFeatures.NATIVE_FOREST_TREES);

		// Spawns
		DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);

		spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnEntry(TaiaoEntities.KIWI, 10, 4, 4));
		spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnEntry(TaiaoEntities.MOA, 8, 4, 4));
		spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnEntry(TaiaoEntities.KAAKAAPOO, 10, 1, 2));

		return OverworldBiomeCreator.createBiome(
			true,
			0.7f,
			0.4f,
			spawnSettings,
			generationSettings,
			MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_FOREST)
		);
	}

	public static Biome kahikateaSwamp(
		RegistryEntryLookup<PlacedFeature> featureLookup,
		RegistryEntryLookup<ConfiguredCarver<?>> carverLookup
	) {
		GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(
			featureLookup,
			carverLookup
		);
		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

		DefaultBiomeFeatures.addFossils(generationSettings);
		addBasicFeatures(generationSettings);
		DefaultBiomeFeatures.addClayDisk(generationSettings);

		// Floor vegetation
		addVegetation(generationSettings, VegetationPlacedFeatures.PATCH_GRASS_NORMAL);
		addVegetation(generationSettings, VegetationPlacedFeatures.PATCH_DEAD_BUSH);
		DefaultBiomeFeatures.addDefaultMushrooms(generationSettings);
		addVegetation(generationSettings, TaiaoPlacedFeatures.RAUPOO_PATCH);
		addVegetation(generationSettings, TaiaoPlacedFeatures.GIANT_CANE_RUSH_PATCH);
		addVegetation(generationSettings, TaiaoPlacedFeatures.HARAKEKE_PATCH);

		addVegetation(generationSettings, OceanPlacedFeatures.SEAGRASS_SWAMP);

		// Trees
		addVegetation(generationSettings, TaiaoPlacedFeatures.KAHIKATEA_SWAMP_TREES);

		// Eel traps
		generationSettings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, TaiaoPlacedFeatures.HIINAKI_SWAMP);

		// Spawns
		DefaultBiomeFeatures.addBatsAndMonsters(spawnSettings);
		spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SLIME, 1, 1, 1));

		spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnEntry(TaiaoEntities.PUUKEKO, 10, 4, 4));
		spawnSettings.spawn(
			SpawnGroup.CREATURE,
			new SpawnEntry(TaiaoEntities.AUSTRALASIAN_BITTERN, 7, 4, 4)
		);

		spawnSettings.spawn(SpawnGroup.WATER_AMBIENT, new SpawnEntry(TaiaoEntities.EEL, 5, 1, 3));

		return OverworldBiomeCreator.createBiome(
			true,
			0.8f,
			0.4f,
			0x617b64,
			0x232317,
			0x4c763c,
			null,
			spawnSettings,
			generationSettings,
			MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_SWAMP)
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
