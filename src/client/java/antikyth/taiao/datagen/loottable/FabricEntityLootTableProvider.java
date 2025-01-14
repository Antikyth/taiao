// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.loottable;

import antikyth.taiao.mixin.EntityLootTableGeneratorAccessor;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider;
import net.fabricmc.fabric.impl.datagen.loot.FabricLootTableProviderImpl;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.loottable.EntityLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@SuppressWarnings({"NonExtendableApiUsage", "UnstableApiUsage"})
public abstract class FabricEntityLootTableProvider extends EntityLootTableGenerator implements
        FabricLootTableProvider {
    private final FabricDataOutput output;

    protected FabricEntityLootTableProvider(FabricDataOutput output) {
        super(FeatureFlags.FEATURE_MANAGER.getFeatureSet());

        this.output = output;
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> exporter) {
        this.generate();

        for (Map<Identifier, LootTable.Builder> map : ((EntityLootTableGeneratorAccessor) this).getLootTables()
                .values()) {
            for (Map.Entry<Identifier, LootTable.Builder> subEntry : map.entrySet()) {
                Identifier id = subEntry.getKey();

                if (id.equals(LootTables.EMPTY)) {
                    continue;
                }

                exporter.accept(id, subEntry.getValue());
            }
        }
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return FabricLootTableProviderImpl.run(writer, this, LootContextTypes.ENTITY, this.output);
    }

    @Override
    public String getName() {
        return "Entity Loot Tables";
    }
}
