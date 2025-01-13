// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen;

import antikyth.taiao.datagen.model.TaiaoModelGenerator;
import antikyth.taiao.world.gen.feature.TaiaoConfiguredFeatures;
import antikyth.taiao.world.gen.feature.TaiaoPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class TaiaoDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(TaiaoModelGenerator::new);
        pack.addProvider(TaiaoRecipeGenerator::new);
        pack.addProvider(TaiaoBlockLootTableGenerator::new);
        pack.addProvider(TaiaoFeatureGenerator::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder builder) {
        DataGeneratorEntrypoint.super.buildRegistry(builder);

        builder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, TaiaoConfiguredFeatures::bootstrapConfiguredFeatures);
        builder.addRegistry(RegistryKeys.PLACED_FEATURE, TaiaoPlacedFeatures::bootstrapPlacedFeatures);
    }
}
