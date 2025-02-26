// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.advancement.criteria.predicate;

import antikyth.taiao.item.kete.KeteItem;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NumberRange;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class KetePredicate {
	protected final NumberRange.IntRange stackCountRange;

	/**
	 * Creates a predicate checking the number of stacks contained within a kete.
	 */
	public KetePredicate(NumberRange.IntRange stackCountRange) {
		this.stackCountRange = stackCountRange;
	}

	public boolean test(@NotNull ItemStack stack) {
		return stack.getItem() instanceof KeteItem && this.stackCountRange.test(KeteItem.getStackCount(stack));
	}

	public void writeJson(@NotNull JsonObject json) {
		json.add("stack_count", this.stackCountRange.toJson());
	}

	@Contract("_ -> new")
	public static @NotNull KetePredicate fromJson(@NotNull JsonObject json) {
		return new KetePredicate(NumberRange.IntRange.fromJson(json.get("stack_count")));
	}
}
