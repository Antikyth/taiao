// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.waka;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.item.TaiaoItems;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class TaiaoWaka {
    public static final RegistryKey<WakaType> KAURI = register(
            Taiao.id("kauri"),
            new WakaType.Impl(
                    false,
                    () -> TaiaoItems.KAURI_WAKA,
                    () -> TaiaoItems.KAURI_SINGLE_CHEST_WAKA,
                    () -> TaiaoItems.KAURI_DOUBLE_CHEST_WAKA,
                    () -> TaiaoBlocks.KAURI_LOG,
                    3
            )
    );

    public static RegistryKey<WakaType> register(Identifier id, WakaType type) {
        RegistryKey<WakaType> key = RegistryKey.of(WakaType.REGISTRY.getKey(), id);

        Registry.register(WakaType.REGISTRY, id, type);

        return key;
    }
}
