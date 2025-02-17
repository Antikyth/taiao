// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity;

import antikyth.taiao.block.HiinakiBlock;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class HiinakiBlockEntity extends BlockEntity {
	protected static final String BAIT_KEY = "Bait";
	protected static final String TRAPPED_ENTITY_KEY = "TrappedEntity";

	protected ItemStack bait = ItemStack.EMPTY;

	/**
	 * The NBT of the currently trapped entity, if there is one.
	 */
	@Nullable
	protected NbtCompound trappedEntityNbt;
	/**
	 * An entity representing the currently trapped entity (used purely for rendering purposes).
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
		return this.trappedEntityNbt != null;
	}

	public float getYaw() {
		return this.getCachedState().get(HiinakiBlock.FACING).asRotation();
	}

	/**
	 * {@return an entity representing the currently trapped entity (only to be used for rendering purposes)}
	 */
	public @Nullable Entity getRenderedEntity() {
		if (this.trappedEntityNbt != null && this.renderedEntity == null) {
			this.renderedEntity = EntityType.loadEntityWithPassengers(
				this.trappedEntityNbt,
				this.world,
				Function.identity()
			);
		} else {
			this.renderedEntity = null;
		}

		return this.renderedEntity;
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
			// Eat the bait
			this.bait.decrement(1);

			entity.stopRiding();
			entity.removeAllPassengers();

			NbtCompound entityNbt = new NbtCompound();
			entity.saveNbt(entityNbt);
			this.trappedEntityNbt = entityNbt;

			// TODO: play sound?
			this.blockChanged(entity);

			entity.discard();
			return true;
		}

		return false;
	}

	/**
	 * Releases the trapped entity.
	 *
	 * @param force whether to release the entity even if the entrance is blocked
	 * @return if the entity was released, the released entity
	 */
	public @Nullable Entity releaseEntity(boolean force, boolean spawn) {
		if (this.trappedEntityNbt != null) {
			BlockPos pos = this.getPos();
			Direction facing = this.getCachedState().get(HiinakiBlock.FACING);
			BlockPos releasePos = pos.offset(facing);
			boolean hasCollision = world != null
				&& !world.getBlockState(releasePos).getCollisionShape(this.world, releasePos).isEmpty();

			// Don't release entity into a solid block unless forced.
			if (hasCollision && !force) return null;

			Entity entity = EntityType.loadEntityWithPassengers(this.trappedEntityNbt, this.world, Function.identity());
			if (entity != null) {
				this.trappedEntityNbt = null;

				float width = entity.getWidth();
				double forwardOffset = hasCollision ? 0d : 0.55d + (double) (width / 2f);

				double x = (double) pos.getX() + 0.5d + (forwardOffset * (double) facing.getOffsetX());
				double y = pos.getY();
				double z = (double) pos.getZ() + 0.5d + (forwardOffset * (double) facing.getOffsetZ());

				entity.refreshPositionAndAngles(x, y, z, this.getYaw(), 0f);

				// TODO: play exit sound?
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
	 * {@linkplain Entity#kill() Kills} the currently trapped entity.
	 *
	 * @param force whether to kill the entity even if the entrance is blocked
	 * @return whether the trapped entity was killed (if there was an entity trapped)
	 */
	public boolean killTrappedEntity(boolean force) {
		Entity entity = this.releaseEntity(force, false);

		if (entity != null) {
			entity.kill();
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
			this.trappedEntityNbt = nbt.getCompound(TRAPPED_ENTITY_KEY);
		} else {
			this.trappedEntityNbt = null;
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
		if (this.trappedEntityNbt != null) {
			nbt.put(TRAPPED_ENTITY_KEY, this.trappedEntityNbt);
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
}
