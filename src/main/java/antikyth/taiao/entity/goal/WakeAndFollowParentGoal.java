// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import antikyth.taiao.entity.SleepyEntity;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.passive.AnimalEntity;

public class WakeAndFollowParentGoal extends FollowParentGoal {
	public WakeAndFollowParentGoal(AnimalEntity animal, double speed) {
		super(animal, speed);
	}

	@Override
	public void start() {
		if (this.animal instanceof SleepyEntity sleepy) sleepy.wake();

		super.start();
	}
}
