// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item.tooltip;

import antikyth.taiao.Taiao;
import antikyth.taiao.item.kete.KeteTooltipData;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class TaiaoTooltipComponents {
	public static void initialize() {
		Taiao.LOGGER.debug("Registering tooltip components");

		register(KeteTooltipData.class, KeteTooltipComponent::new);
	}

	/**
	 * Registers a {@link TooltipComponentCallback} that applies {@code componentFactory} for
	 * {@link TooltipData} matching the {@code dataClass}.
	 * <p>
	 * Avoid registering multiple callbacks for a single {@link TooltipData} class: if there are
	 * multiple component factories registered for a single data class, only the first registered
	 * callback will be used, and all others will be ignored.
	 *
	 * @param <D>              the {@link TooltipData} used for this component
	 * @param dataClass        the tooltip data's class
	 * @param componentFactory a function creating the {@link TooltipComponent}
	 */
	public static <D extends TooltipData> void register(
		@NotNull Class<D> dataClass,
		@NotNull Function<@NotNull D, ? extends TooltipComponent> componentFactory
	) {
		TooltipComponentCallback.EVENT.register(
			data -> dataClass.isInstance(data) ? componentFactory.apply(dataClass.cast(data)) : null
		);
	}
}
