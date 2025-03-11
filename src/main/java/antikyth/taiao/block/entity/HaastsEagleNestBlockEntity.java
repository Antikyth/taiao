// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity;

import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.item.HaastsEagleEggItem;
import antikyth.taiao.item.TaiaoItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
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
	public static final String EGG_KEY = "Egg";
	public static final String CHICK_KEY = "Chick";

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

	public @Nullable Entity getOrCreateRenderedEntity(World world) {
		return this.chick == null ? null : this.chick.getOrCreateRenderedEntity(world);
	}

	/**
	 * Adds a {@link TaiaoItems#HAASTS_EAGLE_EGG} to the nest.
	 * <p>
	 * The stack is added if it is a {@link TaiaoItems#HAASTS_EAGLE_EGG} and there is no egg already
	 * in the nest.
	 * <p>
	 * If the {@code egg} does not already have a hatching time, {@code random} is used to choose
	 * it.
	 */
	public boolean addEgg(@Nullable Entity user, ItemStack egg, Random random) {
		if (this.egg.isEmpty() && !egg.isEmpty() && egg.isOf(TaiaoItems.HAASTS_EAGLE_EGG)) {
			this.egg = egg.split(1);
			this.initializeEgg(random);

			this.blockChanged(user);

			return true;
		}

		return false;
	}

	protected void initializeEgg(@NotNull Random random) {
		HaastsEagleEggItem.getOrInitializeHatchingTime(this.egg, 10000 + random.nextInt(4000));
	}

	public ItemStack removeEgg(@Nullable Entity user) {
		ItemStack egg = this.egg;
		this.egg = ItemStack.EMPTY;

		if (!egg.isEmpty()) this.blockChanged(user);

		return egg;
	}

	public static void tick(
		World world,
		BlockPos ignoredPos,
		BlockState ignoredState,
		@NotNull HaastsEagleNestBlockEntity blockEntity
	) {
		if (blockEntity.chick != null) {
			if (blockEntity.chick.isReadyForRelease()) {
				// TODO: update contents

				blockEntity.blockChanged(null);
			} else {
				blockEntity.chick.tick();
			}
		}

		if (HaastsEagleEggItem.isReadyToHatch(blockEntity.egg)) {
			if (hatchEgg(blockEntity, world.random)) {
				// TODO: update contents

				blockEntity.blockChanged(null);
			}
		} else {
			// Age the egg
			HaastsEagleEggItem.decrementHatchingTime(blockEntity.egg, 1);
		}
	}

	/**
	 * Hatches the egg into a chick.
	 *
	 * @param random used to choose the amount of time the chick will stay in the nest
	 * @return whether the egg was successfully hatched
	 */
	protected static boolean hatchEgg(@NotNull HaastsEagleNestBlockEntity blockEntity, Random random) {
		if (!blockEntity.hasChick() && blockEntity.hasEgg()) {
			blockEntity.egg.decrement(1);
			blockEntity.chick = createChick(random);

			return true;
		}

		return false;
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

		return new Chick(entityNbt, 0, 10000 + random.nextInt(4000));
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

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);

		this.egg = ItemStack.fromNbt(nbt.getCompound(EGG_KEY));
		this.chick = nbt.contains(CHICK_KEY, NbtElement.COMPOUND_TYPE)
			? Chick.fromNbt(nbt.getCompound(CHICK_KEY))
			: null;
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);

		// Egg
		NbtCompound eggNbt = new NbtCompound();
		this.egg.writeNbt(eggNbt);

		nbt.put(EGG_KEY, eggNbt);

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
		NbtCompound nbt = new NbtCompound();

		// Chick - only the chick is used in the block entity renderer
		if (this.chick != null) {
			NbtCompound chickNbt = new NbtCompound();
			this.chick.writeNbt(chickNbt);

			nbt.put(CHICK_KEY, chickNbt);
		}

		return nbt;
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

		public Entity getOrCreateRenderedEntity(World world) {
			if (this.renderedEntity == null) {
				this.renderedEntity = EntityType.loadEntityWithPassengers(this.nbt, world, Function.identity());
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
