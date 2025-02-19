// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.block.entity.HiinakiBlockEntity;
import antikyth.taiao.entity.HiinakiTrappable;
import antikyth.taiao.world.poi.TaiaoPoiTypeTags;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestStorage.OccupationStatus;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class InvestigateHiinakiGoal<E extends PathAwareEntity & HiinakiTrappable> extends Goal {
	protected final E entity;

	protected final int investigationRange;

	protected final float chance;
	protected final int maxCooldown;
	protected int timer;

	/**
	 * @param maxCooldown the maximum number of ticks before the entity attempts to search for a hīnaki
	 * @param chance      the chance of the entity beginning its search once the timer reaches zero
	 */
	public InvestigateHiinakiGoal(E entity, int maxCooldown, float chance, int investigationRange) {
		this.entity = entity;

		this.investigationRange = investigationRange;

		this.maxCooldown = toGoalTicks(maxCooldown);
		this.chance = chance;

		this.setControls(EnumSet.of(Control.MOVE));
	}

	protected void resetTimer() {
		this.timer = this.entity.getRandom().nextInt(maxCooldown);
	}

	@Override
	public boolean canStart() {
		if (this.timer > 0) {
			this.timer--;

			return false;
		} else {
			this.resetTimer();

			if (this.entity.getRandom().nextFloat() < this.chance) {
				List<BlockPos> hiinaki = this.getNearbyBaitedHiinaki(this.investigationRange);
				this.entity.setHiinakiPos(hiinaki.isEmpty() ? null : hiinaki.get(0));

				return this.hasValidTarget();
			} else {
				return false;
			}
		}
	}

	@Override
	public void tick() {
		if (!this.entity.getNavigation().isFollowingPath()) {
			BlockPos hiinakiPos = this.entity.getHiinakiPos();

			if (hiinakiPos != null) {
				if (!hiinakiPos.isWithinDistance(this.entity.getBlockPos(), this.investigationRange)) {
					this.entity.setHiinakiPos(null);
				} else {
					this.entity.getNavigation()
						.startMovingTo(hiinakiPos.getX(), hiinakiPos.getY(), hiinakiPos.getZ(), 1d);
				}
			}

		}

		super.tick();
	}

	@Override
	public void stop() {
		this.entity.getNavigation().stop();
	}

	@Override
	public boolean shouldContinue() {
		return this.hasValidTarget();
	}

	protected boolean hasValidTarget() {
		BlockPos hiinakiPos = this.entity.getHiinakiPos();

		return hiinakiPos != null
			&& this.entity.getWorld().getBlockState(hiinakiPos).isIn(TaiaoBlockTags.EEL_TRAPS)
			&& !this.isCloseEnough(hiinakiPos);
	}

	protected List<BlockPos> getNearbyBaitedHiinaki(int radius) {
		BlockPos pos = this.entity.getBlockPos();
		PointOfInterestStorage poiStorage = ((ServerWorld) this.entity.getWorld()).getPointOfInterestStorage();

		return poiStorage.getInCircle(
				poiType -> poiType.isIn(TaiaoPoiTypeTags.EEL_TRAPS),
				pos,
				radius,
				OccupationStatus.ANY
			)
			.map(PointOfInterest::getPos)
			.filter(poiPos -> {
				if (this.entity.getWorld().getBlockEntity(poiPos) instanceof HiinakiBlockEntity blockEntity) {
					return !blockEntity.hasTrappedEntity() && blockEntity.hasBait();
				} else {
					return false;
				}
			})
			.sorted(Comparator.comparingDouble(pos::getSquaredDistance))
			.collect(Collectors.toList());
	}

	protected boolean isCloseEnough(BlockPos pos) {
		if (this.entity.getBlockPos().isWithinDistance(pos, 2)) {
			return true;
		} else {
			Path path = this.entity.getNavigation().getCurrentPath();

			return path != null && path.getTarget().equals(pos) && path.reachesTarget() && path.isFinished();
		}
	}
}
