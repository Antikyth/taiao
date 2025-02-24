// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.ai.goal;

import antikyth.taiao.entity.Sleepy;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;

import java.util.EnumSet;
import java.util.function.Predicate;

public class TameableSleepGoal<E extends TameableEntity & Sleepy> extends SleepGoal<E> {
	public TameableSleepGoal(
		E entity,
		boolean nocturnal,
		Predicate<LivingEntity> scaryEntityPredicate
	) {
		super(entity, nocturnal, scaryEntityPredicate);

		this.setControls(EnumSet.of(Control.LOOK));
	}

	public TameableSleepGoal(E entity, boolean nocturnal) {
		super(entity, nocturnal);
	}

	public TameableSleepGoal(E entity, boolean nocturnal, double horizontalDistance, double verticalDistance) {
		super(entity, nocturnal, horizontalDistance, verticalDistance);
	}

	public TameableSleepGoal(
		E entity,
		boolean nocturnal,
		double horizontalDistance,
		double verticalDistance,
		Predicate<LivingEntity> scaryEntityPredicate
	) {
		super(entity, nocturnal, horizontalDistance, verticalDistance, scaryEntityPredicate);
	}

	@Override
	protected boolean canSleep() {
		// Only allowed to sleep if not tamed, or tamed but sitting.
		if (!this.entity.isTamed() || this.entity.isSitting()) {
			return super.canSleep();
		} else {
			return false;
		}
	}
}
