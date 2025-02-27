// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin.entity.villager;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.plant.HarakekeBlock;
import antikyth.taiao.block.plant.TripleBlockPart;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.sensor.SecondaryPointsOfInterestSensor;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(SecondaryPointsOfInterestSensor.class)
public class SecondaryPointsOfInterestSensorMixin {
	/**
	 * Add {@link TaiaoBlocks#HARAKEKE} as a secondary job site for {@link VillagerProfession#SHEPHERD}s.
	 */
	@ModifyExpressionValue(
		method = "sense(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/VillagerEntity;)V",
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/village/VillagerProfession;secondaryJobSites()Lcom/google/common/collect/ImmutableSet;"
			),
			to = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/util/math/GlobalPos;create(Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/GlobalPos;"
			)
		),
		at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableSet;contains(Ljava/lang/Object;)Z"),
		remap = false
	)
	protected boolean addShepherdSecondaryJobSite(
		boolean foundJobSite,
		ServerWorld world, VillagerEntity villager,
		@Local(ordinal = 1) BlockPos pos
	) {
		if (foundJobSite) return true;

		VillagerProfession profession = villager.getVillagerData().getProfession();
		BlockState state = world.getBlockState(pos);

		return profession == VillagerProfession.SHEPHERD
			&& state.isOf(TaiaoBlocks.HARAKEKE)
			&& state.get(HarakekeBlock.TRIPLE_BLOCK_PART) == TripleBlockPart.LOWER;
	}
}
