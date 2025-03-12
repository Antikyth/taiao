// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.state;

import antikyth.taiao.item.TaiaoItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringIdentifiable;

public enum EggCondition implements StringIdentifiable {
	NONE("none"),
	INTACT("intact"),
	PARTIALLY_CRACKED("partially_cracked"),
	CRACKED("cracked");

	public static final com.mojang.serialization.Codec<EggCondition> CODEC = StringIdentifiable.createCodec(EggCondition::values);

	private final String name;

	EggCondition(String name) {
		this.name = name;
	}

	@Override
	public String asString() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public ItemStack getHaastsEagleEgg() {
		return switch (this) {
			case NONE -> ItemStack.EMPTY;
			case INTACT -> new ItemStack(TaiaoItems.HAASTS_EAGLE_EGG);
			case PARTIALLY_CRACKED -> new ItemStack(TaiaoItems.PARTIALLY_CRACKED_HAASTS_EAGLE_EGG);
			case CRACKED -> new ItemStack(TaiaoItems.CRACKED_HAASTS_EAGLE_EGG);
		};
	}
}
