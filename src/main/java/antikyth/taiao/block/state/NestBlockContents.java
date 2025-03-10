// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.state;

import net.minecraft.util.StringIdentifiable;

public enum NestBlockContents implements StringIdentifiable {
	NONE("none"),
	EGG("egg"),
	CHICK("chick");

	private final String name;

	NestBlockContents(String name) {
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
}
