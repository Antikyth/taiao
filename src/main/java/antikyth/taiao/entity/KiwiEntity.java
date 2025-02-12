// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.entity.goal.AvoidDaylightGoal;
import antikyth.taiao.entity.goal.SleepGoal;
import antikyth.taiao.entity.goal.TaiaoEntityPredicates;
import antikyth.taiao.entity.goal.WakeAndFollowParentGoal;
import antikyth.taiao.entity.goal.control.SleepyEntityLookControl;
import antikyth.taiao.entity.goal.control.SleepyEntityMoveControl;
import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.sound.TaiaoSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class KiwiEntity extends AnimalEntity implements SleepyEntity {
	protected static final float WHITE_CHANCE = 0.01f;

	protected static final TrackedData<Byte> KIWI_DATA = DataTracker.registerData(
		KiwiEntity.class,
		TrackedDataHandlerRegistry.BYTE
	);
	/**
	 * Bitmask for the data referring to the {@link KiwiColor}.
	 */
	protected static final int COLOR_MASK = 0b0000_1111;
	/**
	 * Bitmask for the flag referring to whether the kiwi {@linkplain KiwiEntity#isSleeping() is sleeping}.
	 */
	protected static final int SLEEPING_FLAG = 0b0001_0000;

	protected KiwiEntity(
		EntityType<? extends AnimalEntity> entityType,
		World world
	) {
		super(entityType, world);

		this.lookControl = new SleepyEntityLookControl(this);
		this.moveControl = new SleepyEntityMoveControl(this);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));

		this.goalSelector.add(1, new MeleeAttackGoal(this, 1.25d, true));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25d));
		this.goalSelector.add(
			2,
			new FleeEntityGoal<>(
				this,
				LivingEntity.class,
				8f,
				1.2d,
				1.2d,
				TaiaoEntityPredicates.isIn(TaiaoEntityTypeTags.KIWI_PREDATORS)
			)
		);

		this.goalSelector.add(3, new AnimalMateGoal(this, 1d));
		this.goalSelector.add(
			4,
			new TemptGoal(this, 1.0, Ingredient.fromTag(TaiaoItemTags.KIWI_FOOD), false)
		);
		this.goalSelector.add(5, new AvoidDaylightGoal(this, 1.25d));
		this.goalSelector.add(6, new WakeAndFollowParentGoal(this, 1.1d));
		this.goalSelector.add(
			7,
			new SleepGoal<>(this, true, TaiaoEntityPredicates.isIn(TaiaoEntityTypeTags.KIWI_PREDATORS))
		);
		this.goalSelector.add(8, new WanderAroundFarGoal(this, 1d));
		this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 6f));
		this.goalSelector.add(10, new LookAroundGoal(this));

		this.targetSelector.add(1, new RevengeGoal(this));
	}

	@Override
	protected @Nullable SoundEvent getAmbientSound() {
		return TaiaoSoundEvents.ENTITY_KIWI_CHIRP;
	}

	@Override
	public void playAmbientSound() {
		if (!this.isSleeping()) super.playAmbientSound();
	}

	@Override
	protected @Nullable SoundEvent getHurtSound(DamageSource source) {
		return TaiaoSoundEvents.ENTITY_KIWI_CHIRP;
	}

	@Override
	protected @Nullable SoundEvent getDeathSound() {
		return TaiaoSoundEvents.ENTITY_KIWI_CHIRP;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15f, 1f);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 3d)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25d)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2d);
	}

	// Kiwi will drop NO drops and NO xp. We will NOT encourage killing kiwi for any purpose.
	@Override
	public boolean shouldDropXp() {
		return false;
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();

		this.dataTracker.startTracking(KIWI_DATA, (byte) 0b0000_0000);
	}

	public void setColor(@NotNull KiwiColor color) {
		byte data = this.dataTracker.get(KIWI_DATA);

		this.dataTracker.set(KIWI_DATA, (byte) ((data & ~COLOR_MASK) | (color.getId() & COLOR_MASK)));
	}

	public KiwiColor getColor() {
		return KiwiColor.byId(this.dataTracker.get(KIWI_DATA) & COLOR_MASK);
	}

	@Override
	public void setSleeping(boolean sleeping) {
		byte data = this.dataTracker.get(KIWI_DATA);

		if (sleeping) {
			this.dataTracker.set(KIWI_DATA, (byte) (data | SLEEPING_FLAG));
		} else {
			this.dataTracker.set(KIWI_DATA, (byte) (data & ~SLEEPING_FLAG));
		}
	}

	@Override
	public boolean isSleeping() {
		return (this.dataTracker.get(KIWI_DATA) & SLEEPING_FLAG) != 0;
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		nbt.putBoolean("Sleeping", this.isSleeping());
		nbt.putString("Color", this.getColor().asString());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		this.setSleeping(nbt.getBoolean("Sleeping"));
		this.setColor(KiwiColor.byName(nbt.getString("Color")));
	}

	@Override
	public EntityData initialize(
		@NotNull ServerWorldAccess world,
		LocalDifficulty difficulty,
		SpawnReason spawnReason,
		@Nullable EntityData entityData,
		@Nullable NbtCompound entityNbt
	) {
		this.setColor(world.getRandom().nextFloat() < WHITE_CHANCE ? KiwiColor.WHITE : KiwiColor.BROWN);

		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	@Override
	public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity otherParent) {
		KiwiEntity child = TaiaoEntities.KIWI.create(world);

		if (child != null) {
			// Recessive gene - if both parents have it, the child has it
			boolean passWhiteGene = this.getColor() == KiwiColor.WHITE && ((KiwiEntity) otherParent).getColor() == KiwiColor.WHITE;
			// Mutation chance
			passWhiteGene = passWhiteGene || world.getRandom().nextFloat() < WHITE_CHANCE;

			child.setColor(passWhiteGene ? KiwiColor.WHITE : KiwiColor.BROWN);
		}

		return child;
	}

	@Override
	public boolean isBreedingItem(@NotNull ItemStack stack) {
		return stack.isIn(TaiaoItemTags.KIWI_FOOD);
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

	public enum KiwiColor implements StringIdentifiable {
		BROWN(0, "brown"),
		WHITE(1, "white");

		private final int id;
		private final String name;

		@SuppressWarnings("deprecation")
		public static final Codec<KiwiColor> CODEC = StringIdentifiable.createCodec(KiwiColor::values);
		private static final IntFunction<KiwiColor> BY_ID = ValueLists.createIdToValueFunction(
			KiwiColor::getId,
			KiwiColor.values(),
			ValueLists.OutOfBoundsHandling.ZERO
		);

		KiwiColor(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return this.id;
		}

		public static KiwiColor byId(int id) {
			return BY_ID.apply(id);
		}

		public static KiwiColor byName(String name) {
			return CODEC.byId(name, KiwiColor.BROWN);
		}

		@Override
		public String asString() {
			return this.name;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}
}
