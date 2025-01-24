// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.sound.TaiaoSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
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
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class KiwiEntity extends AnimalEntity {
    private static final TrackedData<Byte> COLOR = DataTracker.registerData(
            KiwiEntity.class,
            TrackedDataHandlerRegistry.BYTE
    );
    private static final float WHITE_CHANCE = 0.01f;

    protected KiwiEntity(
            EntityType<? extends AnimalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(
                3,
                new TemptGoal(this, 1.0, Ingredient.fromTag(TaiaoItemTags.KIWI_FOOD), false)
        );
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return TaiaoSoundEvents.ENTITY_KIWI_CHIRP;
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
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    // Kiwi will drop NO drops and NO xp. We will NOT encourage killing kiwi for any purpose.
    @Override
    public boolean shouldDropXp() {
        return false;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(COLOR, (byte) KiwiColor.BROWN.getId());
    }

    public void setColor(@NotNull KiwiColor color) {
        byte data = this.dataTracker.get(COLOR);

        this.dataTracker.set(COLOR, (byte) ((data & 0b1111_0000) | (color.getId() & 0b0000_1111)));
    }

    public KiwiColor getColor() {
        return KiwiColor.byId(this.dataTracker.get(COLOR) & 0b0000_1111);
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

    public enum KiwiColor {
        BROWN(0),
        WHITE(1);

        private final int id;
        private static final IntFunction<KiwiColor> BY_ID = ValueLists.createIdToValueFunction(
                KiwiColor::getId,
                KiwiColor.values(),
                ValueLists.OutOfBoundsHandling.ZERO
        );

        KiwiColor(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static KiwiColor byId(int id) {
            return BY_ID.apply(id);
        }
    }
}
