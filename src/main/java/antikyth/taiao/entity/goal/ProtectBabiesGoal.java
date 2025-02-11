// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;

import java.util.function.Predicate;

public class ProtectBabiesGoal<E extends LivingEntity> extends ActiveTargetGoal<E> {
	public ProtectBabiesGoal(
		MobEntity mob,
		Class<E> targetClass,
		boolean checkVisibility,
		boolean checkCanNavigate,
		Predicate<LivingEntity> targetPredicate
	) {
		super(mob, targetClass, 20, checkVisibility, checkCanNavigate, targetPredicate);
	}

	@Override
	public boolean canStart() {
		if (this.mob.isBaby()) return false;

		if (super.canStart()) {
			// Are there any babies nearby?
			for (MobEntity entity : this.mob.getWorld().getNonSpectatingEntities(
				this.mob.getClass(),
				this.mob.getBoundingBox().expand(8d, 4d, 8d)
			)) {
				if (entity.isBaby()) return true;
			}
		}

		return false;
	}

	@Override
	protected double getFollowRange() {
		return super.getFollowRange() * 0.5d;
	}
}
