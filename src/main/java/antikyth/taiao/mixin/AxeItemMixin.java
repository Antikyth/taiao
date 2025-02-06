// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin;

import antikyth.taiao.block.log.Strippable;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Mixin(AxeItem.class)
public class AxeItemMixin {
	/**
	 * Adds support for {@link Strippable} blocks that would otherwise not be supported for stripping due to their lack
	 * of an {@link net.minecraft.state.property.Properties#AXIS AXIS} property.
	 */
	@ModifyReturnValue(
		method = "getStrippedState(Lnet/minecraft/block/BlockState;)Ljava/util/Optional;",
		at = @At("RETURN")
	)
	private @NotNull Optional<BlockState> addStrippedStateOptions(
		@NotNull Optional<BlockState> original,
		@Local(argsOnly = true) BlockState state
	) {
		return original.or(() -> Strippable.getStrippedState(state));
	}
}
