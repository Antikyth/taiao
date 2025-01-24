// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.item.TaiaoItemTags;
import antikyth.taiao.sound.TaiaoSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
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

public class KaakaapooEntity extends AnimalEntity {
    protected KaakaapooEntity(
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
                new TemptGoal(this, 1.0, Ingredient.fromTag(TaiaoItemTags.KAAKAAPOO_FOOD), false)
        );
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return TaiaoSoundEvents.ENTITY_KAAKAAPOO_BOOM;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return TaiaoSoundEvents.ENTITY_KAAKAAPOO_CHING;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return TaiaoSoundEvents.ENTITY_KAAKAAPOO_CHING;
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
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return TaiaoEntities.KAAKAAPOO.create(world);
    }

    @Override
    public boolean isBreedingItem(@NotNull ItemStack stack) {
        return stack.isIn(TaiaoItemTags.KAAKAAPOO_FOOD);
    }
}
