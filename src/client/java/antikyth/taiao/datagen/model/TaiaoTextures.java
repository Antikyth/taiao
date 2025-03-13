// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.model;

import net.minecraft.block.Block;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;

public class TaiaoTextures {
	public static class Keys {
		public static final TextureKey OVERLAY = TextureKey.of("overlay");

		public static final TextureKey LEFT_SIDE = TextureKey.of("left_side", TextureKey.SIDE);
		public static final TextureKey RIGHT_SIDE = TextureKey.of("right_side", TextureKey.SIDE);

		public static final TextureKey EGG = TextureKey.of("egg");
	}

	public static class Maps {
		public static TextureMap largeBirdNest(Block block) {
			return new TextureMap()
				.put(Keys.LEFT_SIDE, TextureMap.getSubId(block, "_left_side"))
				.put(Keys.RIGHT_SIDE, TextureMap.getSubId(block, "_right_side"))
				.put(TextureKey.BOTTOM, TextureMap.getSubId(block, "_bottom"));
		}

		public static TextureMap largeBirdNestEgg(Block block) {
			return largeBirdNestEgg(block, "");
		}

		public static TextureMap largeBirdNestEgg(Block block, String eggSuffix) {
			return largeBirdNest(block)
				.put(Keys.EGG, TextureMap.getSubId(block, "_egg" + eggSuffix));
		}
	}
}
