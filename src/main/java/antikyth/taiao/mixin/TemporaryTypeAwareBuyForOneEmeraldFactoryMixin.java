// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Cancellable;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes <a href="https://github.com/FabricMC/fabric/issues/4456">Fabric API #4456</a> until
 * <a href="https://github.com/FabricMC/fabric/pull/4457">Fabric API #4457</a> is merged and backported.
 */
@Mixin(TradeOffers.TypeAwareBuyForOneEmeraldFactory.class)
public class TemporaryTypeAwareBuyForOneEmeraldFactoryMixin {
	@ModifyExpressionValue(
		method = "create",
		at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;")
	)
	private Object failOnNullItem(Object original, @Cancellable CallbackInfoReturnable<TradeOffer> cir) {
		if (original == null) {
			cir.setReturnValue(null);
		}

		return original;
	}
}
