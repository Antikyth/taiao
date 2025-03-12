// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.state;

import antikyth.taiao.item.TaiaoItems;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum HaastsEagleEggStage implements StringIdentifiable {
	/**
	 * No egg.
	 */
	NONE("none", null),
	/**
	 * A fully intact egg.
	 */
	INTACT("intact", TaiaoItems.HAASTS_EAGLE_EGG),
	/**
	 * A partially cracked egg.
	 */
	PARTIALLY_CRACKED("partially_cracked", TaiaoItems.PARTIALLY_CRACKED_HAASTS_EAGLE_EGG),
	/**
	 * A cracked egg, ready to hatch.
	 */
	CRACKED("cracked", TaiaoItems.CRACKED_HAASTS_EAGLE_EGG);

	public static final com.mojang.serialization.Codec<HaastsEagleEggStage> CODEC = StringIdentifiable.createCodec(
		HaastsEagleEggStage::values
	);

	/**
	 * A map of egg items to the associated stage.
	 */
	public static final Map<ItemConvertible, HaastsEagleEggStage> EGG_TO_STAGE = Arrays.stream(values())
		.filter(stage -> stage.egg != null)
		.collect(Collectors.toMap(stage -> stage.egg, Function.identity()));
	/**
	 * A map of egg stages' eggs to the following egg stage's egg.
	 * <p>
	 * If either the stage itself or the following stage (in the case of the final stage) is
	 * {@link HaastsEagleEggStage#NONE}, it does not appear in this map.
	 */
	public static final Map<ItemConvertible, ItemConvertible> INCUBATIONS = Arrays.stream(values())
		.filter(stage -> stage.egg != null)
		.filter(stage -> stage.nextStage().egg != null)
		.collect(Collectors.toMap(stage -> stage.egg, stage -> stage.nextStage().egg));

	private final String name;
	private final @Nullable ItemConvertible egg;

	HaastsEagleEggStage(String name, @Nullable ItemConvertible egg) {
		this.name = name;
		this.egg = egg;
	}

	@Override
	public String asString() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Returns whether this stage is {@link HaastsEagleEggStage#NONE}.
	 *
	 * @see HaastsEagleEggStage#hasEgg()
	 */
	public boolean isEmpty() {
		return this == NONE;
	}

	/**
	 * Returns whether this stage is not {@link HaastsEagleEggStage#NONE}.
	 *
	 * @see HaastsEagleEggStage#isEmpty()
	 */
	public boolean hasEgg() {
		return !this.isEmpty();
	}

	/**
	 * {@return the Haast's eagle egg item associated with this stage}
	 * <p>
	 * If {@linkplain HaastsEagleEggStage#hasEgg() there is no egg}, {@link ItemStack#EMPTY} is returned.
	 */
	public ItemStack getEgg() {
		return this.egg == null ? ItemStack.EMPTY : new ItemStack(this.egg);
	}

	/**
	 * {@return the next stage after incubation}
	 * <p>
	 * If this is the final stage or {@linkplain HaastsEagleEggStage#hasEgg() there is no egg},
	 * {@link HaastsEagleEggStage#NONE} is returned.
	 */
	public HaastsEagleEggStage nextStage() {
		return switch (this) {
			case INTACT -> PARTIALLY_CRACKED;
			case PARTIALLY_CRACKED -> CRACKED;
			case NONE, CRACKED -> NONE;
		};
	}

	/**
	 * Whether this is the final stage of incubation, with the next stage being hatching.
	 */
	public boolean isReadyToHatch() {
		return this == CRACKED;
	}
}
