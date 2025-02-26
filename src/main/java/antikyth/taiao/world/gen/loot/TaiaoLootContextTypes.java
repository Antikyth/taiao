// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.loot;

import antikyth.taiao.Taiao;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TaiaoLootContextTypes {
	public static final LootContextType TRAP_BAIT = register(
		Taiao.id("trap_bait"),
		builder -> builder.require(LootContextParameters.ORIGIN)
			.require(LootContextParameters.BLOCK_STATE)
			.require(LootContextParameters.BLOCK_ENTITY)
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registered loot context types");
	}

	public static LootContextType register(@NotNull Identifier id, Consumer<LootContextType.Builder> type) {
		return LootContextTypes.register(id.toString(), type);
	}
}
