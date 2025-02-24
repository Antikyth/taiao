// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.entity.ai.control.SleepyEntityLookControl;
import antikyth.taiao.entity.ai.control.SleepyEntityMoveControl;
import antikyth.taiao.entity.ai.goal.*;
import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.sound.TaiaoSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class KaakaapooEntity extends TameableEntity implements Shushable, Sleepy {
	protected static final float TAME_CHANCE = 1f / 3f;
	protected boolean shushed = false;

	protected static final Predicate<LivingEntity> PREDATOR_PREDICATE = TaiaoEntityPredicates.isIn(TaiaoEntityTypeTags.KAAKAAPOO_PREDATORS);

	protected static final TrackedData<Byte> KAAKAAPOO_DATA = DataTracker.registerData(
		KaakaapooEntity.class,
		TrackedDataHandlerRegistry.BYTE
	);
	protected static final int SLEEPING_FLAG = 0b0000_0001;

	protected KaakaapooEntity(
		EntityType<? extends TameableEntity> entityType,
		World world
	) {
		super(entityType, world);

		this.lookControl = new SleepyEntityLookControl(this);
		this.moveControl = new SleepyEntityMoveControl(this);
	}

	@Override
	public void setShushed(boolean shushed) {
		this.shushed = shushed;
	}

	@Override
	public boolean isShushed() {
		return shushed;
	}

	@Override
	public void setSleeping(boolean sleeping) {
		byte data = this.dataTracker.get(KAAKAAPOO_DATA);

		if (sleeping) {
			this.dataTracker.set(KAAKAAPOO_DATA, (byte) (data | SLEEPING_FLAG));
		} else {
			this.dataTracker.set(KAAKAAPOO_DATA, (byte) (data & ~SLEEPING_FLAG));
		}
	}

	@Override
	public boolean isSleeping() {
		return (this.dataTracker.get(KAAKAAPOO_DATA) & SLEEPING_FLAG) != 0;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4));
		this.goalSelector.add(2, new SitGoal(this));
		this.goalSelector.add(
			3,
			new FreezeWhenThreatenedGoal<>(this, 8d, LivingEntity.class, PREDATOR_PREDICATE)
		);
		this.goalSelector.add(
			4,
			new TemptGoal(this, 1.0, Ingredient.fromTag(TaiaoItemTags.KAAKAAPOO_FOOD), false)
		);
		this.goalSelector.add(5, new FollowOwnerGoal(this, 1.0, 10.0F, 5.0F, true));
		this.goalSelector.add(6, new AnimalMateGoal(this, 1.0));
		this.goalSelector.add(7, new AvoidDaylightGoal(this, 1d));
		this.goalSelector.add(8, new WakeAndFollowParentGoal(this, 1.1));
		this.goalSelector.add(9, new TameableSleepGoal<>(this, true, PREDATOR_PREDICATE));
		this.goalSelector.add(10, new WhileAwakeGoal(this, new WanderAroundFarGoal(this, 1.0)));
		this.goalSelector.add(11, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(12, new LookAroundGoal(this));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();

		this.dataTracker.startTracking(KAAKAAPOO_DATA, (byte) 0b0000_0000);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		nbt.putBoolean("Sleeping", this.isSleeping());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		this.setSleeping(nbt.getBoolean("Sleeping"));
	}

	@Override
	protected @Nullable SoundEvent getAmbientSound() {
		return TaiaoSoundEvents.ENTITY_KAAKAAPOO_AMBIENT;
	}

	@Override
	public void playAmbientSound() {
		if (!this.isShushed() && !this.isSleeping()) super.playAmbientSound();
	}

	@Override
	protected @Nullable SoundEvent getHurtSound(DamageSource source) {
		return TaiaoSoundEvents.ENTITY_KAAKAAPOO_HURT;
	}

	@Override
	protected @Nullable SoundEvent getDeathSound() {
		return TaiaoSoundEvents.ENTITY_KAAKAAPOO_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.ENTITY_PARROT_STEP, 0.15f, 1f);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
	}

	// Kākāpō will drop NO drops and NO xp. We will NOT encourage killing kākāpō for any purpose.
	@Override
	public boolean shouldDropXp() {
		return false;
	}

	@Override
	public @Nullable KaakaapooEntity createChild(ServerWorld world, PassiveEntity entity) {
		KaakaapooEntity child = TaiaoEntities.KAAKAAPOO.create(world);

		if (child != null && this.isTamed()) {
			child.setOwnerUuid(this.getOwnerUuid());
			child.setTamed(true);
		}

		return child;
	}

	@Override
	public boolean isBreedingItem(@NotNull ItemStack stack) {
		return stack.isIn(TaiaoItemTags.KAAKAAPOO_FOOD);
	}

	@Override
	public void tick() {
		super.tick();

		if (this.canMoveVoluntarily()) {
			// Wake up if touching water or with thunder.
			if (this.isTouchingWater() || this.getWorld().isThundering()) {
				this.wake();
			}
		}
	}

	@Override
	public void tickMovement() {
		if (this.isSleeping() || this.isImmobile()) {
			this.jumping = false;
			this.sidewaysSpeed = 0f;
			this.forwardSpeed = 0f;
		}

		super.tickMovement();
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
		Item item = stack.getItem();

		if (this.getWorld().isClient) {
			if (this.isTamed()) {
				if (this.isOwner(player)) return ActionResult.SUCCESS;

				// heal
				if (this.isBreedingItem(stack) && this.getHealth() < this.getMaxHealth()) return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		} else {
			if (this.isTamed()) {
				if (this.isOwner(player)) {
					// Healing
					FoodComponent food = item.getFoodComponent();
					if (food != null && this.isBreedingItem(stack) && this.getHealth() < this.getMaxHealth()) {
						this.eat(player, hand, stack);
						this.heal(food.getHunger());

						return ActionResult.CONSUME;
					}

					// Sitting
					ActionResult result = super.interactMob(player, hand);
					if (!result.isAccepted() || this.isBaby()) {
						this.setSitting(!this.isSitting());
					}

					return result;
				}
			} else if (this.isBreedingItem(stack)) {
				// Taming
				this.eat(player, hand, stack);

				if (this.random.nextFloat() < TAME_CHANCE) {
					this.setOwner(player);
					this.setSitting(true);

					this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
				} else {
					this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
				}

				this.setPersistent();

				return ActionResult.CONSUME;
			}

			ActionResult result = super.interactMob(player, hand);
			if (result.isAccepted()) this.setPersistent();

			return result;
		}
	}

	@Override
	public EntityView method_48926() {
		return this.getWorld();
	}
}
