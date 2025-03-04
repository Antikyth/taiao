// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.entity.ai.goal.EnterHiinakiGoal;
import antikyth.taiao.entity.ai.goal.InvestigateHiinakiGoal;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.sound.TaiaoSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EelEntity extends FishEntity implements HiinakiTrappable {
	@Nullable
	protected BlockPos hiinakiPos;

	public EelEntity(EntityType<? extends FishEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initGoals() {
		super.initGoals();

		this.goalSelector.add(1, new EnterHiinakiGoal<>(this, 200, 0.4f));
		this.goalSelector.add(3, new InvestigateHiinakiGoal<>(this, 1000, 0.75f, 32));
	}

	@Override
	public void setHiinakiPos(@Nullable BlockPos hiinakiPos) {
		this.hiinakiPos = hiinakiPos;
	}

	@Override
	public @Nullable BlockPos getHiinakiPos() {
		return hiinakiPos;
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
