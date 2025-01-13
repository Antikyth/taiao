// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import antikyth.taiao.Taiao;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class TaiaoBlockTags {
    public static final TagKey<Block> THIN_LOGS = Taiao.createTagKey("thin_logs", RegistryKeys.BLOCK);
    public static final TagKey<Block> CABBAGE_TREE_LOGS = Taiao.createTagKey("cabbage_tree_logs", RegistryKeys.BLOCK);

    public static final TagKey<Block> THIN_LOG_CONNECTION_OVERRIDE = Taiao.createTagKey(
            "thin_log_connection_override",
            RegistryKeys.BLOCK
    );
}
