// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.plant;

import antikyth.taiao.sound.TaiaoSoundEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

public class HarakekeBlock extends HarvestableTripleTallPlantBlock {
	public HarakekeBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	@Override
	protected @Nullable SoundEvent getShearSound() {
		return TaiaoSoundEvents.BLOCK_HARAKEKE_SHEAR;
	}
}
