// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.poi;

import antikyth.taiao.Taiao;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;

public final class TaiaoPoiTypeTags {
	public static final TagKey<PointOfInterestType> EEL_TRAPS = tagKey(Taiao.id("eel_traps"));

	public static TagKey<PointOfInterestType> tagKey(Identifier id) {
		return TagKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, id);
	}
}
