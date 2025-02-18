// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.registry;

import antikyth.taiao.Taiao;
import antikyth.taiao.world.gen.entityprovider.EntityTypeProvider;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;

public class TaiaoRegistries {
	public static final Registry<EntityTypeProvider.Type<?>> ENTITY_TYPE_PROVIDER_TYPE = FabricRegistryBuilder.createDefaulted(
		TaiaoRegistryKeys.ENTITY_TYPE_PROVIDER_TYPE,
		Taiao.id("simple")
	).buildAndRegister();
}
