// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.Taiao;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TaiaoEntities {
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

    public static <T extends Entity> EntityType<T> register(Identifier id, EntityType<T> entityType) {
        return Registry.register(Registries.ENTITY_TYPE, id, entityType);
    }

    public static void initialize() {
        Taiao.LOGGER.debug("Registering entities");
    }
}
