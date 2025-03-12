// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DayOrNightLootCondition implements LootCondition {
	@Nullable
	final Boolean expectsDay;

	DayOrNightLootCondition(@Nullable Boolean expectsDay) {
		this.expectsDay = expectsDay;
	}

	@Override
	public LootConditionType getType() {
		return TaiaoLootConditionTypes.DAY_OR_NIGHT;
	}

	@Override
	public boolean test(LootContext context) {
		if (this.expectsDay == null) {
			return true;
		} else {
			World world = context.getWorld();
			return this.expectsDay ? world.isDay() : world.isNight();
		}
	}

	@Contract(value = " -> new", pure = true)
	public static DayOrNightLootCondition.@NotNull Builder builder() {
		return new DayOrNightLootCondition.Builder();
	}

	public static class Builder implements LootCondition.Builder {
		private @Nullable Boolean expectsDay;

		/**
		 * Sets whether daytime is expected.
		 *
		 * @param day whether daytime is expected; if not, night is expected, if {@code null} either is expected
		 */
		public Builder expectsDay(@Nullable Boolean day) {
			this.expectsDay = day;
			return this;
		}

		/**
		 * Sets daytime as expected.
		 */
		public Builder expectsDay() {
			this.expectsDay = true;
			return this;
		}

		/**
		 * Sets nighttime as expected.
		 */
		public Builder expectsNight() {
			this.expectsDay = false;
			return this;
		}

		@Override
		public LootCondition build() {
			return new DayOrNightLootCondition(this.expectsDay);
		}
	}

	public static class Serializer implements JsonSerializer<DayOrNightLootCondition> {
		static final String DAYTIME_KEY = "daytime";

		@Override
		public void toJson(
			JsonObject json,
			@NotNull DayOrNightLootCondition condition,
			JsonSerializationContext context
		) {
			if (condition.expectsDay != null) json.addProperty(DAYTIME_KEY, condition.expectsDay);
		}

		@Override
		public DayOrNightLootCondition fromJson(@NotNull JsonObject json, JsonDeserializationContext context) {
			Boolean expectsDay = json.has(DAYTIME_KEY) ? JsonHelper.getBoolean(json, DAYTIME_KEY) : null;

			return new DayOrNightLootCondition(expectsDay);
		}
	}
}
