// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity;

import antikyth.taiao.block.HaastsEagleNestBlock;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.sound.TaiaoSoundEvents;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class HaastsEagleNestBlockEntity extends BlockEntity {
	public static final String EGG_KEY = "Egg";
	public static final String CHICK_KEY = "Chick";

	protected ItemStack egg = ItemStack.EMPTY;
	protected @Nullable Chick chick;

	/**
	 * A map of egg stages to the next incubation stage.
	 * <p>
	 * If an egg does not have a next incubation stage, then its next stage will be hatching.
	 * <p>
	 * This is used to find the {@link HaastsEagleNestBlockEntity#EGGS EGGS} allowed in the nest.
	 */
	public static final Map<ItemConvertible, ItemConvertible> INCUBATIONS = Map.of(
		TaiaoItems.HAASTS_EAGLE_EGG, TaiaoItems.PARTIALLY_CRACKED_HAASTS_EAGLE_EGG,
		TaiaoItems.PARTIALLY_CRACKED_HAASTS_EAGLE_EGG, TaiaoItems.CRACKED_HAASTS_EAGLE_EGG
	);
	/**
	 * The eggs allowed in the nest.
	 */
	public static final Set<ItemConvertible> EGGS = ImmutableSet.<ItemConvertible>builder()
		.addAll(INCUBATIONS.keySet())
		.addAll(INCUBATIONS.values())
		.build();

	public HaastsEagleNestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public HaastsEagleNestBlockEntity(BlockPos pos, BlockState state) {
		this(TaiaoBlockEntities.HAASTS_EAGLE_NEST, pos, state);
	}

	public boolean hasEgg() {
		return !this.egg.isEmpty();
	}

	public ItemStack getEgg() {
		return this.egg;
	}

	public boolean hasChick() {
		return this.chick != null;
	}

	public @Nullable Entity getOrCreateRenderedEntity(World world) {
		return this.chick == null ? null : this.chick.getOrCreateRenderedEntity(world);
	}

	/**
	 * Adds an {@code egg} to the nest.
	 * <p>
	 * The stack is added if it is within {@link HaastsEagleNestBlockEntity#EGGS EGGS} and there is
	 * no egg already in the nest.
	 * <p>
	 * The nest's {@link HaastsEagleNestBlock#EGG_CONDITION} should be updated after calling this.
	 */
	public boolean addEgg(ItemStack egg) {
		if (this.egg.isEmpty() && !egg.isEmpty() && EGGS.contains(egg.getItem())) {
			this.egg = egg.split(1);

			this.markDirty();

			return true;
		}

		return false;
	}

	/**
	 * Removes an egg from the nest.
	 * <p>
	 * The nest's {@link HaastsEagleNestBlock#EGG_CONDITION} should be updated after calling this.
	 *
	 * @return the egg that was removed; may be empty if there was no egg in the nest
	 */
	public ItemStack removeEgg() {
		ItemStack egg = this.egg;
		this.egg = ItemStack.EMPTY;

		if (!egg.isEmpty()) {
			this.markDirty();
		}

		return egg;
	}

	/**
	 * Incubates the egg inside.
	 * <p>
	 * If the egg has a following
	 * {@linkplain HaastsEagleNestBlockEntity#INCUBATIONS incubation stage}, the egg changes to that
	 * stage. Otherwise, if there is no chick already in the nest, the egg hatches into a chick.
	 *
	 * @param random used to choose the amount of time a hatched chick stays in the nest
	 */
	public void incubate(World world, BlockPos pos, BlockState state, Random random) {
		if (this.hasEgg()) {
			ItemConvertible nextStage = INCUBATIONS.get(this.egg.getItem());

			if (nextStage != null) {
				// Incubation
				world.playSound(
					null,
					pos,
					TaiaoSoundEvents.ENTITY_HAASTS_EAGLE_EGG_CRACK,
					SoundCategory.BLOCKS,
					0.7f,
					0.9f + random.nextFloat() * 0.2f
				);

				ItemStack oldEgg = this.egg;
				this.egg = new ItemStack(nextStage, oldEgg.getCount());
				this.egg.setNbt(oldEgg.getNbt());

				this.contentsChanged(world, pos, state, null);
			} else if (!this.hasChick()) {
				// Hatching
				world.playSound(
					null,
					pos,
					TaiaoSoundEvents.ENTITY_HAASTS_EAGLE_EGG_HATCH,
					SoundCategory.BLOCKS,
					0.7f,
					0.9f + random.nextFloat() * 0.2f
				);

				this.egg.decrement(1);
				this.chick = createChick(random);

				this.contentsChanged(world, pos, state, null);
			}
		}
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
				blockEntity.releaseChick(world, pos, state);
			}
		}
	}

	protected void releaseChick(World world, BlockPos pos, BlockState state) {
		if (this.chick != null) {
			// TODO: check if there is enough room to release

			Entity entity = this.chick.createReleasedEntity(world);
			this.chick = null;

			// TODO: set position, angle, and spawn in world

			this.contentsChanged(world, pos, state, entity);
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

	protected void contentsChanged(
		@NotNull World world,
		BlockPos pos,
		@NotNull BlockState state,
		@Nullable Entity user
	) {
		this.markDirty();

		BlockState newState = state.with(HaastsEagleNestBlock.EGG_CONDITION, HaastsEagleNestBlock.getContents(this));

		world.setBlockState(pos, newState, Block.NOTIFY_ALL);
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(user, newState));
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
