// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin;

import antikyth.taiao.TaiaoConfig;
import antikyth.taiao.entity.AustralasianBitternEntity;
import antikyth.taiao.entity.KaakaapooEntity;
import antikyth.taiao.entity.KiwiEntity;
import antikyth.taiao.entity.PuukekoEntity;
import antikyth.taiao.entity.ai.goal.TaiaoEntityPredicates;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.UntamedActiveTargetGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatEntity.class)
public abstract class CatEntityMixin extends TameableEntity {
	protected CatEntityMixin(
		EntityType<? extends TameableEntity> entityType,
		World world
	) {
		super(entityType, world);
	}

	/**
	 * Adds certain native birds as targets for cats.
	 */
	@Inject(
		method = "initGoals",
		at = @At("RETURN")
	)
	public void addUntamedAttackTargets(CallbackInfo ci) {
		if (TaiaoConfig.AnimalBehavior.mammalianPredatorsHuntNativeAnimals) {
			this.targetSelector.add(
				1,
				new UntamedActiveTargetGoal<>(this, KiwiEntity.class, false, LivingEntity::isBaby)
			);
			this.targetSelector.add(
				1,
				new UntamedActiveTargetGoal<>(this, PuukekoEntity.class, false, LivingEntity::isBaby)
			);
			this.targetSelector.add(
				1,
				new UntamedActiveTargetGoal<>(this, KaakaapooEntity.class, false, TaiaoEntityPredicates.UNTAMED)
			);
			this.targetSelector.add(
				1,
				new UntamedActiveTargetGoal<>(this, AustralasianBitternEntity.class, false, LivingEntity::isBaby)
			);
		}
	}
}
