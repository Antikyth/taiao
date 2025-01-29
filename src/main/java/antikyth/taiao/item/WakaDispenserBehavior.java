// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.entity.waka.ChestWakaEntity;
import antikyth.taiao.entity.waka.DoubleChestWakaEntity;
import antikyth.taiao.entity.waka.WakaEntity;
import antikyth.taiao.entity.waka.WakaType;
import com.terraformersmc.terraform.boat.impl.item.TerraformBoatDispenserBehavior;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class WakaDispenserBehavior extends ItemDispenserBehavior {
    protected static final DispenserBehavior FALLBACK_BEHAVIOR = new ItemDispenserBehavior();
    protected static final float OFFSET_MULTIPLIER = 1.125f;

    private final RegistryKey<WakaType> wakaTypeKey;
    private final WakaType.ChestType chestType;

    public WakaDispenserBehavior(RegistryKey<WakaType> wakaTypeKey, WakaType.ChestType chestType) {
        this.wakaTypeKey = wakaTypeKey;
        this.chestType = chestType;
    }

    /**
     * Behavior modified from {@link TerraformBoatDispenserBehavior}.
     */
    @Override
    protected ItemStack dispenseSilently(@NotNull BlockPointer pointer, ItemStack stack) {
        Direction facing = pointer.getBlockState().get(DispenserBlock.FACING);

        double x = pointer.getX() + (facing.getOffsetX() * OFFSET_MULTIPLIER);
        double y = pointer.getY() + (facing.getOffsetY() * OFFSET_MULTIPLIER);
        double z = pointer.getZ() + (facing.getOffsetZ() * OFFSET_MULTIPLIER);

        World world = pointer.getWorld();
        BlockPos pos = pointer.getPos().offset(facing);

        if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
            y += 1;
        } else if (!world.getBlockState(pos).isAir() || !world.getFluidState(pos.down()).isIn(FluidTags.WATER)) {
            return FALLBACK_BEHAVIOR.dispense(pointer, stack);
        }

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

        entity.setYaw(facing.asRotation());

        world.spawnEntity(entity);
        stack.decrement(1);

        return stack;
    }
}
