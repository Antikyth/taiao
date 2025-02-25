// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item.tooltip;

import antikyth.taiao.item.kete.KeteTooltipData;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;

public class KeteTooltipComponent implements TooltipComponent {
	protected static final int SLOT_SIZE = 16;

	protected final KeteTooltipData data;

	public KeteTooltipComponent(KeteTooltipData data) {
		this.data = data;
	}

	@Override
	public int getWidth(TextRenderer textRenderer) {
		return this.data.isEmpty() ? 0 : SLOT_SIZE;
	}

	@Override
	public int getHeight() {
		return this.data.isEmpty() ? 0 : SLOT_SIZE + 4;
	}

	@Override
	public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
		ItemStack contents = this.data.contents();

		if (!contents.isEmpty()) {
			context.drawItem(contents, x, y, 0);
			context.drawItemInSlot(textRenderer, contents, x, y);
		}
	}
}
