// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin;

import net.minecraft.data.server.loottable.EntityLootTableGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(EntityLootTableGenerator.class)
public interface EntityLootTableGeneratorAccessor {
    @Accessor
    Map<EntityType<?>, Map<Identifier, LootTable.Builder>> getLootTables();
}
