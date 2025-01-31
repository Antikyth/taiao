// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.blockpredicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.blockpredicate.BlockPredicateType;
import org.jetbrains.annotations.NotNull;

public class WithinHorizontalRangeBlockPredicate implements BlockPredicate {
    public static final Codec<WithinHorizontalRangeBlockPredicate> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BASE_CODEC.fieldOf("predicate").forGetter(predicate -> predicate.predicate),
                    Vec3i.createOffsetCodec(16)
                            .optionalFieldOf("offset", Vec3i.ZERO)
                            .forGetter(predicate -> predicate.offset),
                    Shape.CODEC.fieldOf("shape").forGetter(predicate -> predicate.shape),
                    Codec.BOOL.fieldOf("ignore_center").forGetter(predicate -> predicate.ignoreCenter),
                    Codec.BOOL.optionalFieldOf("ignore_origin", false).forGetter(predicate -> predicate.ignoreOrigin),
                    Codec.intRange(1, 32)
                            .fieldOf("max_distance_from_center")
                            .forGetter(predicate -> predicate.maxDistanceFromCenter)
            ).apply(instance, WithinHorizontalRangeBlockPredicate::new)
    );

    protected final BlockPredicate predicate;

    protected final Vec3i offset;
    protected final boolean ignoreCenter;
    protected final boolean ignoreOrigin;
    protected final Shape shape;
    protected final int maxDistanceFromCenter;

    /**
     * @see TaiaoBlockPredicates#withinHorizontalRange(BlockPredicate, Shape, boolean, int)
     * @see TaiaoBlockPredicates#withinHorizontalRange(BlockPredicate, Vec3i, Shape, boolean, boolean, int)
     */
    protected WithinHorizontalRangeBlockPredicate(
            BlockPredicate predicate,
            Vec3i offset,
            Shape shape,
            boolean ignoreCenter,
            boolean ignoreOrigin,
            int maxDistanceFromCenter
    ) {
        this.predicate = predicate;
        this.offset = offset;
        this.ignoreCenter = ignoreCenter;
        this.ignoreOrigin = ignoreOrigin;
        this.shape = shape;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    @Override
    public boolean test(StructureWorldAccess world, @NotNull BlockPos origin) {
        BlockPos center = origin.add(this.offset);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int dx = -this.maxDistanceFromCenter; dx <= this.maxDistanceFromCenter; dx++) {
            for (int dz = -this.maxDistanceFromCenter; dz <= this.maxDistanceFromCenter; dz++) {
                mutable.set(center, dx, 0, dz);

                // Skip the center
                if (ignoreCenter && (dx == 0 && dz == 0)) continue;
                // Skip the origin
                if (ignoreOrigin && mutable.equals(origin)) continue;
                // Skip positions out of range
                if (this.shape == Shape.CIRCLE && (dx * dx) + (dz * dz) > this.maxDistanceFromCenter) continue;

                if (this.predicate.test(world, mutable)) return true;
            }
        }

        return false;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return TaiaoBlockPredicates.WITHIN_HORIZONTAL_RANGE;
    }

    /**
     * The shape of the area to check for matches within.
     */
    public enum Shape implements StringIdentifiable {
        /**
         * A square shape.
         * <p>
         * The distance from the center will be larger at the corners/diagonals.
         */
        SQUARE("square"),
        /**
         * A circular shape.
         * <p>
         * The distance from the center will be constant.
         */
        CIRCLE("circle");

        public static final com.mojang.serialization.Codec<Shape> CODEC = StringIdentifiable.createCodec(Shape::values);

        private final String name;

        Shape(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
