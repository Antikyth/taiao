// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.world.gen.feature.foliage.TaiaoFoliagePlacers;
import antikyth.taiao.world.gen.feature.trunk.TaiaoTrunkPlacers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Taiao implements ModInitializer {
    public static final String MOD_NAME = "Te Taiao o Aotearoa";
    public static final String MOD_ID = "taiao";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        TaiaoBlocks.initialize();
        TaiaoItems.initialize();
        TaiaoEntities.initialize();

        TaiaoTrunkPlacers.initialize();
        TaiaoFoliagePlacers.initialize();
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }

    public static <T> RegistryKey<T> createRegistryKey(Identifier id, RegistryKey<? extends Registry<T>> registryKey) {
        return RegistryKey.of(registryKey, id);
    }

    public static <T> TagKey<T> createTagKey(Identifier id, RegistryKey<? extends Registry<T>> registryKey) {
        return TagKey.of(registryKey, id);
    }
}