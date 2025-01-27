// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.leaves;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class SlowMovementLeavesBlock extends LeavesBlock {
    protected final Vec3d slowMovement;

    public SlowMovementLeavesBlock(Settings settings) {
        this(settings, new Vec3d(0.8, 0.75, 0.8));
    }

    public SlowMovementLeavesBlock(Settings settings, Vec3d slowMovement) {
        super(settings);

        this.slowMovement = slowMovement;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.slowMovement(state, this.slowMovement);
        }
    }
}
