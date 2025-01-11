package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TaiaoItems {
    public static final RegistryKey<ItemGroup> TAIAO_ITEM_GROUP_KEY = Taiao.createRegistryKey(
            "item_group",
            Registries.ITEM_GROUP
    );
    public static final ItemGroup TAIAO_ITEM_GROUP = registerItemGroup(
            TAIAO_ITEM_GROUP_KEY,
            TaiaoBlocks.CABBAGE_TREE_LOG.asItem()
    );

    public static void initialize() {
        // Add items to the item group.
        ItemGroupEvents.modifyEntriesEvent(TAIAO_ITEM_GROUP_KEY).register(group -> {
            group.add(TaiaoBlocks.CABBAGE_TREE_LEAVES.asItem());

            group.add(TaiaoBlocks.CABBAGE_TREE_LOG.asItem());
            group.add(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG.asItem());
            group.add(TaiaoBlocks.CABBAGE_TREE_WOOD.asItem());
            group.add(TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD.asItem());
        });
    }

    /**
     * Register the given {@code item} with the given {@code name} as its item ID.
     */
    public static Item register(String name, Item item) {
        Identifier id = Identifier.of(Taiao.MOD_ID, name);

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
