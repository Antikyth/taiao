// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import antikyth.taiao.entity.SleepyEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.function.Predicate;

public class SleepGoal<E extends PathAwareEntity & SleepyEntity> extends Goal {
	protected final E entity;
	protected final TargetPredicate scaryEntityPredicate;

	protected static final int MAX_CALM_DOWN_TIME = toGoalTicks(140);
	protected int timer;

	protected final double horizontalDistance;
	protected final double verticalDistance;

	public SleepGoal(E entity, Predicate<LivingEntity> scaryEntityPredicate) {
		this(entity, 12d, 6d, scaryEntityPredicate);
	}

	public SleepGoal(E entity) {
		this(entity, TaiaoEntityPredicates.ALWAYS_FALSE);
	}

	public SleepGoal(E entity, double horizontalDistance, double verticalDistance) {
		this(entity, horizontalDistance, verticalDistance, TaiaoEntityPredicates.ALWAYS_FALSE);
	}

	public SleepGoal(
		E entity,
		double horizontalDistance,
		double verticalDistance,
		Predicate<LivingEntity> scaryEntityPredicate
	) {
		this.entity = entity;

		this.resetTimer();

		this.horizontalDistance = horizontalDistance;
		this.verticalDistance = verticalDistance;

		this.scaryEntityPredicate = TargetPredicate.createNonAttackable()
			.setBaseMaxDistance(horizontalDistance)
			.ignoreVisibility()
			.setPredicate(scaryEntityPredicate);

		this.setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
	}

	protected void resetTimer() {
		this.timer = this.entity.getRandom().nextInt(MAX_CALM_DOWN_TIME);
	}

	protected boolean isAtFavoredLocation() {
		World world = this.entity.getWorld();
		BlockPos pos = BlockPos.ofFloored(this.entity.getX(), this.entity.getBoundingBox().maxY, this.entity.getZ());

		return !world.isSkyVisible(pos) && this.entity.getPathfindingFavor(pos) >= 0f;
	}

	protected boolean isCalm() {
		World world = this.entity.getWorld();
		Box surroundings = this.entity.getBoundingBox()
			.expand(this.horizontalDistance, this.verticalDistance, this.horizontalDistance);

		// No scary entities nearby?
		return world.getTargets(LivingEntity.class, this.scaryEntityPredicate, this.entity, surroundings).isEmpty();
	}

	protected boolean canSleep() {
		if (this.timer > 0) {
			this.timer--;

			return false;
		} else {
			World world = this.entity.getWorld();

			return world.isDay() && this.isAtFavoredLocation() && this.isCalm() && !this.entity.inPowderSnow;
		}
	}

	@Override
	public boolean canStart() {
		if (this.entity.sidewaysSpeed == 0f && this.entity.upwardSpeed == 0f && this.entity.forwardSpeed == 0f) {
			return this.canSleep() || this.entity.isSleeping();
		} else {
			return false;
		}
	}

	@Override
	public boolean shouldContinue() {
		return this.canSleep();
	}

	@Override
	public void start() {
		this.entity.startSleeping();

		this.entity.getNavigation().stop();
		this.entity.getMoveControl().moveTo(this.entity.getX(), this.entity.getY(), this.entity.getZ(), 0d);
	}

	@Override
	public void stop() {
		this.resetTimer();
		this.entity.wake();
	}
}
