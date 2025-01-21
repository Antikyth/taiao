// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.feature.tree.placer.trunk;

import antikyth.taiao.world.gen.feature.tree.placer.TaiaoTreePlacers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * A straight trunk supporting {@link net.minecraft.block.ConnectingBlock}s, with the top log connecting in all
 * horizontal directions.
 */
public class ThinStraightTrunkPlacer extends ThinTrunkPlacer {
    public static final Codec<ThinStraightTrunkPlacer> CODEC = RecordCodecBuilder.create(
            instance -> fillTrunkPlacerFields(instance).apply(instance, ThinStraightTrunkPlacer::new)
    );

    public ThinStraightTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return TaiaoTreePlacers.THIN_STRAIGHT_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(
            TestableWorld world,
            BiConsumer<BlockPos, BlockState> replacer,
            Random random,
            int height,
            BlockPos startPos,
            TreeFeatureConfig config
    ) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        // Straight trunk section
        for (int i = 0; i < height - 1; i++) {
            mutable.set(startPos, 0, i, 0);

            this.placeVerticalLog(world, replacer, random, mutable, config);
        }

        // Top cross block
        mutable.move(Direction.UP);
        this.placeFullyConnectedLog(world, replacer, random, mutable, config);

        return List.of(new FoliagePlacer.TreeNode(mutable.up(), 0, false));
    }
}
