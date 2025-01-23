// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.entity.TaiaoEntities;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.world.gen.biome.TaiaoRegions;
import antikyth.taiao.world.gen.feature.tree.placer.TaiaoTreePlacers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import terrablender.api.TerraBlenderApi;

public class Taiao implements ModInitializer, TerraBlenderApi {
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

        TaiaoBlocks.initialize();
        TaiaoItems.initialize();
        TaiaoEntities.initialize();

        TaiaoTreePlacers.initialize();
    }

    @Override
    public void onTerraBlenderInitialized() {
        onInitialize();

        TaiaoRegions.initialize();
//        SurfaceRuleManager.addSurfaceRules(
//                SurfaceRuleManager.RuleCategory.OVERWORLD,
//                MOD_ID,
//                TaiaoSurfaceRules.createSurfaceRules()
//        );
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