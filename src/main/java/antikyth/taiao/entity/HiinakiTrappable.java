// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface HiinakiTrappable {
	void setHiinakiPos(@Nullable BlockPos pos);

	@Nullable BlockPos getHiinakiPos();
}
