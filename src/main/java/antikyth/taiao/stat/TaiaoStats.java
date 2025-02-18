// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.stat;

import antikyth.taiao.Taiao;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class TaiaoStats {
	public static final Identifier HIINAKI_BAIT_ADDED = register(
		Taiao.id("hiinaki_bait_added"),
		StatFormatter.DEFAULT
	);
	public static final Identifier HIINAKI_TRAPPED_ENTITY_HARMED = register(
		Taiao.id("hiinaki_trapped_entity_harmed"),
		StatFormatter.DEFAULT
	);
	public static final Identifier HIINAKI_TRAPPED_ENTITY_FREED = register(
		Taiao.id("hiinaki_trapped_entity_freed"),
		StatFormatter.DEFAULT
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registering custom stats");
	}

	public static Identifier register(Identifier id, StatFormatter formatter) {
		Registry.register(Registries.CUSTOM_STAT, id, id);
		Stats.CUSTOM.getOrCreateStat(id, formatter);

		return id;
	}
}
