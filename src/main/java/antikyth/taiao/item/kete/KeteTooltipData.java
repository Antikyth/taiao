// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item.kete;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;

public record KeteTooltipData(ItemStack contents) implements TooltipData {
	public int getCount() {
		return this.contents.getCount();
	}

	public int getMaxCount() {
		return KeteItem.maxStackSize(this.contents);
	}

	public boolean isEmpty() {
		return this.contents.isEmpty();
	}
}
