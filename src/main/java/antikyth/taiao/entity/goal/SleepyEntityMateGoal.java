// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import antikyth.taiao.entity.SleepyEntity;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;

public class SleepyEntityMateGoal extends AnimalMateGoal {
	public SleepyEntityMateGoal(AnimalEntity animal, double speed) {
		super(animal, speed);
	}

	public SleepyEntityMateGoal(AnimalEntity animal, double speed, Class<? extends AnimalEntity> mateClass) {
		super(animal, speed, mateClass);
	}

	@Override
	public void start() {
		if (this.animal instanceof SleepyEntity sleepyAnimal) sleepyAnimal.wake();
		if (this.mate instanceof SleepyEntity sleepyMate) sleepyMate.wake();

		super.start();
	}
}
