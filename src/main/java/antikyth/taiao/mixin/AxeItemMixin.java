package antikyth.taiao.mixin;

import antikyth.taiao.block.Strippable;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Mixin(AxeItem.class)
public class AxeItemMixin {
    @ModifyReturnValue(
            method = "getStrippedState(Lnet/minecraft/block/BlockState;)Ljava/util/Optional;",
            at = @At("RETURN")
    )
    private Optional<BlockState> addStrippedStateOptions(
            Optional<BlockState> original,
            @Local(argsOnly = true) BlockState state
    ) {
        return original.or(() -> Strippable.getStrippedState(state));
    }
}
