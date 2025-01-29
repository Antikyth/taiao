// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.waka.WakaType;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

import java.util.Optional;

public class TaiaoTrackedDataHandlers {
    public static final TrackedDataHandler<Optional<WakaType>> WAKA_TYPE = register(TrackedDataHandler.ofOptional(
            (buf, wakaType) -> buf.writeRegistryValue(WakaType.REGISTRY, wakaType),
            (buf) -> buf.readRegistryValue(WakaType.REGISTRY)
    ));

    public static void initialize() {
        Taiao.LOGGER.debug("Registering tracked data handlers");
    }

    public static <T> TrackedDataHandler<T> register(TrackedDataHandler<T> handler) {
        TrackedDataHandlerRegistry.register(handler);

        return handler;
    }
}
