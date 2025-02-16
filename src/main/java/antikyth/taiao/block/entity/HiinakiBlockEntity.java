// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity;

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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

public class HiinakiBlockEntity extends BlockEntity {
	protected static final String BAIT_KEY = "Bait";
	protected static final String TRAPPED_ENTITY_KEY = "TrappedEntity";

	protected ItemStack bait = ItemStack.EMPTY;

	@Nullable
	protected Entity trappedEntity;

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

	public boolean hasBait() {
		return !this.bait.isEmpty();
	}

	public ItemStack getBait() {
		return this.bait;
	}

	/**
	 * Adds bait to the hīnaki.
	 *
	 * @param user the entity adding bait
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
		return this.trappedEntity != null && this.trappedEntity.isAlive();
	}

	public @Nullable Entity getTrappedEntity() {
		return this.trappedEntity;
	}

	/**
	 * Traps the {@code entity} in the hīnaki if no other entity is currently trapped.
	 * <p>
	 * If {@linkplain HiinakiBlockEntity#hasBait() there is currently bait in the hīnaki}, the bait is eaten.
	 *
	 * @return whether the entity was successfully trapped
	 */
	public boolean trapEntity(Entity entity) {
		if (!this.hasTrappedEntity()) {
			// TODO: play sound?

			// Eat the bait
			this.bait.decrement(1);

			entity.stopRiding();
			entity.setPosition(this.getPos().toCenterPos());
			entity.setRemoved(Entity.RemovalReason.CHANGED_DIMENSION);

			this.trappedEntity = entity;

			this.blockChanged(entity);
			return true;
		}

		return false;
	}

	/**
	 * {@linkplain Entity#kill() Kills} the {@linkplain HiinakiBlockEntity#getTrappedEntity() currently trapped entity}.
	 *
	 * @return whether there was an entity to kill
	 */
	public boolean killTrappedEntity() {
		if (this.hasTrappedEntity()) {
			Entity entity = Objects.requireNonNull(this.getTrappedEntity());

			entity.kill();
			this.trappedEntity = null;

			this.blockChanged(null);
			return true;
		}

		return false;
	}

	public boolean freeTrappedEntity(boolean update) {
		if (this.hasTrappedEntity()) {
			Entity entity = Objects.requireNonNull(this.getTrappedEntity());

			if (this.world != null && this.world instanceof ServerWorld serverWorld) {
				Entity newEntity = entity.getType().create(this.world);
				if (newEntity != null) {
					newEntity.copyFrom(entity);
					newEntity.refreshPositionAndAngles(this.getPos(), entity.getYaw(), entity.getPitch());
				}

				serverWorld.onDimensionChanged(newEntity);
				this.trappedEntity = null;

				if (update) this.blockChanged(null);
				return true;
			}
		}

		return false;
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
			this.trappedEntity = EntityType.loadEntityWithPassengers(
				nbt.getCompound(TRAPPED_ENTITY_KEY),
				this.world,
				Function.identity()
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
		if (this.trappedEntity == null) {
			nbt.remove(TRAPPED_ENTITY_KEY);
		} else {
			NbtCompound entityNbt = new NbtCompound();
			this.trappedEntity.writeNbt(entityNbt);

			nbt.put(TRAPPED_ENTITY_KEY, entityNbt);
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
}
