// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity;

import antikyth.taiao.entity.TaiaoEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class HaastsEagleNestBlockEntity extends BlockEntity {
	public static final String CHICK_KEY = "Chick";

	protected @Nullable Chick chick;

	public HaastsEagleNestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public HaastsEagleNestBlockEntity(BlockPos pos, BlockState state) {
		this(TaiaoBlockEntities.HAASTS_EAGLE_NEST, pos, state);
	}

	public boolean hasChick() {
		return this.chick != null;
	}

	public @Nullable Entity getOrCreateRenderedEntity(World world) {
		return this.chick == null ? null : this.chick.getOrCreateRenderedEntity(world);
	}

	/**
	 * The ticker, called each tick on the server.
	 */
	public static void serverTick(
		World world,
		BlockPos pos,
		BlockState state,
		@NotNull HaastsEagleNestBlockEntity blockEntity
	) {
		if (blockEntity.chick != null) {
			if (!blockEntity.chick.isReadyForRelease()) {
				// Age the chick
				blockEntity.chick.tick();
			} else {
				// Chick is old enough to be released
				blockEntity.releaseChick(false, world, pos, state);
			}
		}
	}

	/**
	 * Puts a new chick in the nest if there isn't already one.
	 */
	public void hatchChick(Random random) {
		if (this.chick == null) {
			this.chick = createChick(random);

			this.markDirty();
		}
	}

	/**
	 * Releases the chick from the nest.
	 *
	 * @param force whether to force the chick's release even if there isn't enough room
	 */
	protected void releaseChick(boolean force, World world, BlockPos pos, BlockState state) {
		if (this.chick != null) {
			// TODO: check if there is enough room to release

			Entity entity = this.chick.createReleasedEntity(world);
			this.chick = null;

			// TODO: set position, angle, and spawn in world

			this.blockChanged(world, pos, state, entity);
		}
	}

	/**
	 * Creates a chick.
	 *
	 * @param random used to choose the amount of time the chick will stay in the nest
	 */
	@Contract("_ -> new")
	protected static @NotNull Chick createChick(@NotNull Random random) {
		NbtCompound entityNbt = new NbtCompound();
		// Entity type
		EntityType<?> entityType = TaiaoEntities.HAASTS_EAGLE;
		entityNbt.putString("id", Registries.ENTITY_TYPE.getId(entityType).toString());
		// Baby
		entityNbt.putInt("Age", -24000);

		return new Chick(entityNbt, 10000 + random.nextInt(4000), 0);
	}

	/**
	 * Called when the block contents have changed but the state has not.
	 */
	protected void blockChanged(@NotNull World world, BlockPos pos, BlockState state, @Nullable Entity source) {
		this.markDirty();

		world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(source, state));
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);

		this.chick = nbt.contains(CHICK_KEY, NbtElement.COMPOUND_TYPE)
			? Chick.fromNbt(nbt.getCompound(CHICK_KEY))
			: null;
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);

		// Chick
		if (this.chick != null) {
			NbtCompound chickNbt = new NbtCompound();
			this.chick.writeNbt(chickNbt);

			nbt.put(CHICK_KEY, chickNbt);
		} else {
			nbt.remove(CHICK_KEY);
		}
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return this.createNbt();
	}

	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	public static class Chick {
		static final String ENTITY_KEY = "Entity";
		static final String TICKS_IN_NEST_KEY = "TicksInNest";
		static final String FEED_COUNT_KEY = "FeedCount";

		final NbtCompound nbt;
		int ticksInNest;
		short feedCount;

		@Nullable Entity renderedEntity;

		/**
		 * Creates a chick.
		 *
		 * @param nbt         the entity's NBT data
		 * @param ticksInNest the number of ticks left before the chick leaves the nest
		 * @param feedCount   the number of times the chick has been fed compared to siblings
		 */
		Chick(@NotNull NbtCompound nbt, int ticksInNest, int feedCount) {
			this.nbt = nbt;
			this.ticksInNest = ticksInNest;
			this.feedCount = (short) feedCount;
		}

		boolean isReadyForRelease() {
			return this.ticksInNest <= 0;
		}

		void tick() {
			this.ticksInNest--;
		}

		public Entity createReleasedEntity(World world) {
			return EntityType.loadEntityWithPassengers(this.nbt, world, Function.identity());
		}

		public Entity getOrCreateRenderedEntity(World world) {
			if (this.renderedEntity == null) {
				this.renderedEntity = EntityType.loadEntityWithPassengers(
					this.nbt,
					world,
					entity -> {
						entity.setOnGround(true);

						return entity;
					}
				);
			}

			return this.renderedEntity;
		}

		static @NotNull Chick fromNbt(@NotNull NbtCompound nbt) {
			NbtCompound entityNbt = nbt.getCompound(ENTITY_KEY);
			int ticksInNest = nbt.getInt(TICKS_IN_NEST_KEY);
			short feedCount = nbt.getShort(FEED_COUNT_KEY);

			return new Chick(entityNbt, ticksInNest, feedCount);
		}

		void writeNbt(@NotNull NbtCompound nbt) {
			nbt.put(ENTITY_KEY, this.nbt);
			nbt.putInt(TICKS_IN_NEST_KEY, this.ticksInNest);
			nbt.putShort(FEED_COUNT_KEY, this.feedCount);
		}
	}
}
