// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.entity.TaiaoEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TaiaoItems {
    public static final Item PUUKEKO_SPAWN_EGG = register(
            Taiao.id("puukeko_spawn_egg"),
            new SpawnEggItem(TaiaoEntities.PUUKEKO, 0x111111, 0x073673, new FabricItemSettings())
    );

    public static final RegistryKey<ItemGroup> TAIAO_ITEM_GROUP_KEY = Taiao.createRegistryKey(
            Taiao.id("item_group"),
            RegistryKeys.ITEM_GROUP
    );
    public static final ItemGroup TAIAO_ITEM_GROUP = registerItemGroup(
            TAIAO_ITEM_GROUP_KEY,
            TaiaoBlocks.CABBAGE_TREE_SAPLING.asItem()
    );

    public static void initialize() {
        // Add items to the item group.
        ItemGroupEvents.modifyEntriesEvent(TAIAO_ITEM_GROUP_KEY).register(group -> {
            group.add(TaiaoBlocks.CABBAGE_TREE_SAPLING.asItem());
            group.add(TaiaoBlocks.CABBAGE_TREE_LEAVES.asItem());

            group.add(TaiaoBlocks.CABBAGE_TREE_LOG.asItem());
            group.add(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG.asItem());
            group.add(TaiaoBlocks.CABBAGE_TREE_WOOD.asItem());
            group.add(TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD.asItem());

            group.add(PUUKEKO_SPAWN_EGG);
        });

        // Spawn eggs
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(group -> {
            group.add(PUUKEKO_SPAWN_EGG);
        });
    }

    /**
     * Register the given {@code item} with the given {@code name} as its item ID.
     */
    public static Item register(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }

    /**
     * Register a new item group with the given {@code key} and {@code icon}.
     * <p>
     * The group's translation key is {@code itemGroup.%namespace.%name}, e.g. {@code itemGroup.taiao.item_group}.
     */
    public static ItemGroup registerItemGroup(RegistryKey<ItemGroup> key, Item icon) {
        Identifier id = key.getValue();
        String translationKey = String.format("itemGroup.%s.%s", id.getNamespace(), id.getPath());

        ItemGroup group = FabricItemGroup.builder()
                .icon(() -> new ItemStack(icon))
                .displayName(Text.translatable(translationKey))
                .build();

        return Registry.register(Registries.ITEM_GROUP, key, group);
    }
}
