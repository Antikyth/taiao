// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TaiaoDamageTypeProvider extends FabricDynamicRegistryProvider {
	public TaiaoDamageTypeProvider(
		FabricDataOutput output,
		CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
	) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.@NotNull WrapperLookup registries, @NotNull Entries entries) {
		entries.addAll(registries.getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE));
	}

	@Override
	public String getName() {
		return "Damage Types";
	}
}
