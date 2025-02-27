// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.tag;

import antikyth.taiao.effect.TaiaoStatusEffectTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TaiaoStatusEffectTagProvider extends FabricTagProvider<StatusEffect> {
	public TaiaoStatusEffectTagProvider(
		FabricDataOutput output,
		CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
	) {
		super(output, RegistryKeys.STATUS_EFFECT, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.@NotNull WrapperLookup lookup) {
		FabricTagBuilder tapuBuilder = getOrCreateTagBuilder(TaiaoStatusEffectTags.TAPU);

		// Add harmful effects
		lookup.getWrapperOrThrow(RegistryKeys.STATUS_EFFECT)
			.filter(effect -> effect.getCategory() == StatusEffectCategory.HARMFUL)
			.streamKeys()
			.forEach(tapuBuilder::add);
		tapuBuilder.add(StatusEffects.BAD_OMEN);
	}
}
