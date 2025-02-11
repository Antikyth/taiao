// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import antikyth.taiao.entity.ShushableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.function.Predicate;

public class FreezeWhenThreatenedGoal<E extends LivingEntity> extends Goal {
	protected final MobEntity mob;

	protected final double maxDistance;
	protected final Class<E> scaredOfClass;
	protected final Predicate<LivingEntity> scaredOfPredicate;
	protected final TargetPredicate withinRangePredicate;

	public FreezeWhenThreatenedGoal(
		MobEntity mob,
		double maxDistance,
		Class<E> scaredOfClass,
		Predicate<LivingEntity> scaredOfPredicate
	) {
		this.mob = mob;

		this.maxDistance = maxDistance;

		this.scaredOfClass = scaredOfClass;
		this.scaredOfPredicate = scaredOfPredicate;
		this.withinRangePredicate = TargetPredicate.createNonAttackable()
			.setBaseMaxDistance(maxDistance)
			.setPredicate(scaredOfPredicate);

		this.setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
	}

	@Override
	public boolean canStart() {
		// Don't want to freeze in water or midair
		if (this.mob.isInsideWaterOrBubbleColumn() || !this.mob.isOnGround()) return false;

		World world = this.mob.getWorld();

		// Are there any scary entities nearby?
		for (LivingEntity entity : world.getEntitiesByClass(
			this.scaredOfClass,
			this.mob.getBoundingBox().expand(this.maxDistance, this.maxDistance, this.maxDistance),
			this.scaredOfPredicate
		)) {
			if (this.withinRangePredicate.test(this.mob, entity)) return true;
		}

		return false;
	}

	@Override
	public void start() {
		this.mob.getNavigation().stop();

		if (this.mob instanceof ShushableEntity shushable) shushable.setShushed(true);
	}

	@Override
	public void stop() {
		if (this.mob instanceof ShushableEntity shushable) shushable.setShushed(false);
	}
}
