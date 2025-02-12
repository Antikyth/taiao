// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import net.minecraft.entity.ai.goal.EscapeSunlightGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AvoidDaylightGoal extends EscapeSunlightGoal {
	protected static final int TIMER_MAX = 100;
	protected int timer = toGoalTicks(TIMER_MAX);

	public AvoidDaylightGoal(PathAwareEntity mob, double speed) {
		super(mob, speed);
	}

	@Override
	public boolean canStart() {
		if (!this.mob.isSleeping() && this.mob.getTarget() == null) {
			World world = this.mob.getWorld();
			BlockPos pos = this.mob.getBlockPos();

			if (world.isThundering() && world.isSkyVisible(pos)) {
				return this.targetShadedPos();
			} else if (this.timer > 0) {
				this.timer--;

				return false;
			} else {
				this.timer = TIMER_MAX;

				return world.isDay() && world.isSkyVisible(pos) && this.targetShadedPos();
			}
		}

		return false;
	}
}
