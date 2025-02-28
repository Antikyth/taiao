// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.plant.TallReedsBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class TaiaoBlockTags {
	public static final TagKey<Block> WEETAA_SPAWNABLE_ON = createTagKey(Taiao.id("weetaa_spawnable_on"));

	public static final TagKey<Block> THIN_LOGS = createTagKey(Taiao.id("thin_logs"));
	public static final TagKey<Block> DIRECTIONAL_LEAVES = createTagKey(Taiao.id("directional_leaves"));

	public static final TagKey<Block> KAURI_LOGS = createTagKey(Taiao.id("kauri_logs"));
	public static final TagKey<Block> KAHIKATEA_LOGS = createTagKey(Taiao.id("kahikatea_logs"));
	public static final TagKey<Block> RIMU_LOGS = createTagKey(Taiao.id("rimu_logs"));
	/**
	 * Tī kōuka logs.
	 */
	public static final TagKey<Block> CABBAGE_TREE_LOGS = createTagKey(Taiao.id("cabbage_tree_logs"));
	public static final TagKey<Block> MAMAKU_LOGS = createTagKey(Taiao.id("mamaku_logs"));
	/**
	 * Whekī ponga logs.
	 */
	public static final TagKey<Block> WHEKII_PONGA_LOGS = createTagKey(Taiao.id("whekii_ponga_logs"));

	public static final TagKey<Block> EEL_TRAPS = createTagKey(Taiao.id("eel_traps"));

	/**
	 * Blocks that {@linkplain antikyth.taiao.block.log.ThinLogBlock thin logs} should be able to connect to when they otherwise would not.
	 */
	public static final TagKey<Block> THIN_LOG_CONNECTION_OVERRIDE = createTagKey(
		Taiao.id("thin_log_connection_override")
	);
	/**
	 * Blocks that {@link TallReedsBlock}s can be planted on.
	 */
	public static final TagKey<Block> REEDS_PLANTABLE_ON = createTagKey(Taiao.id("reeds_plantable_on"));
	/**
	 * Blocks that allow {@link TallReedsBlock}s to be planted on blocks adjacent, in addition to
	 * {@linkplain net.minecraft.registry.tag.FluidTags#WATER water}.
	 */
	public static final TagKey<Block> HYDRATES_REEDS = createTagKey(Taiao.id("hydrates_reeds"));

	public static final TagKey<Block> HARAKEKE_PLANTABLE_ON = createTagKey(Taiao.id("harakeke_plantable_on"));
	public static final TagKey<Block> HYDRATES_HARAKEKE = createTagKey(Taiao.id("hydrates_harakeke"));

	public static TagKey<Block> createTagKey(Identifier id) {
		return TagKey.of(RegistryKeys.BLOCK, id);
	}
}
