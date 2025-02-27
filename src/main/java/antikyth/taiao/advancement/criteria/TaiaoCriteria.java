// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.advancement.criteria;

import antikyth.taiao.Taiao;
import net.minecraft.advancement.criterion.Criteria;

public class TaiaoCriteria {
	public static final KeteStackCountCriterion KETE_CHANGED = Criteria.register(new KeteStackCountCriterion());
	public static final BlockPlacedFromKeteCriterion BLOCK_PLACED_FROM_KETE = Criteria.register(new BlockPlacedFromKeteCriterion());

	public static final TrapDestroyedCriterion TRAP_DESTROYED = Criteria.register(new TrapDestroyedCriterion());
	public static final ItemCraftedCriterion ITEM_CRAFTED = Criteria.register(new ItemCraftedCriterion());

	public static void initialize() {
		Taiao.LOGGER.debug("Registered advancement criteria");
	}
}
