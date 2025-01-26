// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.item.TaiaoItemGroups;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.sound.TaiaoSoundEvents;
import antikyth.taiao.world.gen.biome.TaiaoBiomes;
import antikyth.taiao.world.gen.biome.TaiaoMaterialRules;
import antikyth.taiao.world.gen.feature.tree.placer.TaiaoTreePlacers;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Taiao implements ModInitializer {
    public static final String MOD_NAME = "Te Taiao o Aotearoa";
    public static final String MOD_ID = "taiao";

    boolean initialized = false;

    // This logger is used to write text to the console and the log file.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        if (initialized) return;
        else initialized = true;

        LOGGER.info("Initializing {}", MOD_NAME);

        TaiaoSoundEvents.initialize();

        TaiaoBlocks.initialize();
        TaiaoItems.initialize();
        TaiaoItemGroups.initialize();
        TaiaoEntities.initialize();

        TaiaoTreePlacers.initialize();

        TaiaoBiomes.setBiomePlacements();
        SurfaceGeneration.addOverworldSurfaceRules(Taiao.id("rules/overworld"), TaiaoMaterialRules.ALL);
    }

    /**
     * Creates an {@link Identifier} using the {@linkplain Taiao#MOD_ID Te Taiao o Aotearoa namespace}.
     */
    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }

    /**
     * Creates an {@link Identifier} using the common ({@code c}) namespace.
     */
    public static Identifier commonId(String name) {
        return Identifier.of("c", name);
    }
}