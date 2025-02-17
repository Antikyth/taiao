// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.goal;

import antikyth.taiao.block.entity.HiinakiBlockEntity;
import antikyth.taiao.entity.Trappable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnterHiinakiGoal<E extends Entity & Trappable> extends Goal {
	protected final E entity;

	public EnterHiinakiGoal(E entity) {
		this.entity = entity;
	}

	@Override
	public boolean canStart() {
		BlockPos hiinakiPos = this.entity.getHiinakiPos();

		if (hiinakiPos != null && hiinakiPos.isWithinDistance(this.entity.getPos(), 2)) {
			if (this.entity.getWorld().getBlockEntity(hiinakiPos) instanceof HiinakiBlockEntity blockEntity) {
				if (!blockEntity.hasTrappedEntity()) {
					return true;
				} else {
					this.entity.setHiinakiPos(null);
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

		if (world.getBlockEntity(hiinakiPos) instanceof HiinakiBlockEntity blockEntity) {
			blockEntity.trapEntity(this.entity);
		}
	}
}
