// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class TaiaoItemTags {
    public static final TagKey<Item> THIN_LOGS = Taiao.createTagKey("thin_logs", RegistryKeys.ITEM);
    public static final TagKey<Item> CABBAGE_TREE_LOGS = Taiao.createTagKey("cabbage_tree_logs", RegistryKeys.ITEM);
}
