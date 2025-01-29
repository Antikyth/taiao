// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.entity.waka.ChestWakaEntity;
import antikyth.taiao.entity.waka.DoubleChestWakaEntity;
import antikyth.taiao.entity.waka.WakaEntity;
import antikyth.taiao.entity.waka.WakaType;
import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.RegistryKey;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class WakaItem extends Item {
    private static final Predicate<Entity> RIDER_PREDICATE = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::canHit);

    private final RegistryKey<WakaType> wakaTypeKey;
    private final WakaType.ChestType chestType;

    public WakaItem(RegistryKey<WakaType> wakaTypeKey, WakaType.ChestType chestType, Item.Settings settings) {
        super(settings);

        this.wakaTypeKey = wakaTypeKey;
        this.chestType = chestType;
    }

    /**
     * Modified from {@link TerraformBoatItem#use(World, PlayerEntity, Hand)}
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, @NotNull PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        HitResult hitResult = Item.raycast(world, user, RaycastContext.FluidHandling.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(stack);
        }

        Vec3d rotationVec = user.getRotationVec(1f);
        List<Entity> riders = world.getOtherEntities(
                user,
                user.getBoundingBox().stretch(rotationVec.multiply(5d)).expand(1d),
                RIDER_PREDICATE
        );

        // Prevent collision with user
        if (!riders.isEmpty()) {
            Vec3d eyePos = user.getEyePos();
            for (Entity entity : riders) {
                Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
                if (box.contains(eyePos)) {
                    return TypedActionResult.pass(stack);
                }
            }
        }

        // Spawn waka entity
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            double x = hitResult.getPos().x;
            double y = hitResult.getPos().y;
            double z = hitResult.getPos().z;

            WakaType type = WakaType.REGISTRY.getOrThrow(this.wakaTypeKey);
            BoatEntity entity;

            switch (this.chestType) {
                case SINGLE -> {
                    ChestWakaEntity waka = new ChestWakaEntity(world, x, y, z);
                    waka.setWakaType(type);

                    entity = waka;
                }
                case DOUBLE -> {
                    DoubleChestWakaEntity waka = new DoubleChestWakaEntity(world, x, y, z);
                    waka.setWakaType(type);

                    entity = waka;
                }

                default -> {
                    WakaEntity waka = new WakaEntity(world, x, y, z);
                    waka.setWakaType(type);

                    entity = waka;
                }

            }

            entity.setYaw(user.getYaw());

            if (!world.isSpaceEmpty(entity, entity.getBoundingBox().expand(-0.1d))) {
                return TypedActionResult.fail(stack);
            }

            if (!world.isClient()) {
                world.spawnEntity(entity);
                world.emitGameEvent(user, GameEvent.ENTITY_PLACE, BlockPos.ofFloored(hitResult.getPos()));

                if (!user.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
            }

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            return TypedActionResult.success(stack, world.isClient());
        }

        return TypedActionResult.pass(stack);
    }
}
