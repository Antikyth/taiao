// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.sound.TaiaoSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;

public class WeetaaEntity extends PathAwareEntity {
	/**
	 * Flags for the wētā.
	 */
	protected static final TrackedData<Byte> FLAGS = DataTracker.registerData(
		WeetaaEntity.class,
		TrackedDataHandlerRegistry.BYTE
	);
	protected static final int CLIMBING_FLAG = 0b0000_0001;

	protected WeetaaEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.ARTHROPOD;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
		this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(4, new LookAroundGoal(this));
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(TaiaoSoundEvents.ENTITY_WEETAA_STEP, 0.15f, 1f);
	}

	public static boolean isValidSpawn(
		EntityType<WeetaaEntity> entityType,
		@NotNull WorldAccess world,
		SpawnReason reason,
		@NotNull BlockPos pos,
		Random random
	) {
		BlockState below = world.getBlockState(pos.down());

		return below.isIn(TaiaoBlockTags.WEETAA_SPAWNABLE_ON);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();

		this.dataTracker.startTracking(FLAGS, (byte) 0);
	}

	@Override
	public boolean isClimbing() {
		return (this.dataTracker.get(FLAGS) & CLIMBING_FLAG) != 0;
	}

	public void setClimbing(boolean climbing) {
		byte flags = this.dataTracker.get(FLAGS);

		if (climbing) flags = (byte) (flags | CLIMBING_FLAG);
		else flags = (byte) (flags & ~CLIMBING_FLAG);

		this.dataTracker.set(FLAGS, flags);
	}

	@Override
	public void tick() {
		super.tick();

		if (!this.getWorld().isClient) this.setClimbing(this.horizontalCollision);
	}
}
