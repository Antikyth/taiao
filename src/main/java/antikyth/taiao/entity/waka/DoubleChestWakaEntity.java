// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.waka;

import antikyth.taiao.entity.TaiaoEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DoubleChestWakaEntity extends ChestWakaEntity {
    public DoubleChestWakaEntity(EntityType<? extends ChestWakaEntity> entityType, World world) {
        super(entityType, world);

        this.resetInventory();
    }

    public DoubleChestWakaEntity(World world, double x, double y, double z) {
        super(TaiaoEntities.DOUBLE_CHEST_WAKA, world, x, y, z);

        this.resetInventory();
    }

    @Override
    protected int getMaxPassengers() {
        return super.getMaxPassengers() - 1;
    }

    @Override
    public int size() {
        return 9 * 6;
    }

    @Override
    public @Nullable ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        if (this.getLootTableId() != null && playerEntity.isSpectator()) return null;

        this.generateLoot(playerInventory.player);
        return GenericContainerScreenHandler.createGeneric9x6(i, playerInventory, this);
    }

    @Override
    public Item asItem() {
        WakaType type = this.getWakaType();

        if (type != null) return type.getDoubleChestItem();
        else return super.asItem();
    }
}
