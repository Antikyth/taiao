package antikyth.taiao.block;

import antikyth.taiao.Taiao;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class TaiaoBlockTags {
    public static final TagKey<Block> THIN_LOGS = Taiao.createTagKey("thin_logs", RegistryKeys.BLOCK);
    public static final TagKey<Block> CABBAGE_TREE_LOGS = Taiao.createTagKey("cabbage_tree_logs", RegistryKeys.BLOCK);
}
