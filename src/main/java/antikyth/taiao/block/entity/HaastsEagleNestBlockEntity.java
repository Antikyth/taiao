// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity;

import antikyth.taiao.item.TaiaoItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HaastsEagleNestBlockEntity extends BlockEntity {
	protected ItemStack egg = ItemStack.EMPTY;
	protected @Nullable Chick chick;

	public HaastsEagleNestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public HaastsEagleNestBlockEntity(BlockPos pos, BlockState state) {
		this(TaiaoBlockEntities.HAASTS_EAGLE_NEST, pos, state);
	}

	public boolean hasEgg() {
		return !this.egg.isEmpty();
	}

	public boolean hasChick() {
		return this.chick != null;
	}

	/**
	 * Adds a {@link TaiaoItems#HAASTS_EAGLE_EGG} to the nest.
	 * <p>
	 * The stack is added if it is a {@link TaiaoItems#HAASTS_EAGLE_EGG} and there is no egg already
	 * in the nest.
	 */
	public boolean addEgg(@Nullable Entity user, ItemStack egg) {
		if (this.egg.isEmpty() && !egg.isEmpty() && egg.isOf(TaiaoItems.HAASTS_EAGLE_EGG)) {
			this.egg = egg.split(1);

			this.blockChanged(user);

			return true;
		}

		return false;
	}

	public ItemStack removeEgg(@Nullable Entity user) {
		ItemStack egg = this.egg;
		this.egg = ItemStack.EMPTY;

		if (!egg.isEmpty()) this.blockChanged(user);

		return egg;
	}

	protected void blockChanged(@Nullable Entity user) {
		this.markDirty();

		if (this.world != null) {
			this.world.emitGameEvent(
				GameEvent.BLOCK_CHANGE,
				this.getPos(),
				GameEvent.Emitter.of(user, this.getCachedState())
			);

			this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
		}
	}

	protected static class Chick {
		final NbtCompound nbt;
		int ticksInNest;
		int feedCount;

		Chick(@NotNull NbtCompound nbt, int feedCount) {
			this(nbt, 0, feedCount);
		}

		Chick(@NotNull NbtCompound nbt, int ticksInNest, int feedCount) {
			this.nbt = nbt;
			this.ticksInNest = ticksInNest;
			this.feedCount = feedCount;
		}
	}
}
