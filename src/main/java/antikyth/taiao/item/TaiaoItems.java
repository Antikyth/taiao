// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import antikyth.taiao.entity.TaiaoEntities;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TaiaoItems {
    public static final Item CONIFER_FRUIT = register(
            Taiao.id("conifer_fruit"),
            new Item(new FabricItemSettings().food(TaiaoFoodComponents.CONIFER_FRUIT))
    );

    public static final Item KAURI_BOAT = TerraformBoatItemHelper.registerBoatItem(
            Taiao.id("kauri_boat"),
            TaiaoBoats.KAURI,
            false
    );
    public static final Item KAURI_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(
            Taiao.id("kauri_chest_boat"),
            TaiaoBoats.KAURI,
            true
    );
    public static final Item KAHIKATEA_BOAT = TerraformBoatItemHelper.registerBoatItem(
            Taiao.id("kahikatea_boat"),
            TaiaoBoats.KAHIKATEA,
            false
    );
    public static final Item KAHIKATEA_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(
            Taiao.id("kahikatea_chest_boat"),
            TaiaoBoats.KAHIKATEA,
            true
    );
    public static final Item MAMAKU_RAFT = TerraformBoatItemHelper.registerBoatItem(
            Taiao.id("mamaku_raft"),
            TaiaoBoats.MAMAKU,
            false
    );
    public static final Item MAMAKU_CHEST_RAFT = TerraformBoatItemHelper.registerBoatItem(
            Taiao.id("mamaku_chest_raft"),
            TaiaoBoats.MAMAKU,
            true
    );

    public static final Item KIWI_SPAWN_EGG = register(
            Taiao.id("kiwi_spawn_egg"),
            new SpawnEggItem(TaiaoEntities.KIWI, 0x482d19, 0xf5bb98, new FabricItemSettings())
    );
    public static final Item PUUKEKO_SPAWN_EGG = register(
            Taiao.id("puukeko_spawn_egg"),
            new SpawnEggItem(TaiaoEntities.PUUKEKO, 0x073673, 0xaf2e2e, new FabricItemSettings())
    );
    public static final Item MOA_SPAWN_EGG = register(
            Taiao.id("moa_spawn_egg"),
            new SpawnEggItem(TaiaoEntities.MOA, 0x2c180c, 0x55361c, new FabricItemSettings())
    );
    public static final Item KAAKAAPOO_SPAWN_EGG = register(
            Taiao.id("kaakaapoo_spawn_egg"),
            new SpawnEggItem(TaiaoEntities.KAAKAAPOO, 0x7a9539, 0xd4ae68, new FabricItemSettings())
    );

    public static void initialize() {
        Taiao.LOGGER.debug("Registering items");
    }

    /**
     * Register the given {@code item} with the given {@code id}.
     */
    public static Item register(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }

    public static class TaiaoFoodComponents {
        public static final FoodComponent CONIFER_FRUIT = new FoodComponent.Builder()
                .hunger(1)
                .saturationModifier(0.1f)
                .build();
    }

}
