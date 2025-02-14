// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.sound;

import net.minecraft.sound.BlockSoundGroup;

public class TaiaoBlockSoundGroups {
	public static final BlockSoundGroup HARAKEKE = new BlockSoundGroup(
		1f,
		1f,
		TaiaoSoundEvents.BLOCK_HARAKEKE_BREAK,
		TaiaoSoundEvents.BLOCK_HARAKEKE_STEP,
		TaiaoSoundEvents.BLOCK_HARAKEKE_PLACE,
		TaiaoSoundEvents.BLOCK_HARAKEKE_HIT,
		TaiaoSoundEvents.BLOCK_HARAKEKE_FALL
	);
}
