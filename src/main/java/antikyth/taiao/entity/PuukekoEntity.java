// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.TaiaoConfig;
import antikyth.taiao.entity.ai.goal.ParentRevengeGoal;
import antikyth.taiao.entity.ai.goal.ProtectBabiesGoal;
import antikyth.taiao.entity.ai.goal.TaiaoEntityPredicates;
import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.sound.TaiaoSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PuukekoEntity extends AnimalEntity {
	public float flapProgress;
	public float prevFlapProgress;

	public float maxWingDeviation;
	public float prevMaxWingDeviation;

	public float flapSpeed = 1.0F;
	public float flapSpeedThreshold = 1.0F;

	protected PuukekoEntity(
		EntityType<? extends AnimalEntity> entityType,
		World world
	) {
		super(entityType, world);

		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new MeleeAttackGoal(this, 1.25d, true));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25d));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
		this.goalSelector.add(
			3,
			new TemptGoal(this, 1.0, Ingredient.fromTag(TaiaoItemTags.PUUKEKO_FOOD), false)
		);
		this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(7, new LookAroundGoal(this));

		this.targetSelector.add(1, new ParentRevengeGoal(this));
		if (TaiaoConfig.AnimalBehavior.puukekoAttackPredatorsNearChicks) {
			this.targetSelector.add(
				2,
				new ProtectBabiesGoal<>(
					this,
					LivingEntity.class,
					true,
					true,
					TaiaoEntityPredicates.isIn(TaiaoEntityTypeTags.PUUKEKO_PREDATORS).and(TaiaoEntityPredicates.UNTAMED)
				)
			);
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return this.isBaby()
			? TaiaoSoundEvents.ENTITY_PUUKEKO_BABY_AMBIENT
			: TaiaoSoundEvents.ENTITY_PUUKEKO_ADULT_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return this.isBaby() ? TaiaoSoundEvents.ENTITY_PUUKEKO_BABY_HURT : TaiaoSoundEvents.ENTITY_PUUKEKO_ADULT_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return this.isBaby() ? TaiaoSoundEvents.ENTITY_PUUKEKO_BABY_DEATH : TaiaoSoundEvents.ENTITY_PUUKEKO_ADULT_DEATH;
	}

	@Override
	public int getMinAmbientSoundDelay() {
		return 160;
	}

	@Override
	public float getSoundPitch() {
		// Same pitch for babies and adults as babies have their own sounds recorded.
		return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 4d)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25d)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2d);
	}

	@Override
	public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return TaiaoEntities.PUUKEKO.create(world);
	}

	@Override
	public float getScaleFactor() {
		return this.isBaby() ? 0.65f : 1f;
	}

	@Override
	public boolean isBreedingItem(@NotNull ItemStack stack) {
		return stack.isIn(TaiaoItemTags.PUUKEKO_FOOD);
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return this.isBaby() ? dimensions.height * 0.85F : dimensions.height * 0.92F;
	}

	@Override
	protected boolean isFlappingWings() {
		return this.speed > this.flapSpeedThreshold;
	}

	@Override
	protected void addFlapEffects() {
		this.flapSpeedThreshold = this.speed + this.maxWingDeviation / 2.0F;
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		this.prevFlapProgress = this.flapProgress;
		this.prevMaxWingDeviation = this.maxWingDeviation;
		this.maxWingDeviation = this.maxWingDeviation + (this.isOnGround() ? -1.0F : 4.0F) * 0.3F;
		this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0F, 1.0F);
		if (!this.isOnGround() && this.flapSpeed < 1.0F) {
			this.flapSpeed = 1.0F;
		}

		this.flapSpeed *= 0.9F;
		Vec3d vec3d = this.getVelocity();
		if (!this.isOnGround() && vec3d.y < 0.0) {
			this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
		}

		this.flapProgress = this.flapProgress + this.flapSpeed * 2.0F;
	}
}
