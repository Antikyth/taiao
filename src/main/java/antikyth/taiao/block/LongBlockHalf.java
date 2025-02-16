// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

public enum LongBlockHalf implements StringIdentifiable {
	FRONT("front"),
	BACK("back");

	private final String name;

	LongBlockHalf(String name) {
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

	public Direction getDirectionTowardsOtherHalf(Direction facing) {
		return this == LongBlockHalf.BACK ? facing : facing.getOpposite();
	}
}
