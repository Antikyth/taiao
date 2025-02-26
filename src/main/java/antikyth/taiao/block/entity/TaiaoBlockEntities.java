// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.entity;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class TaiaoBlockEntities {
	public static final BlockEntityType<HiinakiBlockEntity> HIINAKI = register(
		Taiao.id("hiinaki"),
		BlockEntityType.Builder.create(
			HiinakiBlockEntity::new,
			TaiaoBlocks.HIINAKI
		)
	);
	public static final BlockEntityType<HiinakiDummyBlockEntity> HIINAKI_DUMMY = register(
		Taiao.id("hiinaki_dummy"),
		BlockEntityType.Builder.create(
			HiinakiDummyBlockEntity::new,
			TaiaoBlocks.HIINAKI
		)
	);

	public static void initialize() {
		Taiao.LOGGER.debug("Registered block entity types");
	}

	public static <T extends BlockEntity> BlockEntityType<T> register(
		Identifier id,
		BlockEntityType.@NotNull Builder<T> builder
	) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, builder.build(null));
	}
}
