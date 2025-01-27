// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.leaves;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FruitLeavesBlock extends LeavesBlock implements Fertilizable {
    public static final BooleanProperty FRUIT = BooleanProperty.of("fruit");
    public final Item fruitItem;

    public FruitLeavesBlock(Item fruitItem, Settings settings) {
        super(settings);

        this.fruitItem = fruitItem;
        this.setDefaultState(this.getDefaultState().with(FRUIT, false));
    }

    public ActionResult pickFruit(@Nullable Entity picker, @NotNull BlockState state, World world, BlockPos pos) {
        if (state.get(FRUIT)) {
            FruitLeavesBlock.dropStack(world, pos, new ItemStack(this.fruitItem, 1));

            world.playSound(
                    null,
                    pos,
                    SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES,
                    SoundCategory.BLOCKS,
                    1f,
                    MathHelper.nextBetween(world.random, 0.8f, 1.2f)
            );

            world.setBlockState(pos, state.with(FRUIT, false), FruitLeavesBlock.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(picker, state));

            return ActionResult.success(world.isClient);
        }

        return ActionResult.PASS;
    }

    @Override
    public ActionResult onUse(
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            Hand hand,
            BlockHitResult hit
    ) {
        return this.pickFruit(player, state, world, pos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(FRUIT);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, @NotNull BlockState state, boolean isClient) {
        return !state.get(FRUIT);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(@NotNull ServerWorld world, Random random, BlockPos pos, @NotNull BlockState state) {
        world.setBlockState(pos, state.with(FRUIT, true), FruitLeavesBlock.NOTIFY_LISTENERS);
    }
}
