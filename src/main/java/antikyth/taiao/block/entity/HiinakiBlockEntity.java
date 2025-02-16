// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class HiinakiBlockEntity extends BlockEntity {
	public HiinakiBlockEntity(
		BlockEntityType<?> type,
		BlockPos pos,
		BlockState state
	) {
		super(type, pos, state);
	}

	public HiinakiBlockEntity(BlockPos pos, BlockState state) {
		this(TaiaoBlockEntities.HIINAKI, pos, state);
	}
}
