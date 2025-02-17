// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import antikyth.taiao.entity.Sleepy;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.function.Predicate;

public class SleepGoal<E extends PathAwareEntity & Sleepy> extends Goal {
	protected final E entity;
	protected final TargetPredicate scaryEntityPredicate;
	protected final boolean nocturnal;

	protected static final int MAX_CALM_DOWN_TIME = toGoalTicks(140);
	protected int timer;

	protected final double horizontalDistance;
	protected final double verticalDistance;

	public SleepGoal(E entity, boolean nocturnal, Predicate<LivingEntity> scaryEntityPredicate) {
		this(entity, nocturnal, 12d, 6d, scaryEntityPredicate);
	}

	public SleepGoal(E entity, boolean nocturnal) {
		this(entity, nocturnal, TaiaoEntityPredicates.ALWAYS_FALSE);
	}

	public SleepGoal(E entity, boolean nocturnal, double horizontalDistance, double verticalDistance) {
		this(entity, nocturnal, horizontalDistance, verticalDistance, TaiaoEntityPredicates.ALWAYS_FALSE);
	}

	public SleepGoal(
		E entity,
		boolean nocturnal,
		double horizontalDistance,
		double verticalDistance,
		Predicate<LivingEntity> scaryEntityPredicate
	) {
		this.entity = entity;
		this.nocturnal = nocturnal;

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

	/**
	 * Whether the entity is at a favored sleeping location.
	 */
	protected boolean isAtFavoredLocation() {
		World world = this.entity.getWorld();
		BlockPos pos = BlockPos.ofFloored(this.entity.getX(), this.entity.getBoundingBox().maxY, this.entity.getZ());

		return !world.isSkyVisible(pos) && this.entity.getPathfindingFavor(pos) >= 0f;
	}

	/**
	 * Whether the entity feels safe.
	 */
	protected boolean isCalm() {
		if (this.entity.inPowderSnow || this.entity.getAttacker() != null || this.entity.isOnFire()) {
			return false;
		}

		World world = this.entity.getWorld();
		Box surroundings = this.entity.getBoundingBox()
			.expand(this.horizontalDistance, this.verticalDistance, this.horizontalDistance);

		// No scary entities nearby?
		return world.getTargets(LivingEntity.class, this.scaryEntityPredicate, this.entity, surroundings).isEmpty();
	}

	/**
	 * Whether it is the correct time of day for the entity to sleep.
	 */
	protected boolean isCorrectTimeOfDay() {
		World world = this.entity.getWorld();

		return this.nocturnal ? world.isDay() : world.isNight();
	}

	/**
	 * Whether the entity is able to sleep at the moment.
	 * <p>
	 * If sleeping conditions are met, calling this method decrements the timer (the entity 'starts to doze off').
	 * Once the timer reaches zero, the entity is allowed to sleep.
	 */
	protected boolean canSleep() {
		if (this.isCorrectTimeOfDay() && this.isCalm() && this.isAtFavoredLocation()) {
			if (this.timer > 0) {
				this.timer--;

				return false;
			}

			return true;
		}

		return false;
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
		this.timer = 0;
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
