// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.item.ItemPlacementContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TallPlantBlock.class)
public class TallPlantBlockMixin {
    /**
     * Correctly waterlogs the lower half of {@link TallPlantBlock}s when placed.
     * <p>
     * The upper half is already correctly waterlogged.
     */
    @ModifyReturnValue(
            method = "getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;",
            at = @At("RETURN")
    )
    public @Nullable BlockState correctlyWaterlogPlacementState(@Nullable BlockState state, ItemPlacementContext ctx) {
        if (state != null) return TallPlantBlock.withWaterloggedState(ctx.getWorld(), ctx.getBlockPos(), state);
        else return null;
    }
}
