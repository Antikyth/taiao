// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.advancement.criteria;

import antikyth.taiao.Taiao;
import net.minecraft.advancement.criterion.Criteria;

public class TaiaoCriteria {
	public static final KeteChangedCriterion KETE_CHANGED = Criteria.register(new KeteChangedCriterion());
	public static final BlockPlacedFromKeteCriterion BLOCK_PLACED_FROM_KETE = Criteria.register(new BlockPlacedFromKeteCriterion());

	public static final EntityFreedCriterion ENTITY_FREED = Criteria.register(new EntityFreedCriterion());

	public static void initialize() {
		Taiao.LOGGER.debug("Registered advancement criteria");
	}
}
