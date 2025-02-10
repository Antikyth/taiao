// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin;

import antikyth.taiao.entity.TaiaoEntities;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoxEntity.class)
public class FoxEntityMixin {
	/**
	 * Adds certain native birds as targets for foxes.
	 */
	@ModifyReturnValue(
		method = "method_18262",
		at = @At("RETURN")
	)
	private static boolean addAttackTargets(boolean shouldHunt, @NotNull LivingEntity entity) {
		EntityType<?> entityType = entity.getType();

		return shouldHunt
			|| entityType.equals(TaiaoEntities.KIWI)
			|| (entityType.equals(TaiaoEntities.PUUKEKO) && entity.isBaby())
			|| entityType.equals(TaiaoEntities.KAAKAAPOO)
			|| entityType.equals(TaiaoEntities.AUSTRALASIAN_BITTERN);
	}
}
