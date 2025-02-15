// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.sound.TaiaoSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EelEntity extends FishEntity {
	public EelEntity(EntityType<? extends FishEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected @Nullable SoundEvent getAmbientSound() {
		return TaiaoSoundEvents.ENTITY_EEL_AMBIENT;
	}

	@Override
	protected SoundEvent getFlopSound() {
		return TaiaoSoundEvents.ENTITY_EEL_FLOP;
	}

	@Override
	protected @Nullable SoundEvent getDeathSound() {
		return TaiaoSoundEvents.ENTITY_EEL_DEATH;
	}

	@Override
	protected @Nullable SoundEvent getHurtSound(DamageSource source) {
		return TaiaoSoundEvents.ENTITY_EEL_HURT;
	}

	@Override
	public ItemStack getBucketItem() {
		return new ItemStack(TaiaoItems.EEL_BUCKET);
	}

	public float getYawMultiplier() {
		return this.isTouchingWater() ? 1f : 1.3f;
	}

	public float getYawAngleMultiplier() {
		return this.isTouchingWater() ? 1f : 1.7f;
	}
}
