// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.loot.function;

import antikyth.taiao.Taiao;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonSerializer;

public class TaiaoLootFunctionTypes {
	public static final LootFunctionType ADD_KETE_CONTENTS = register(
		Taiao.id("add_kete_contents"),
		new AddKeteContentsLootFunction.Serializer()
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registering loot function types");
	}

	public static LootFunctionType register(Identifier id, JsonSerializer<? extends LootFunction> serializer) {
		return Registry.register(Registries.LOOT_FUNCTION_TYPE, id, new LootFunctionType(serializer));
	}
}
