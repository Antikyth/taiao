// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.waka;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public interface WakaTypeHolder {
    String WAKA_TYPE_KEY = "waka_type";

    WakaType getWakaType();

    void setWakaType(WakaType wakaType);

    default boolean hasValidWakaType() {
        return this.getWakaType() != null;
    }

    default void readWakaTypeFromNbt(NbtCompound nbt) {
        Identifier id = Identifier.tryParse(nbt.getString(WAKA_TYPE_KEY));

        if (id != null) {
            WakaType wakaType = WakaType.REGISTRY.get(id);

            if (wakaType != null) this.setWakaType(wakaType);
        }
    }

    default void writeWakaTypeToNbt(NbtCompound nbt) {
        Identifier id = WakaType.REGISTRY.getId(this.getWakaType());

        if (id != null) nbt.putString(WAKA_TYPE_KEY, id.toString());
    }

    default BoatEntity.Type getImpersonatedBoatType() {
        return this.getWakaType().isRaft() ? BoatEntity.Type.BAMBOO : BoatEntity.Type.OAK;
    }
}
