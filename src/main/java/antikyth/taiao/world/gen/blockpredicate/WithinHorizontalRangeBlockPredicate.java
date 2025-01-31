// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.blockpredicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("range").forGetter(predicate -> predicate.range),
                    Vec3i.createOffsetCodec(16)
                            .optionalFieldOf("offset", Vec3i.ZERO)
                            .forGetter(predicate -> predicate.offset)
            ).apply(instance, WithinHorizontalRangeBlockPredicate::new)
    );

    protected final BlockPredicate predicate;
    protected final int range;
    protected final Vec3i offset;

    /**
     * Creates a predicate testing whether any blocks match the given {@code predicate} within horizontal range.
     *
     * @param predicate the predicate to check blocks within range against
     * @param range     the maximum horizontal range within which to check matching blocks
     * @param offset    the offset of the center position of the range
     */
    public WithinHorizontalRangeBlockPredicate(BlockPredicate predicate, int range, Vec3i offset) {
        this.predicate = predicate;
        this.range = range;
        this.offset = offset;
    }

    @Override
    public boolean test(StructureWorldAccess world, @NotNull BlockPos pos) {
        BlockPos center = pos.add(this.offset);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int dx = -this.range; dx < this.range; dx++) {
            for (int dz = -this.range; dz < this.range; dz++) {
                // Skip the center position.
                if (dx == 0 && dz == 0) continue;
                // Skip positions out of range.
                if ((dx * dx) + (dz * dz) > this.range) continue;

                mutable.set(center, dx, 0, dz);
                if (this.predicate.test(world, mutable)) return true;
            }
        }

        return false;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return TaiaoBlockPredicates.WITHIN_HORIZONTAL_RANGE;
    }
}
