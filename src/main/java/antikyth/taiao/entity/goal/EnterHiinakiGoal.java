// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import antikyth.taiao.block.entity.HiinakiBlockEntity;
import antikyth.taiao.entity.HiinakiTrappable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnterHiinakiGoal<E extends LivingEntity & HiinakiTrappable> extends Goal {
	protected final E entity;

	protected final float chance;
	protected final int maxCooldown;
	protected int timer;

	public EnterHiinakiGoal(E entity, int maxCooldown, float chance) {
		this.entity = entity;
		this.maxCooldown = toGoalTicks(maxCooldown);
		this.chance = chance;
	}

	protected void resetTimer() {
		this.timer = this.entity.getRandom().nextInt(this.maxCooldown);
	}

	@Override
	public boolean canStart() {
		BlockPos hiinakiPos = this.entity.getHiinakiPos();

		if (hiinakiPos != null && hiinakiPos.isWithinDistance(this.entity.getPos(), 2)) {
			if (this.entity.getWorld().getBlockEntity(hiinakiPos) instanceof HiinakiBlockEntity blockEntity) {
				if (blockEntity.hasTrappedEntity() || blockEntity.isEntranceBlocked()) {
					this.entity.setHiinakiPos(null);

					return false;
				} else if (this.timer > 0) {
					this.timer--;

					return false;
				} else {
					this.resetTimer();

					return this.entity.getRandom().nextFloat() < this.chance;
				}
			}
		}

		return false;
	}

	@Override
	public boolean shouldContinue() {
		return false;
	}

	@Override
	public void start() {
		World world = this.entity.getWorld();
		BlockPos hiinakiPos = this.entity.getHiinakiPos();

		if (hiinakiPos != null && world.getBlockEntity(hiinakiPos) instanceof HiinakiBlockEntity blockEntity) {
			blockEntity.trapEntity(this.entity);
		}
	}
}
