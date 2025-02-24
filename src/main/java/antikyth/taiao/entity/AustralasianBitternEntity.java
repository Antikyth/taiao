// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.entity.ai.goal.FreezeWhenThreatenedGoal;
import antikyth.taiao.entity.ai.goal.TaiaoEntityPredicates;
import antikyth.taiao.item.TaiaoItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class AustralasianBitternEntity extends AnimalEntity implements Shushable {
	protected boolean shushed = false;

	protected static final Predicate<LivingEntity> PREDATOR_PREDICATE = TaiaoEntityPredicates.isIn(TaiaoEntityTypeTags.AUSTRALASIAN_BITTERN_PREDATORS);
	protected static final Predicate<LivingEntity> PREY_PREDICATE = TaiaoEntityPredicates.isIn(TaiaoEntityTypeTags.AUSTRALASIAN_BITTERN_PREY);

	protected AustralasianBitternEntity(
		EntityType<? extends AnimalEntity> entityType,
		World world
	) {
		super(entityType, world);
	}

	@Override
	public void setShushed(boolean shushed) {
		this.shushed = shushed;
	}

	@Override
	public boolean isShushed() {
		return this.shushed;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25d));

		// Freeze when predators are close
		this.goalSelector.add(
			2,
			new FreezeWhenThreatenedGoal<>(this, 8d, LivingEntity.class, PREDATOR_PREDICATE)
		);
		// Flee when predators are a bit further away
		this.goalSelector.add(
			3,
			new FleeEntityGoal<>(this, LivingEntity.class, 24f, 1.25d, 1.25d, PREDATOR_PREDICATE)
		);

		this.goalSelector.add(4, new PounceAtTargetGoal(this, 0.4f));
		this.goalSelector.add(5, new MeleeAttackGoal(this, 1d, true));

		this.goalSelector.add(6, new AnimalMateGoal(this, 1.0));
		this.goalSelector.add(
			7,
			new TemptGoal(this, 1.0, Ingredient.fromTag(TaiaoItemTags.AUSTRALASIAN_BITTERN_FOOD), false)
		);
		this.goalSelector.add(8, new FollowParentGoal(this, 1.1));
		this.goalSelector.add(9, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(11, new LookAroundGoal(this));

		this.targetSelector.add(
			1,
			new ActiveTargetGoal<>(this, LivingEntity.class, 10, false, false, PREY_PREDICATE)
		);
	}

	@Override
	public double getSwimHeight() {
		return (double) this.getStandingEyeHeight() < 0.4d ? 0d : 0.25d;
	}

	@Override
	protected @Nullable SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	public void playAmbientSound() {
		if (!this.isShushed()) super.playAmbientSound();
	}

	@Override
	protected @Nullable SoundEvent getHurtSound(DamageSource source) {
		return null;
	}

	@Override
	protected @Nullable SoundEvent getDeathSound() {
		return null;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15f, 1f);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2d);
	}

	@Override
	public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity otherParent) {
		return TaiaoEntities.AUSTRALASIAN_BITTERN.create(world);
	}

	@Override
	public boolean isBreedingItem(@NotNull ItemStack stack) {
		return stack.isIn(TaiaoItemTags.AUSTRALASIAN_BITTERN_FOOD);
	}
}
