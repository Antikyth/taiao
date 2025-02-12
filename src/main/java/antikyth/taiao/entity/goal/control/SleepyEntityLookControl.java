// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal.control;

import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.mob.MobEntity;

public class SleepyEntityLookControl extends LookControl {
	public SleepyEntityLookControl(MobEntity entity) {
		super(entity);
	}

	@Override
	public void tick() {
		if (!this.entity.isSleeping()) super.tick();
	}
}
