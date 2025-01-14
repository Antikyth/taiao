// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class TaiaoItemTags {
    public static final TagKey<Item> THIN_LOGS = createTagKey(Taiao.id("thin_logs"));
    public static final TagKey<Item> CABBAGE_TREE_LOGS = createTagKey(Taiao.id("cabbage_tree_logs"));
    public static final TagKey<Item> KAURI_LOGS = createTagKey(Taiao.id("kauri_logs"));

    /**
     * Items used to breed {@linkplain antikyth.taiao.entity.TaiaoEntities#PUUKEKO pūkeko}.
     */
    public static final TagKey<Item> PUUKEKO_TEMPT_ITEMS = createTagKey(Taiao.id("puukeko_tempt_items"));
    /**
     * Items used to breed {@linkplain antikyth.taiao.entity.TaiaoEntities#MOA moa}.
     */
    public static final TagKey<Item> MOA_TEMPT_ITEMS = createTagKey(Taiao.id("moa_tempt_items"));

    public static TagKey<Item> createTagKey(Identifier id) {
        return Taiao.createTagKey(id, RegistryKeys.ITEM);
    }
}
