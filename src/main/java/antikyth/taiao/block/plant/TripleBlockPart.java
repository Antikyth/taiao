// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.plant;

import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum TripleBlockPart implements StringIdentifiable {
	LOWER("lower"),
	MIDDLE("middle"),
	UPPER("upper");

	private final String name;

	TripleBlockPart(String name) {
		this.name = name;
	}

	@Contract(pure = true)
	@Override
	public @NotNull String asString() {
		return this.name;
	}

	@Contract(pure = true)
	@Override
	public @NotNull String toString() {
		return this.asString();
	}
}
