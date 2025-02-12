// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;

import java.util.EnumSet;

public class WhileAwakeGoal extends Goal {
	protected final MobEntity mob;
	protected final Goal goal;

	public WhileAwakeGoal(MobEntity mob, Goal goal) {
		this.mob = mob;
		this.goal = goal;
	}

	@Override
	public boolean canStart() {
		return !this.mob.isSleeping() && this.goal.canStart();
	}

	@Override
	public boolean shouldContinue() {
		return !this.mob.isSleeping() && this.goal.shouldContinue();
	}

	@Override
	public boolean canStop() {
		return this.goal.canStop();
	}

	@Override
	public void start() {
		this.goal.start();
	}

	@Override
	public void stop() {
		this.goal.stop();
	}

	@Override
	public void setControls(EnumSet<Control> controls) {
		this.goal.setControls(controls);
	}

	@Override
	public EnumSet<Control> getControls() {
		return this.goal.getControls();
	}

	@Override
	public boolean shouldRunEveryTick() {
		return this.goal.shouldRunEveryTick();
	}

	@Override
	public void tick() {
		if (!this.mob.isSleeping()) this.goal.tick();
	}
}
