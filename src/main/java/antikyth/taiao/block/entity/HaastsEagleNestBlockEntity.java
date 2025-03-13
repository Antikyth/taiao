// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity;

import antikyth.taiao.block.state.HaastsEagleEggStage;
import antikyth.taiao.entity.TaiaoEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class HaastsEagleNestBlockEntity extends BlockEntity implements BlockEntityWithTicker {
	public static final String CHICK_KEY = "Chick";

	protected @Nullable Chick chick;

	public HaastsEagleNestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public HaastsEagleNestBlockEntity(BlockPos pos, BlockState state) {
		this(TaiaoBlockEntities.HAASTS_EAGLE_NEST, pos, state);
	}

	/**
	 * {@return whether there is a chick in the nest}
	 */
	public boolean hasChick() {
		return this.chick != null;
	}

	/**
	 * {@return the chick within the nest, if there is one}
	 */
	public @Nullable Chick getChick() {
		return this.chick;
	}

	/**
	 * Returns an {@link Entity} to be used for rendering the block entity.
	 * <p>
	 * This must not be used for purposes other than rendering, as it may differ from the entity
	 * upon release.
	 */
	public @Nullable Entity getOrCreateRenderedEntity(World world) {
		return this.chick == null ? null : this.chick.getOrCreateRenderedEntity(world);
	}

	/**
	 * Puts a new chick in the nest if there isn't already one.
	 */
	public void hatchChick() {
		if (this.chick == null) {
			this.chick = createChick();

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
	 */
	protected static @NotNull Chick createChick() {
		NbtCompound entityNbt = new NbtCompound();
		// Entity type
		EntityType<?> entityType = TaiaoEntities.HAASTS_EAGLE;
		entityNbt.putString("id", Registries.ENTITY_TYPE.getId(entityType).toString());
		// Baby
		entityNbt.putInt("Age", -24000);

		return new Chick(entityNbt, false);
	}

	@Override
	public void serverTick(World world, BlockPos pos, BlockState state) {
		if (this.chick != null) {
			if (this.chick.isReadyForRelease()) {
				// Chick is old enough to be released
				this.releaseChick(false, world, pos, state);
			} else {
				// Age the chick
				this.chick.tick();

				this.markDirty(this.chick.shouldUpdateComparators());
			}
		}
	}

	/**
	 * Called when the block contents have changed but the state has not.
	 * <p>
	 * This
	 * {@linkplain HaastsEagleNestBlockEntity#markDirty() marks the block entity as dirty}
	 * (triggering a {@linkplain World#updateComparators comparator update}),
	 * {@linkplain World#updateListeners updates listeners}
	 * (triggering a {@linkplain HaastsEagleNestBlockEntity#toUpdatePacket server-to-client update packet}),
	 * and emits a {@link GameEvent#BLOCK_CHANGE}.
	 *
	 * @param source the entity that triggered this update, if any (e.g. the player or a hatched
	 *               chick)
	 */
	protected void blockChanged(@NotNull World world, BlockPos pos, BlockState state, @Nullable Entity source) {
		world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(source, state));

		this.markDirty();
	}

	/**
	 * Marks the block entity as dirty.
	 *
	 * @param updateComparators whether {@linkplain World#updateComparators comparators should be updated}
	 * @see HaastsEagleNestBlockEntity#markDirty()
	 * @see HaastsEagleNestBlockEntity#markDirtyWithoutComparatorUpdate()
	 */
	protected void markDirty(boolean updateComparators) {
		if (updateComparators) {
			this.markDirty();
		} else {
			this.markDirtyWithoutComparatorUpdate();
		}
	}

	/**
	 * {@linkplain HaastsEagleNestBlockEntity#markDirty() Marks the block entity as dirty} without
	 * triggering a {@linkplain World#updateComparators comparator update}.
	 * <p>
	 * This avoids unnecessarily triggering comparator updates for changes which don't affect the
	 * comparator output, particularly useful for changes which happen very frequently, e.g. each
	 * tick.
	 *
	 * @see HaastsEagleNestBlockEntity#markDirtyWithoutComparatorUpdate(World, BlockPos)
	 * @see HaastsEagleNestBlockEntity#markDirty()
	 * @see HaastsEagleNestBlockEntity#blockChanged(World, BlockPos, BlockState, Entity)
	 */
	protected void markDirtyWithoutComparatorUpdate() {
		if (this.world != null) {
			this.markDirtyWithoutComparatorUpdate(this.world, this.pos);
		}
	}

	/**
	 * {@linkplain HaastsEagleNestBlockEntity#markDirty(World, BlockPos, BlockState) Marks the block entity as dirty}
	 * without triggering a {@linkplain World#updateComparators comparator update}.
	 * <p>
	 * This avoids unnecessarily triggering comparator updates for changes which don't affect the
	 * comparator output, particularly useful for changes which happen very frequently, e.g. each
	 * tick.
	 *
	 * @see HaastsEagleNestBlockEntity#markDirtyWithoutComparatorUpdate()
	 * @see HaastsEagleNestBlockEntity#markDirty(World, BlockPos, BlockState)
	 * @see HaastsEagleNestBlockEntity#blockChanged(World, BlockPos, BlockState, Entity)
	 */
	protected void markDirtyWithoutComparatorUpdate(@NotNull World world, BlockPos pos) {
		markDirty(world, pos, Blocks.AIR.getDefaultState());
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
			nbt.put(CHICK_KEY, this.chick.createNbt());
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
		static final String TICKS_LEFT_IN_NEST_KEY = "TicksLeftInNest";
		static final String HAS_BEEN_FED_KEY = "Fed";

		final NbtCompound nbt;
		int ticksLeftInNest;
		boolean hasBeenFed;

		/**
		 * The comparator output last time {@link Chick#shouldUpdateComparators()} was called.
		 * <p>
		 * This is used to only update comparators if there have been changes to the output.
		 */
		int lastComparatorOutput;

		@Nullable Entity renderedEntity;

		/**
		 * Creates a chick.
		 *
		 * @param nbt             the entity's NBT data
		 * @param ticksLeftInNest the number of ticks left before the chick leaves the nest
		 * @param hasBeenFed      whether the chick has been fed compared to its siblings
		 */
		Chick(@NotNull NbtCompound nbt, int ticksLeftInNest, boolean hasBeenFed) {
			this.nbt = nbt;
			this.ticksLeftInNest = ticksLeftInNest;
			this.hasBeenFed = hasBeenFed;
		}

		Chick(@NotNull NbtCompound nbt, boolean hasBeenFed) {
			this(nbt, getMinTicksInNestForRelease(), hasBeenFed);
		}

		/**
		 * Whether this chick has been fed compared to its siblings.
		 * <p>
		 * This is used for Haast's eagle parents to decide which chick to feed, and is reset after
		 * all chicks and the parent have eaten.
		 */
		public boolean hasBeenFed() {
			return this.hasBeenFed;
		}

		/**
		 * Sets whether this chick has been fed compared to its siblings.
		 * <p>
		 * This is used for Haast's eagle parents to decide which chick to feed.
		 */
		public void setHasBeenFed(boolean hasBeenFed) {
			this.hasBeenFed = hasBeenFed;
		}

		/**
		 * {@return the minimum number of ticks before the chick can be released}
		 */
		static int getMinTicksInNestForRelease() {
			return 24_000;
		}

		/**
		 * {@return the number of ticks the chick has left in the nest}
		 */
		int getTicksLeftInNest() {
			return this.ticksLeftInNest;
		}

		/**
		 * {@return the number of ticks the chick has been in the nest}
		 */
		int getTicksBeenInNest() {
			return getMinTicksInNestForRelease() - this.getTicksLeftInNest();
		}

		public boolean isReadyForRelease() {
			return this.ticksLeftInNest <= 0;
		}

		/**
		 * Ticks the chick.
		 */
		void tick() {
			this.ticksLeftInNest--;
		}

		/**
		 * Calculates the comparator output based on how long the chick has been in the nest.
		 */
		public int getComparatorOutput() {
			int eggStageCount = HaastsEagleEggStage.values().length;
			// The number of signals left after accounting for egg signals
			int signalRange = 15 - eggStageCount;

			return eggStageCount + (this.getTicksBeenInNest() * signalRange / getMinTicksInNestForRelease());
		}

		/**
		 * Returns whether the {@linkplain Chick#getComparatorOutput() comparator output} has
		 * changed since this method was last called.
		 */
		public boolean shouldUpdateComparators() {
			int output = this.getComparatorOutput();
			boolean update = output != this.lastComparatorOutput;
			this.lastComparatorOutput = output;

			return update;
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
			boolean hasBeenFed = nbt.getBoolean(HAS_BEEN_FED_KEY);

			if (nbt.contains(TICKS_LEFT_IN_NEST_KEY, NbtElement.INT_TYPE)) {
				return new Chick(entityNbt, nbt.getInt(TICKS_LEFT_IN_NEST_KEY), hasBeenFed);
			} else {
				return new Chick(entityNbt, hasBeenFed);
			}
		}

		void writeNbt(@NotNull NbtCompound nbt) {
			nbt.put(ENTITY_KEY, this.nbt);
			nbt.putInt(TICKS_LEFT_IN_NEST_KEY, this.ticksLeftInNest);
			nbt.putBoolean(HAS_BEEN_FED_KEY, this.hasBeenFed);
		}

		NbtCompound createNbt() {
			NbtCompound nbt = new NbtCompound();
			this.writeNbt(nbt);

			return nbt;
		}
	}
}
