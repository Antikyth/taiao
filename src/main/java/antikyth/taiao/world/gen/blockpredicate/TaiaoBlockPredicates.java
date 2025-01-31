// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.blockpredicate;

import antikyth.taiao.Taiao;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.blockpredicate.BlockPredicateType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TaiaoBlockPredicates {
    public static final BlockPredicateType<WithinHorizontalRangeBlockPredicate> WITHIN_HORIZONTAL_RANGE = register(
            Taiao.id("within_horizontal_range"),
            WithinHorizontalRangeBlockPredicate.CODEC
    );

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull BlockPredicate withinHorizontalRange(BlockPredicate predicate, int range) {
        return withinHorizontalRange(predicate, range, Vec3i.ZERO);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull BlockPredicate withinHorizontalRange(BlockPredicate predicate, int range, Vec3i offset) {
        return new WithinHorizontalRangeBlockPredicate(predicate, range, offset);
    }

    public static void initialize() {
    }

    public static <P extends BlockPredicate> BlockPredicateType<P> register(Identifier id, Codec<P> codec) {
        return Registry.register(Registries.BLOCK_PREDICATE_TYPE, id, () -> codec);
    }
}
