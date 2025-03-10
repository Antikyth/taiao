// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HaastsEagleEggItem extends Item {
	/**
	 * The remaining ticks until hatching inside a nest.
	 */
	public static final String HATCHING_TIME_KEY = "HatchingTime";

	public HaastsEagleEggItem(Settings settings) {
		super(settings);
	}

	public void setHatchingTime(@NotNull ItemStack egg, int hatchingTime) {
		if (egg.isOf(this)) {
			egg.getOrCreateNbt().putInt(HATCHING_TIME_KEY, hatchingTime);
		}
	}

	/**
	 * If {@code egg} is of this item, returns the time until hatching, or initializes it as
	 * {@code hatchingTime} if the egg does not yet have one.
	 * <p>
	 * If {@code egg} is not of this item, {@code -1} is returned.
	 */
	public int getOrInitializeHatchingTime(@NotNull ItemStack egg, int hatchingTime) {
		if (egg.isOf(this)) {
			NbtCompound nbt = egg.getNbt();

			if (nbt != null) {
				return nbt.getInt(HATCHING_TIME_KEY);
			} else {
				egg.getOrCreateNbt().putInt(HATCHING_TIME_KEY, hatchingTime);

				return hatchingTime;
			}
		}

		return -1;
	}

	/**
	 * Returns the time until hatching.
	 * <p>
	 * If {@code egg} is not of this item or its hatching time hasn't been initialized, {@code -1}
	 * is returned.
	 */
	public int getHatchingTime(@NotNull ItemStack egg) {
		if (egg.isOf(this)) {
			NbtCompound nbt = egg.getNbt();

			if (nbt != null) return nbt.getInt(HATCHING_TIME_KEY);
		}

		return -1;
	}

	/**
	 * Decrements the time until hatching by {@code ticks}.
	 * <p>
	 * The minimum hatching time will be 0.
	 */
	public void decrementHatchingTime(@NotNull ItemStack egg, int ticks) {
		if (egg.isOf(this)) {
			this.setHatchingTime(egg, Math.min(0, this.getHatchingTime(egg) - ticks));
		}
	}

	/**
	 * Whether the {@code egg} is ready to hatch.
	 */
	public boolean isReadyToHatch(@NotNull ItemStack egg) {
		return egg.isOf(this) && getHatchingTime(egg) == 0;
	}

	@Override
	public void appendTooltip(ItemStack egg, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		int hatchingTime = getHatchingTime(egg);

		if (hatchingTime == 0) {
			tooltip.add(Text.translatable(this.getTranslationKey() + ".ready").formatted(Formatting.GRAY));
		} else if (hatchingTime > 0) {
			tooltip.add(
				Text.translatable(this.getTranslationKey() + ".time", hatchingTime / 20)
					.formatted(Formatting.GRAY)
			);
		}
	}
}
