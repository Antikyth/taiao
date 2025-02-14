// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.emi;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.ColorResolver;
import net.minecraft.world.chunk.light.LightingProvider;
import org.jetbrains.annotations.Nullable;

public class BrightBlockRenderView implements BlockRenderView {
	protected final BlockRenderView inner;

	public BrightBlockRenderView(BlockRenderView inner) {
		this.inner = inner;
	}

	@Override
	public int getLightLevel(LightType type, BlockPos pos) {
		return this.getMaxLightLevel();
	}

	@Override
	public int getBaseLightLevel(BlockPos pos, int ambientDarkness) {
		return this.getMaxLightLevel();
	}

	@Override
	public float getBrightness(Direction direction, boolean shaded) {
		return this.inner.getBrightness(direction, shaded);
	}

	@Override
	public LightingProvider getLightingProvider() {
		return this.inner.getLightingProvider();
	}

	@Override
	public int getColor(BlockPos pos, ColorResolver colorResolver) {
		if (colorResolver.equals(BiomeColors.GRASS_COLOR)) {
			return GrassColors.getDefaultColor();
		} else if (colorResolver.equals(BiomeColors.FOLIAGE_COLOR)) {
			return FoliageColors.getDefaultColor();
		} else if (colorResolver.equals(BiomeColors.WATER_COLOR)) {
			return 0x3F76E4;
		} else {
			return this.inner.getColor(pos, colorResolver);
		}
	}

	@Override
	public @Nullable BlockEntity getBlockEntity(BlockPos pos) {
		return this.inner.getBlockEntity(pos);
	}

	@Override
	public BlockState getBlockState(BlockPos pos) {
		return this.inner.getBlockState(pos);
	}

	@Override
	public FluidState getFluidState(BlockPos pos) {
		return this.inner.getFluidState(pos);
	}

	@Override
	public int getHeight() {
		return this.inner.getHeight();
	}

	@Override
	public int getBottomY() {
		return this.inner.getBottomY();
	}
}
