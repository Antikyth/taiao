// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.registry;

import antikyth.taiao.Taiao;
import antikyth.taiao.world.gen.entityprovider.EntityTypeProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class TaiaoRegistryKeys {
	public static final RegistryKey<Registry<EntityTypeProvider.Type<?>>> ENTITY_TYPE_PROVIDER_TYPE = RegistryKey.ofRegistry(
		Taiao.id("entity_type_provider_type")
	);
}
