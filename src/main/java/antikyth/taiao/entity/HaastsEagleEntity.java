// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.entity.ai.brain.sensor.TaiaoSensorTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyAdultSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A hokioi, also known as Haast's eagle, a large bird of prey with a wingspan of up to three
 * meters.
 */
public class HaastsEagleEntity extends AnimalEntity implements SmartBrainOwner<HaastsEagleEntity> {
	protected HaastsEagleEntity(
		EntityType<? extends AnimalEntity> entityType,
		World world
	) {
		super(entityType, world);

		this.moveControl = new FlightMoveControl(this, 10, false);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 14d)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25d)
			.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.8d);
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
	protected EntityNavigation createNavigation(World world) {
		BirdNavigation navigation = new BirdNavigation(this, world);
		navigation.setCanPathThroughDoors(false);
		navigation.setCanEnterOpenDoors(false);
		navigation.setCanSwim(true);

		return navigation;
	}

	@Override
	public List<? extends ExtendedSensor<? extends HaastsEagleEntity>> getSensors() {
		return List.of(
			new NearbyLivingEntitySensor<>(),
			new NearbyAdultSensor<>(),
			new HurtBySensor<>(),
			TaiaoSensorTypes.HAASTS_EAGLE_PREY.create(),
			TaiaoSensorTypes.HAASTS_EAGLE_TEMPTATIONS.create()
		);
	}

	@Override
	public BrainActivityGroup<? extends HaastsEagleEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(
			new LookAtTarget<>(),
			new MoveToWalkTarget<>()
		);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BrainActivityGroup<? extends HaastsEagleEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(
			new FirstApplicableBehaviour<HaastsEagleEntity>(
				new SetPlayerLookTarget<>(),
				new SetRandomLookTarget<>()
			),
			new OneRandomBehaviour<HaastsEagleEntity>(
				new SetRandomWalkTarget<>(),
				new Idle<>().runFor(eagle -> eagle.getRandom().nextBetween(30, 60))
			)
		);
	}

	@Override
	protected Brain.Profile<HaastsEagleEntity> createBrainProfile() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	protected void mobTick() {
		this.tickBrain(this);
	}
}
