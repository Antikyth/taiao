// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.waka;

import antikyth.taiao.Taiao;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface WakaType {
    Registry<WakaType> REGISTRY = FabricRegistryBuilder.<WakaType>createSimple(
            RegistryKey.ofRegistry(Taiao.id("waka_type"))
    ).buildAndRegister();

    boolean isRaft();

    Item getNormalItem();

    Item getSingleChestItem();

    Item getDoubleChestItem();

    Item getMaterialItem();

    int getMaxPassengers();

    default String getTranslationKey(ChestType type) {
        return Util.createTranslationKey("waka", WakaType.REGISTRY.getId(this)) + type;
    }

    class Impl implements WakaType {
        private final boolean raft;
        private final Supplier<ItemConvertible> normalItemSupplier;
        private final Supplier<ItemConvertible> singleChestItemSupplier;
        private final Supplier<ItemConvertible> doubleChestItemSupplier;
        private final Supplier<ItemConvertible> materialItemSupplier;
        private final int maxPassengers;

        public Impl(
                boolean raft,
                Supplier<ItemConvertible> normalItemSupplier,
                Supplier<ItemConvertible> singleChestItemSupplier,
                Supplier<ItemConvertible> doubleChestItemSupplier,
                Supplier<ItemConvertible> materialItemSupplier,
                int maxPassengers
        ) {
            this.raft = raft;
            this.normalItemSupplier = normalItemSupplier;
            this.singleChestItemSupplier = singleChestItemSupplier;
            this.doubleChestItemSupplier = doubleChestItemSupplier;
            this.materialItemSupplier = materialItemSupplier;
            this.maxPassengers = maxPassengers;
        }

        @Override
        public boolean isRaft() {
            return this.raft;
        }

        @Override
        public Item getNormalItem() {
            ItemConvertible item = this.normalItemSupplier.get();

            if (item != null) return item.asItem();
            else return null;
        }

        @Override
        public Item getSingleChestItem() {
            ItemConvertible item = this.singleChestItemSupplier.get();

            if (item != null) return item.asItem();
            else return null;
        }

        @Override
        public Item getDoubleChestItem() {
            ItemConvertible item = this.doubleChestItemSupplier.get();

            if (item != null) return item.asItem();
            else return null;
        }

        @Override
        public Item getMaterialItem() {
            ItemConvertible item = this.materialItemSupplier.get();

            if (item != null) return item.asItem();
            else return null;
        }

        @Override
        public int getMaxPassengers() {
            return this.maxPassengers;
        }
    }

    enum ChestType {
        NONE,
        SINGLE,
        DOUBLE;

        @Contract(pure = true)
        @Override
        public @NotNull String toString() {
            return switch (this) {
                case NONE -> "";
                case SINGLE -> ".single_chest";
                case DOUBLE -> ".double_chest";
            };
        }
    }
}
