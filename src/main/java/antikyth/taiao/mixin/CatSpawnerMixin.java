// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin;

import antikyth.taiao.world.gen.biome.TaiaoBiomeTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.spawner.CatSpawner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CatSpawner.class)
public class CatSpawnerMixin {
	/**
	 * Prevents cats from spawning in
	 * {@linkplain TaiaoBiomeTags#INHIBITS_CAT_SPAWNING biomes that inhibit their spawning}.
	 */
	@WrapOperation(
		method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/SpawnHelper;canSpawn(Lnet/minecraft/entity/SpawnRestriction$Location;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/EntityType;)Z"
		)
	)
	public boolean inhibitCatSpawns(
		SpawnRestriction.Location location,
		@NotNull WorldView world,
		BlockPos pos,
		@Nullable EntityType<?> entityType,
		Operation<Boolean> original
	) {
		return !world.getBiome(pos).isIn(TaiaoBiomeTags.INHIBITS_CAT_SPAWNING)
			&& original.call(location, world, pos, entityType);
	}
}
