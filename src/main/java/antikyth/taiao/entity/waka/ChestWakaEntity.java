// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.waka;

import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.entity.TaiaoTrackedDataHandlers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Optional;

public class ChestWakaEntity extends ChestBoatEntity implements WakaTypeHolder {
    public static final TrackedData<Optional<WakaType>> WAKA_TYPE = DataTracker.registerData(
            ChestWakaEntity.class,
            TaiaoTrackedDataHandlers.WAKA_TYPE
    );

    public ChestWakaEntity(EntityType<? extends ChestWakaEntity> entityType, World world) {
        super(entityType, world);
    }

    public ChestWakaEntity(
            EntityType<? extends ChestWakaEntity> entityType,
            World world,
            double x,
            double y,
            double z
    ) {
        this(entityType, world);

        this.setPosition(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    public ChestWakaEntity(World world, double x, double y, double z) {
        this(TaiaoEntities.SINGLE_CHEST_WAKA, world, x, y, z);
    }

    @Override
    public WakaType getWakaType() {
        return this.dataTracker.get(WAKA_TYPE).orElse(null);
    }

    @Override
    public void setWakaType(WakaType wakaType) {
        this.dataTracker.set(WAKA_TYPE, Optional.of(wakaType));
    }

    @Override
    protected int getMaxPassengers() {
        WakaType type = this.getWakaType();

        if (type != null) return type.getMaxPassengers() - 1;
        else return super.getMaxPassengers();
    }

    @Override
    protected Text getDefaultName() {
        WakaType type = this.getWakaType();

        if (type != null) return Text.translatable(type.getTranslationKey(WakaType.ChestType.NONE));
        else return super.getDefaultName();
    }

    @Override
    public Item asItem() {
        WakaType type = this.getWakaType();

        if (type != null) return type.getSingleChestItem();
        else return super.asItem();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(WAKA_TYPE, Optional.empty());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.readWakaTypeFromNbt(nbt);
        if (!this.hasValidWakaType()) this.discard();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        this.writeWakaTypeToNbt(nbt);
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return this.hasValidWakaType() && super.shouldRender(cameraX, cameraY, cameraZ);
    }

    @Override
    public void tick() {
        if (this.hasValidWakaType()) super.tick();
        else this.discard();
    }

    @Override
    public void setVariant(Type type) {
    }

    @Override
    public Type getVariant() {
        return this.getImpersonatedBoatType();
    }
}
