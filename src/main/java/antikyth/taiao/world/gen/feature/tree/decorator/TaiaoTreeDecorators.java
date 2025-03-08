// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.decorator;

import antikyth.taiao.Taiao;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class TaiaoTreeDecorators {
	public static final TreeDecoratorType<HaastsEagleNestTreeDecorator> HAASTS_EAGLE_NEST = register(
		Taiao.id("haasts_eagle_nest"),
		HaastsEagleNestTreeDecorator.CODEC
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registered tree decorators");
	}

	public static <D extends TreeDecorator> TreeDecoratorType<D> register(Identifier id, Codec<D> codec) {
		return Registry.register(Registries.TREE_DECORATOR_TYPE, id, new TreeDecoratorType<>(codec));
	}
}
