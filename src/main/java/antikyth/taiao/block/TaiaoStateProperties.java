// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import antikyth.taiao.block.plant.TripleBlockPart;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

public class TaiaoStateProperties {
	public static final BooleanProperty HARVESTABLE = BooleanProperty.of("harvestable");
	public static final BooleanProperty FRUIT = BooleanProperty.of("fruit");

	public static final EnumProperty<TripleBlockPart> TRIPLE_BLOCK_PART = EnumProperty.of(
		"part",
		TripleBlockPart.class
	);
}
