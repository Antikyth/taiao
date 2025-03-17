// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.state;

import antikyth.taiao.item.TaiaoItems;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum HaastsEagleNestContents implements StringIdentifiable {
	/**
	 * No egg or chick.
	 */
	EMPTY("empty"),
	/**
	 * A fully intact egg.
	 */
	INTACT_EGG("intact_egg", TaiaoItems.HAASTS_EAGLE_EGG),
	/**
	 * A partially cracked egg.
	 */
	PARTIALLY_CRACKED_EGG("partially_cracked_egg", TaiaoItems.PARTIALLY_CRACKED_HAASTS_EAGLE_EGG),
	/**
	 * A cracked egg, ready to hatch.
	 */
	CRACKED_EGG("cracked_egg", TaiaoItems.CRACKED_HAASTS_EAGLE_EGG),
	/**
	 * A chick.
	 */
	CHICK("chick");

	public static final com.mojang.serialization.Codec<HaastsEagleNestContents> CODEC = StringIdentifiable.createCodec(
		HaastsEagleNestContents::values
	);

	/**
	 * A map of egg items to the associated contents.
	 */
	private static final Map<ItemConvertible, HaastsEagleNestContents> EGG_TO_STAGE = Arrays.stream(values())
		.filter(HaastsEagleNestContents::hasEgg)
		.collect(Collectors.toMap(HaastsEagleNestContents::getEggItem, Function.identity()));
	/**
	 * A list of egg items paired with the following contents' egg item.
	 * <p>
	 * Only mappings between an {@linkplain HaastsEagleNestContents#hasEgg() egg} and another egg
	 * feature in this list. That means there is no pair representing the
	 * {@linkplain HaastsEagleNestContents#isReadyToHatch() final stage}'s egg hatching into a
	 * {@linkplain HaastsEagleNestContents#CHICK chick}.
	 */
	public static final List<Pair<ItemConvertible, ItemConvertible>> INCUBATIONS = Arrays.stream(values())
		.filter(stage -> stage.hasEgg() && stage.nextStage().hasEgg())
		.map(stage -> new Pair<>(stage.egg, stage.nextStage().egg))
		.toList();

	private final String name;
	private final @Nullable ItemConvertible egg;

	HaastsEagleNestContents(String name) {
		this(name, null);
	}

	HaastsEagleNestContents(String name, @Nullable ItemConvertible egg) {
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
	 * Whether the contents have {@linkplain HaastsEagleNestContents#hasEgg() no egg} and
	 * {@linkplain HaastsEagleNestContents#hasChick() no chick}.
	 * <p>
	 * That is, whether the contents are {@link HaastsEagleNestContents#EMPTY}.
	 *
	 * @see HaastsEagleNestContents#hasEgg()
	 */
	public boolean isEmpty() {
		return this == EMPTY;
	}

	/**
	 * Whether there is an {@linkplain HaastsEagleNestContents#getEggItem() egg item} associated with
	 * this stage.
	 *
	 * @see HaastsEagleNestContents#isEmpty()
	 */
	public boolean hasEgg() {
		return this.egg != null;
	}

	/**
	 * Whether this is {@link HaastsEagleNestContents#CHICK}.
	 */
	public boolean hasChick() {
		return this == CHICK;
	}

	/**
	 * Whether this is the final stage of incubation, with the next stage being hatching.
	 */
	public boolean isReadyToHatch() {
		return this.hasEgg() && this.nextStage().hasChick();
	}

	/**
	 * Returns the Haast's eagle egg item associated with this stage (may be {@code null}).
	 */
	public ItemConvertible getEggItem() {
		return this.egg;
	}

	/**
	 * Creates an {@link ItemStack} of the
	 * {@linkplain HaastsEagleNestContents#getEggItem() Haast's eagle egg item associated with this stage}.
	 * <p>
	 * If {@linkplain HaastsEagleNestContents#hasEgg() there is no egg}, {@link ItemStack#EMPTY} is returned.
	 */
	public ItemStack createEggStack() {
		return this.egg == null ? ItemStack.EMPTY : new ItemStack(this.egg);
	}

	/**
	 * Returns the appropriate stage for the given {@code egg} item, or {@code null} if there is no
	 * stage for that item.
	 */
	public static HaastsEagleNestContents getStageForItem(@NotNull ItemConvertible egg) {
		return EGG_TO_STAGE.get(egg);
	}

	/**
	 * Returns the appropriate stage for the given {@code egg} stack.
	 * <p>
	 * If {@code egg} is empty, {@link HaastsEagleNestContents#EMPTY} is returned. If there is otherwise
	 * no stage representing the stack's item, {@code null} is returned.
	 */
	public static HaastsEagleNestContents getStageForStack(@NotNull ItemStack egg) {
		return egg.isEmpty() ? HaastsEagleNestContents.EMPTY : getStageForItem(egg.getItem());
	}

	/**
	 * Whether there is a stage associated with the given {@code item}.
	 *
	 * @see HaastsEagleNestContents#isValidEgg(ItemVariant)
	 * @see HaastsEagleNestContents#getStageForItem(ItemConvertible)
	 */
	@SuppressWarnings("UnstableApiUsage")
	public static boolean isValidEgg(ItemConvertible item) {
		return item != null && EGG_TO_STAGE.containsKey(item);
	}

	/**
	 * Whether there is a stage associated with the {@code variant}'s item.
	 *
	 * @see HaastsEagleNestContents#isValidEgg(ItemConvertible)
	 * @see HaastsEagleNestContents#getStageForStack(ItemStack)
	 */
	@SuppressWarnings("UnstableApiUsage")
	public static boolean isValidEgg(@NotNull ItemVariant variant) {
		return !variant.isBlank() && isValidEgg(variant.getItem());
	}

	/**
	 * {@return the next stage after incubation}
	 * <p>
	 * {@link HaastsEagleNestContents#EMPTY} and {@link HaastsEagleNestContents#CHICK} return
	 * {@link HaastsEagleNestContents#EMPTY}, other contents return the next stage of contents.
	 */
	public HaastsEagleNestContents nextStage() {
		return switch (this) {
			case INTACT_EGG -> PARTIALLY_CRACKED_EGG;
			case PARTIALLY_CRACKED_EGG -> CRACKED_EGG;
			case CRACKED_EGG -> CHICK;
			case CHICK, EMPTY -> EMPTY;
		};
	}
}
