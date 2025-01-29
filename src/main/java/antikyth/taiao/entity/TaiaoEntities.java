// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.waka.ChestWakaEntity;
import antikyth.taiao.entity.waka.DoubleChestWakaEntity;
import antikyth.taiao.entity.waka.WakaEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TaiaoEntities {
    protected static final EntityDimensions WAKA_DIMENSIONS = EntityDimensions.fixed(1.375f, 0.5625f);

    public static final EntityType<KiwiEntity> KIWI = register(
            Taiao.id("kiwi"),
            FabricEntityTypeBuilder.createLiving()
                    .entityFactory(KiwiEntity::new)
                    .spawnGroup(SpawnGroup.CREATURE)
                    .dimensions(EntityDimensions.changing(0.4f, 0.5f))
                    .defaultAttributes(KiwiEntity::createAttributes)
                    .trackRangeChunks(10)
                    .build()
    );
    /**
     * A pūkeko.
     */
    public static final EntityType<PuukekoEntity> PUUKEKO = register(
            Taiao.id("puukeko"),
            FabricEntityTypeBuilder.createLiving()
                    .entityFactory(PuukekoEntity::new)
                    .spawnGroup(SpawnGroup.CREATURE)
                    .dimensions(EntityDimensions.changing(0.4f, 0.7f))
                    .defaultAttributes(PuukekoEntity::createAttributes)
                    .trackRangeChunks(10)
                    .build()
    );
    public static final EntityType<MoaEntity> MOA = register(
            Taiao.id("moa"),
            FabricEntityTypeBuilder.createLiving()
                    .entityFactory(MoaEntity::new)
                    .spawnGroup(SpawnGroup.CREATURE)
                    .dimensions(EntityDimensions.changing(1.25f, 2.25f))
                    .defaultAttributes(MoaEntity::createAttributes)
                    .trackRangeChunks(10)
                    .build()
    );
    /**
     * A kākāpō.
     */
    public static final EntityType<KaakaapooEntity> KAAKAAPOO = register(
            Taiao.id("kaakaapoo"),
            FabricEntityTypeBuilder.createLiving()
                    .entityFactory(KaakaapooEntity::new)
                    .spawnGroup(SpawnGroup.CREATURE)
                    .dimensions(EntityDimensions.changing(0.55f, 0.7f))
                    .defaultAttributes(KaakaapooEntity::createAttributes)
                    .trackRangeChunks(10)
                    .build()
    );

    public static final EntityType<WakaEntity> WAKA = register(
            Taiao.id("waka"),
            FabricEntityTypeBuilder.<WakaEntity>create(SpawnGroup.MISC, WakaEntity::new)
                    .dimensions(WAKA_DIMENSIONS)
                    .build()
    );
    public static final EntityType<ChestWakaEntity> SINGLE_CHEST_WAKA = register(
            Taiao.id("single_chest_waka"),
            FabricEntityTypeBuilder.<ChestWakaEntity>create(SpawnGroup.MISC, ChestWakaEntity::new)
                    .dimensions(WAKA_DIMENSIONS)
                    .build()
    );
    public static final EntityType<DoubleChestWakaEntity> DOUBLE_CHEST_WAKA = register(
            Taiao.id("double_chest_waka"),
            FabricEntityTypeBuilder.<DoubleChestWakaEntity>create(SpawnGroup.MISC, DoubleChestWakaEntity::new)
                    .dimensions(WAKA_DIMENSIONS)
                    .build()
    );

    public static <T extends Entity> EntityType<T> register(Identifier id, EntityType<T> entityType) {
        return Registry.register(Registries.ENTITY_TYPE, id, entityType);
    }

    public static void initialize() {
        Taiao.LOGGER.debug("Registering entities");
    }
}
