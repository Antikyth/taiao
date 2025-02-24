// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.ai.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.mob.MobEntity;

public class SleepyEntityMoveControl extends MoveControl {
	public SleepyEntityMoveControl(MobEntity entity) {
		super(entity);
	}

	@Override
	public void tick() {
		if (!this.entity.isSleeping()) super.tick();
	}
}
