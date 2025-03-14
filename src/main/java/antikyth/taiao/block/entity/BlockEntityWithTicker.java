// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * An interface simplifying block-state-independent {@link BlockEntityTicker}s.
 */
public interface BlockEntityWithTicker {
	/**
	 * A {@link BlockEntityTicker} that uses {@link BlockEntityWithTicker#clientTicker} on the
	 * client and {@link BlockEntityWithTicker#serverTicker} on the server.
	 * <p>
	 * Use this ticker only if you have both client and server ticks.
	 */
	static void ticker(
		@NotNull World world,
		BlockPos pos,
		BlockState state,
		@NotNull BlockEntityWithTicker blockEntity
	) {
		if (world.isClient) {
			clientTicker(world, pos, state, blockEntity);
		} else {
			serverTicker(world, pos, state, blockEntity);
		}
	}

	/**
	 * A {@link BlockEntityTicker} that calls {@link BlockEntityWithTicker#tick} and
	 * {@link BlockEntityWithTicker#clientTick}.
	 * <p>
	 * Use this ticker only on the client.
	 */
	static void clientTicker(
		@NotNull World world,
		BlockPos pos,
		BlockState state,
		@NotNull BlockEntityWithTicker blockEntity
	) {
		blockEntity.tick(world, pos, state);
		blockEntity.clientTick(world, pos, state);
	}

	/**
	 * A {@link BlockEntityTicker} that calls {@link BlockEntityWithTicker#tick} and
	 * {@link BlockEntityWithTicker#serverTick}.
	 * <p>
	 * Use this ticker only on the server.
	 */
	static void serverTicker(
		@NotNull World world,
		BlockPos pos,
		BlockState state,
		@NotNull BlockEntityWithTicker blockEntity
	) {
		blockEntity.tick(world, pos, state);
		blockEntity.serverTick(world, pos, state);
	}

	/**
	 * Called on both the client and server every tick.
	 *
	 * @see BlockEntityWithTicker#ticker
	 * @see BlockEntityWithTicker#clientTick(World, BlockPos, BlockState)
	 * @see BlockEntityWithTicker#serverTick(World, BlockPos, BlockState)
	 */
	default void tick(World world, BlockPos pos, BlockState state) {}

	/**
	 * Called on the client every tick.
	 *
	 * @see BlockEntityWithTicker#clientTicker
	 * @see BlockEntityWithTicker#ticker
	 * @see BlockEntityWithTicker#tick(World, BlockPos, BlockState)
	 * @see BlockEntityWithTicker#serverTick(World, BlockPos, BlockState)
	 */
	default void clientTick(World world, BlockPos pos, BlockState state) {}

	/**
	 * Called on the server every tick.
	 *
	 * @see BlockEntityWithTicker#serverTicker
	 * @see BlockEntityWithTicker#ticker
	 * @see BlockEntityWithTicker#tick(World, BlockPos, BlockState)
	 * @see BlockEntityWithTicker#clientTick(World, BlockPos, BlockState)
	 */
	default void serverTick(World world, BlockPos pos, BlockState state) {}
}
