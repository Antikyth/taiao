// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.blockpredicate;

import antikyth.taiao.Taiao;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
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


    /**
     * Whether any blocks match the given {@code predicate} within horizontal range.
     *
     * @param predicate             the predicate to check blocks within range against
     * @param shape                 the shape of the area to check within
     * @param ignoreCenter          whether to ignore the center of the area to check around
     * @param maxDistanceFromCenter the maximum horizontal distance to check for matches
     */
    public static @NotNull BlockPredicate withinHorizontalRange(
            BlockPredicate predicate,
            WithinHorizontalRangeBlockPredicate.Shape shape,
            boolean ignoreCenter,
            int maxDistanceFromCenter
    ) {
        return withinHorizontalRange(predicate, Vec3i.ZERO, shape, ignoreCenter, false, maxDistanceFromCenter);
    }

    /**
     * Whether any blocks match the given {@code predicate} within horizontal range.
     *
     * @param predicate             the predicate to check blocks within range against
     * @param offset                the offset to center the area to check within around
     * @param shape                 the shape of the area to check within
     * @param ignoreCenter          whether to ignore the center of the area to check within
     * @param ignoreOrigin          whether to ignore the origin position
     * @param maxDistanceFromCenter the maximum horizontal distance to check for matches
     */
    public static @NotNull BlockPredicate withinHorizontalRange(
            BlockPredicate predicate,
            Vec3i offset,
            WithinHorizontalRangeBlockPredicate.Shape shape,
            boolean ignoreCenter,
            boolean ignoreOrigin,
            int maxDistanceFromCenter
    ) {
        return new WithinHorizontalRangeBlockPredicate(
                predicate,
                offset,
                shape,
                ignoreCenter,
                ignoreOrigin,
                maxDistanceFromCenter
        );
    }

    @Contract(" -> new")
    public static @NotNull BlockPredicate air() {
        return air(Vec3i.ZERO);
    }

    @Contract("_ -> new")
    public static @NotNull BlockPredicate air(@NotNull Direction direction) {
        return air(direction, 1);
    }

    @Contract("_, _ -> new")
    public static @NotNull BlockPredicate air(@NotNull Direction direction, int distance) {
        return air(direction.getVector().multiply(distance));
    }

    @Contract("_ -> new")
    public static @NotNull BlockPredicate air(Vec3i offset) {
        return BlockPredicate.matchingBlocks(offset, Blocks.AIR);
    }

    @Contract(" -> new")
    public static @NotNull BlockPredicate water() {
        return water(Vec3i.ZERO);
    }

    @Contract("_ -> new")
    public static @NotNull BlockPredicate water(@NotNull Direction direction) {
        return water(direction, 1);
    }

    @Contract("_, _ -> new")
    public static @NotNull BlockPredicate water(@NotNull Direction direction, int distance) {
        return water(direction.getVector().multiply(distance));
    }

    @Contract("_ -> new")
    public static @NotNull BlockPredicate water(Vec3i offset) {
        return BlockPredicate.matchingBlocks(offset, Blocks.WATER);
    }

    public static void initialize() {
    }

    public static <P extends BlockPredicate> BlockPredicateType<P> register(Identifier id, Codec<P> codec) {
        return Registry.register(Registries.BLOCK_PREDICATE_TYPE, id, () -> codec);
    }
}
