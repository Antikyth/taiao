// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.ai.brain.HaastsEagleBrain;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * A hokioi, also known as Haast's eagle, a large bird of prey with a wingspan of up to three
 * meters.
 * <p>
 * See {@link HaastsEagleBrain} for the brain.
 */
public class HaastsEagleEntity extends AnimalEntity {
	protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of();
	protected static final ImmutableList<? extends SensorType<? extends Sensor<? super HaastsEagleEntity>>> SENSORS = ImmutableList.of();

	protected HaastsEagleEntity(
		EntityType<? extends AnimalEntity> entityType,
		World world
	) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 14.0)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
	}

	@Override
	public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		HaastsEagleEntity child = TaiaoEntities.HAASTS_EAGLE.create(world);

		if (child != null) {
			child.setPersistent();
		}

		return child;
	}

	@Override
	public void travel(Vec3d movementInput) {
		// TODO: flying code
		super.travel(movementInput);
	}

	@Override
	protected Brain.Profile<HaastsEagleEntity> createBrainProfile() {
		return Brain.createProfile(MEMORY_MODULES, SENSORS);
	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return HaastsEagleBrain.create(this.createBrainProfile().deserialize(dynamic));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Brain<HaastsEagleEntity> getBrain() {
		// We know that the brain is for a Haast's eagle: it is safe to cast to it.
		return (Brain<HaastsEagleEntity>) super.getBrain();
	}

	@Override
	protected void sendAiDebugData() {
		super.sendAiDebugData();
		DebugInfoSender.sendBrainDebugData(this);
	}

	@Override
	protected void mobTick() {
		ServerWorld world = (ServerWorld) this.getWorld();
		Profiler profiler = world.getProfiler();

		profiler.push(Taiao.id("haasts_eagle_brain").toString());
		this.getBrain().tick(world, this);
		profiler.pop();

		profiler.push(Taiao.id("haasts_eagle_activity_update").toString());
		HaastsEagleBrain.updateActivities(this);
		profiler.pop();
	}
}
