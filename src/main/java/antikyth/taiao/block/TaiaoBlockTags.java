// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import antikyth.taiao.Taiao;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class TaiaoBlockTags {
    public static final TagKey<Block> THIN_LOGS = createTagKey(Taiao.id("thin_logs"));
    public static final TagKey<Block> CABBAGE_TREE_LOGS = createTagKey(Taiao.id("cabbage_tree_logs"));
    public static final TagKey<Block> KAURI_LOGS = createTagKey(Taiao.id("kauri_logs"));

    public static final TagKey<Block> THIN_LOG_CONNECTION_OVERRIDE = createTagKey(
            Taiao.id("thin_log_connection_override")
    );

    public static TagKey<Block> createTagKey(Identifier id) {
        return Taiao.createTagKey(id, RegistryKeys.BLOCK);
    }
}
