// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.plant;

import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.sound.TaiaoSoundEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class HarakekeBlock extends HarvestableTripleTallPlantBlock {
	protected static final VoxelShape NORMAL_SHAPE = createCuboidShape(2d, 0d, 2d, 14d, 16d, 14d);
	protected static final VoxelShape TOP_SHAPE = createCuboidShape(2d, 0d, 2d, 14d, 14d, 14d);

	public HarakekeBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	@Override
	protected @Nullable SoundEvent getShearSound() {
		return TaiaoSoundEvents.BLOCK_HARAKEKE_SHEAR;
	}

	@Override
	public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(TRIPLE_BLOCK_PART) == TripleBlockPart.UPPER ? TOP_SHAPE : NORMAL_SHAPE;
	}

	@Override
	protected boolean canPlantOnTop(@NotNull BlockState floor, BlockView world, BlockPos pos) {
		return floor.isIn(TaiaoBlockTags.HARAKEKE_PLANTABLE_ON) && isHydrated(world, pos);
	}

	/**
	 * Returns whether the {@code center} position is close enough to hydration sources.
	 */
	protected static boolean isHydrated(BlockView world, @NotNull BlockPos center) {
		for (BlockPos pos : BlockPos.iterate(center.add(-4, 0, -4), center.add(4, 1, 4))) {
			if (
				world.getFluidState(pos).isIn(FluidTags.WATER)
					|| world.getBlockState(pos).isIn(TaiaoBlockTags.HYDRATES_HARAKEKE)
			) {
				return true;
			}
		}

		return false;
	}
}
