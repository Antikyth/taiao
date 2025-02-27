// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.block.TaiaoBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public final class TaiaoItemTags {
	public static final TagKey<Item> THIN_LOGS = createTagKey(TaiaoBlockTags.THIN_LOGS);

	public static final TagKey<Item> KAURI_LOGS = createTagKey(TaiaoBlockTags.KAURI_LOGS);
	public static final TagKey<Item> KAHIKATEA_LOGS = createTagKey(TaiaoBlockTags.KAHIKATEA_LOGS);
	public static final TagKey<Item> RIMU_LOGS = createTagKey(TaiaoBlockTags.RIMU_LOGS);
	/**
	 * Tī kōuka logs.
	 */
	public static final TagKey<Item> CABBAGE_TREE_LOGS = createTagKey(TaiaoBlockTags.CABBAGE_TREE_LOGS);
	public static final TagKey<Item> MAMAKU_LOGS = createTagKey(TaiaoBlockTags.MAMAKU_LOGS);
	/**
	 * Whekī ponga logs.
	 */
	public static final TagKey<Item> WHEKII_PONGA_LOGS = createTagKey(TaiaoBlockTags.WHEKII_PONGA_LOGS);

	/**
	 * Blocks and items with carvings.
	 */
	public static final TagKey<Item> CARVINGS = createTagKey(Taiao.id("carvings"));

	/**
	 * Ferns and fern tree leaves; used for crafting.
	 */
	public static final TagKey<Item> FERNS = createTagKey(Taiao.id("ferns"));
	/**
	 * Bait that can be used in a {@linkplain TaiaoBlocks#HIINAKI hīnaki} to attract eels.
	 */
	public static final TagKey<Item> HIINAKI_BAIT = createTagKey(Taiao.id("hiinaki_bait"));

	/**
	 * Items used to breed {@linkplain antikyth.taiao.entity.TaiaoEntities#KIWI kiwi}.
	 */
	public static final TagKey<Item> KIWI_FOOD = createTagKey(Taiao.id("kiwi_food"));
	/**
	 * Items used to breed {@linkplain antikyth.taiao.entity.TaiaoEntities#PUUKEKO pūkeko}.
	 */
	public static final TagKey<Item> PUUKEKO_FOOD = createTagKey(Taiao.id("puukeko_food"));
	/**
	 * Items used to breed {@linkplain antikyth.taiao.entity.TaiaoEntities#MOA moa}.
	 */
	public static final TagKey<Item> MOA_FOOD = createTagKey(Taiao.id("moa_food"));
	/**
	 * Items used to breed {@linkplain antikyth.taiao.entity.TaiaoEntities#KAAKAAPOO kākāpō}.
	 */
	public static final TagKey<Item> KAAKAAPOO_FOOD = createTagKey(Taiao.id("kaakaapoo_food"));
	/**
	 * Items used to breed {@linkplain antikyth.taiao.entity.TaiaoEntities#AUSTRALASIAN_BITTERN matuku-hūrepo (Australasian bitterns)}.
	 */
	public static final TagKey<Item> AUSTRALASIAN_BITTERN_FOOD = createTagKey(Taiao.id("australasian_bittern_food"));

	// Conventional tags
	public static final TagKey<Item> CONVENTIONAL_SEEDS = createTagKey(Taiao.commonId("seeds"));
	public static final TagKey<Item> CONVENTIONAL_BERRIES = createTagKey(Taiao.commonId("berries"));
	public static final TagKey<Item> CONVENTIONAL_VINES = createTagKey(Taiao.commonId("vines"));

	public static TagKey<Item> createTagKey(@NotNull TagKey<Block> blockTag) {
		return createTagKey(blockTag.id());
	}

	public static TagKey<Item> createTagKey(Identifier id) {
		return TagKey.of(RegistryKeys.ITEM, id);
	}
}
