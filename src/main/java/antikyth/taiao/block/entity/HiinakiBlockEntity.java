// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity;

import antikyth.taiao.block.HiinakiBlock;
import antikyth.taiao.entity.damage.TaiaoDamageTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class HiinakiBlockEntity extends BlockEntity {
	protected static final String BAIT_KEY = "Bait";
	protected static final String TRAPPED_ENTITY_KEY = "TrappedEntity";

	protected static final String ENTITY_DATA_KEY = "EntityData";
	protected static final String TICKS_IN_HIINAKI_KEY = "TicksInHiinaki";

	protected ItemStack bait = ItemStack.EMPTY;

	@Nullable
	protected TrappedEntity trappedEntity;
	/**
	 * An entityNbt representing the currently trapped entityNbt (used purely for rendering purposes).
	 */
	@Nullable
	protected Entity renderedEntity;

	public HiinakiBlockEntity(
		BlockEntityType<?> type,
		BlockPos pos,
		BlockState state
	) {
		super(type, pos, state);
	}

	public HiinakiBlockEntity(BlockPos pos, BlockState state) {
		this(TaiaoBlockEntities.HIINAKI, pos, state);
	}

	/**
	 * {@return whether the entrance to the hīnaki is blocked}
	 */
	public boolean isEntranceBlocked() {
		if (this.world != null) {
			BlockPos pos = this.getPos();
			BlockState state = this.getCachedState();
			Direction facing = state.get(HiinakiBlock.FACING);

			return Block.sideCoversSmallSquare(this.world, pos.offset(facing), facing.getOpposite());
		} else {
			return false;
		}
	}

	public boolean hasBait() {
		return !this.bait.isEmpty();
	}

	public ItemStack getBait() {
		return this.bait;
	}

	/**
	 * Adds bait to the hīnaki.
	 *
	 * @param user the entityNbt adding bait
	 * @param bait the bait to add
	 * @return whether the bait was taken/added
	 */
	public boolean addBait(@Nullable Entity user, ItemStack bait) {
		if (this.bait.isEmpty() && !bait.isEmpty()) {
			this.bait = bait.split(1);

			this.blockChanged(user);

			return true;
		}

		return false;
	}

	public ItemStack removeBait(@Nullable Entity user) {
		ItemStack bait = this.bait;
		this.bait = ItemStack.EMPTY;

		if (!bait.isEmpty()) this.blockChanged(user);

		return bait;
	}

	public boolean hasTrappedEntity() {
		return this.trappedEntity != null;
	}

	/**
	 * Returns the yaw in degrees relative to {@link Direction#NORTH}.
	 */
	public float getYaw() {
		return (this.getCachedState().get(HiinakiBlock.FACING).asRotation() + 180f) % 360f;
	}

	/**
	 * {@return an entity representing the currently trapped entity (only to be used for rendering purposes)}
	 */
	public @Nullable Entity getRenderedEntity() {
		if (this.trappedEntity != null) {
			if (this.renderedEntity == null) {
				this.renderedEntity = EntityType.loadEntityWithPassengers(
					this.trappedEntity.nbt,
					this.world,
					Function.identity()
				);
			}
		} else {
			this.renderedEntity = null;
		}

		return this.renderedEntity;
	}

//	public static void clientTick(
//		World ignoredWorld,
//		BlockPos ignoredPos,
//		BlockState ignoredState,
//		@NotNull HiinakiBlockEntity blockEntity
//	) {
//		if (blockEntity.renderedEntity != null) {
//			blockEntity.renderedEntity.age++;
//		}
//	}

	/**
	 * Traps the {@code entityNbt} in the hīnaki if no other entityNbt is currently trapped.
	 * <p>
	 * If {@linkplain HiinakiBlockEntity#hasBait() there is currently bait in the hīnaki}, the bait is eaten.
	 *
	 * @return whether the entityNbt was successfully trapped
	 */
	public boolean trapEntity(Entity entity) {
		if (!this.hasTrappedEntity()) {
			// Eat the bait
			if (entity instanceof LivingEntity living) {
				living.eatFood(living.getWorld(), this.bait);
			}

			entity.stopRiding();
			entity.removeAllPassengers();

			NbtCompound entityNbt = new NbtCompound();
			entity.saveNbt(entityNbt);
			this.trappedEntity = new TrappedEntity(entityNbt, 0);

			this.blockChanged(entity);

			entity.discard();
			return true;
		}

		return false;
	}

	/**
	 * Releases the trapped entityNbt.
	 *
	 * @param force whether to release the entityNbt even if the entrance is blocked
	 * @return if the entityNbt was released, the released entityNbt
	 */
	public @Nullable Entity releaseEntity(boolean force, boolean spawn) {
		if (this.trappedEntity != null) {
			BlockPos pos = this.getPos();
			Direction facing = this.getCachedState().get(HiinakiBlock.FACING);
			BlockPos releasePos = pos.offset(facing);
			boolean hasCollision = world != null
				&& !world.getBlockState(releasePos).getCollisionShape(this.world, releasePos).isEmpty();

			// Don't release entityNbt into a solid block unless forced.
			if (hasCollision && !force) return null;

			Entity entity = EntityType.loadEntityWithPassengers(
				this.trappedEntity.nbt,
				this.world,
				Function.identity()
			);
			if (entity != null) {
				entity.age += this.trappedEntity.ticksInHiinaki;
				if (entity instanceof AnimalEntity animal) {
					int age = animal.getBreedingAge();

					if (age < 0) animal.setBreedingAge(Math.min(0, age + this.trappedEntity.ticksInHiinaki));
					else if (age > 0) animal.setBreedingAge(Math.max(0, age - this.trappedEntity.ticksInHiinaki));

					animal.setLoveTicks(Math.max(0, animal.getLoveTicks() - this.trappedEntity.ticksInHiinaki));
				}

				this.trappedEntity = null;

				float width = entity.getWidth();
				double forwardOffset = hasCollision ? 0d : 0.55d + (double) (width / 2f);

				double x = (double) pos.getX() + 0.5d + (forwardOffset * (double) facing.getOffsetX());
				double y = pos.getY();
				double z = (double) pos.getZ() + 0.5d + (forwardOffset * (double) facing.getOffsetZ());

				entity.refreshPositionAndAngles(x, y, z, this.getYaw(), 0f);

				this.blockChanged(entity);

				if (this.world != null && spawn) {
					return this.world.spawnEntity(entity) ? entity : null;
				} else {
					return entity;
				}
			}
		}

		return null;
	}

	/**
	 * Hurts and releases the currently trapped entity.
	 *
	 * @param damage   the amount of damage to deal
	 * @param attacker the entity trying to kill the trapped entity (can be {@code null})
	 * @param force    whether to kill the entity even if the entrance is blocked
	 * @return whether there was a trapped entity to hurt
	 */
	public boolean tryKillTrappedEntity(boolean force, float damage, @Nullable LivingEntity attacker) {
		Entity entity = this.releaseEntity(force, false);

		if (entity != null) {
			boolean damaged = entity.damage(
				entity.getDamageSources().create(TaiaoDamageTypes.HIINAKI, attacker),
				damage
			);

			if (damaged && attacker != null) {
				attacker.applyDamageEffects(attacker, entity);
				attacker.onAttacking(entity);
			}

			if (entity.isAlive() && this.world != null) this.world.spawnEntity(entity);

			return true;
		} else {
			return false;
		}
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

		// Bait
		this.bait = ItemStack.fromNbt(nbt.getCompound(BAIT_KEY));

		// Entity
		if (nbt.contains(TRAPPED_ENTITY_KEY, NbtElement.COMPOUND_TYPE)) {
			NbtCompound entityNbt = nbt.getCompound(TRAPPED_ENTITY_KEY);

			this.trappedEntity = new TrappedEntity(
				entityNbt.getCompound(ENTITY_DATA_KEY),
				entityNbt.getInt(TICKS_IN_HIINAKI_KEY)
			);
		} else {
			this.trappedEntity = null;
		}
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);

		// Bait
		NbtCompound baitNbt = new NbtCompound();
		this.bait.writeNbt(baitNbt);

		nbt.put(BAIT_KEY, baitNbt);

		// Entity
		if (this.trappedEntity != null) {
			NbtCompound entityNbt = new NbtCompound();

			entityNbt.put(ENTITY_DATA_KEY, this.trappedEntity.nbt);
			entityNbt.putInt(TICKS_IN_HIINAKI_KEY, this.trappedEntity.ticksInHiinaki);

			nbt.put(TRAPPED_ENTITY_KEY, entityNbt);
		} else {
			nbt.remove(TRAPPED_ENTITY_KEY);
		}
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		NbtCompound nbt = new NbtCompound();
		// All of this block entity's data is used in rendering and so must be sent to clients.
		this.writeNbt(nbt);

		return nbt;
	}

	@Override
	public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	protected static class TrappedEntity {
		final NbtCompound nbt;
		int ticksInHiinaki;

		TrappedEntity(@NotNull NbtCompound nbt, int ticksInHiinaki) {
			nbt.remove("UUID");

			this.nbt = nbt;
			this.ticksInHiinaki = ticksInHiinaki;
		}
	}
}
