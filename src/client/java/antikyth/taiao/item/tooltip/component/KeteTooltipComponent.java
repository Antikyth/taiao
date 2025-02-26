// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item.tooltip.component;

import antikyth.taiao.item.tooltip.data.KeteTooltipData;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KeteTooltipComponent implements TooltipComponent {
	protected static final int SLOT_SIZE = 16;

	protected final ItemStack contents;

	public KeteTooltipComponent(@NotNull KeteTooltipData data) {
		this(data.contents());
	}

	public KeteTooltipComponent(ItemStack contents) {
		this.contents = contents;
	}

	@Override
	public int getWidth(TextRenderer textRenderer) {
		return SLOT_SIZE;
	}

	@Override
	public int getHeight() {
		return SLOT_SIZE + 4;
	}

	@Override
	public void drawItems(TextRenderer textRenderer, int x, int y, @NotNull DrawContext context) {
		context.drawItem(this.contents, x, y, 0);
		// Draw overlays like durability, but without the count
		context.drawItemInSlot(textRenderer, this.contents, x, y, "");
	}
}
