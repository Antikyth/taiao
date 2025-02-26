// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.loot.entry;

import antikyth.taiao.Taiao;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonSerializer;

public class TaiaoLootPoolEntryTypes {
	public static final LootPoolEntryType BANNER = register(Taiao.id("banner"), new BannerEntry.Serializer());

	public static void initialize() {
		Taiao.LOGGER.debug("Registered loot pool entry types");
	}

	public static LootPoolEntryType register(Identifier id, JsonSerializer<? extends LootPoolEntry> serializer) {
		return Registry.register(Registries.LOOT_POOL_ENTRY_TYPE, id, new LootPoolEntryType(serializer));
	}
}
