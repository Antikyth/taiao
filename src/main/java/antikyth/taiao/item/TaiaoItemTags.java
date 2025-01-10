package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class TaiaoItemTags {
    public static final TagKey<Item> THIN_LOGS = Taiao.createTagKey("thin_logs", RegistryKeys.ITEM);
    public static final TagKey<Item> CABBAGE_TREE_LOGS = Taiao.createTagKey("cabbage_tree_logs", RegistryKeys.ITEM);
}
