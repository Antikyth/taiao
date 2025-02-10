// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import org.jetbrains.annotations.NotNull;

public class ParentRevengeGoal extends RevengeGoal {
	public ParentRevengeGoal(PathAwareEntity mob, Class<?>... noRevengeTypes) {
		super(mob, noRevengeTypes);
	}

	@Override
	public void start() {
		super.start();

		if (this.mob.isBaby()) {
			this.callSameTypeForRevenge();
			this.stop();
		}
	}

	@Override
	protected void setMobEntityTarget(@NotNull MobEntity mob, LivingEntity target) {
		if (!mob.isBaby()) super.setMobEntityTarget(mob, target);
	}
}
