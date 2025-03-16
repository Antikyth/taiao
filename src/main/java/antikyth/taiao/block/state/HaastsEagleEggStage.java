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

public enum HaastsEagleEggStage implements StringIdentifiable {
	/**
	 * No egg.
	 */
	NONE("none"),
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
	private static final Map<ItemConvertible, HaastsEagleEggStage> EGG_TO_STAGE = Arrays.stream(values())
		.filter(HaastsEagleEggStage::hasEgg)
		.collect(Collectors.toMap(HaastsEagleEggStage::getEggItem, Function.identity()));
	/**
	 * A list of egg items paired with the following stage's egg item.
	 * <p>
	 * Only mappings between a {@linkplain HaastsEagleEggStage#hasEgg() non-empty egg} and another
	 * non-empty egg feature in this list.
	 * That means there is no pair representing the
	 * {@linkplain HaastsEagleEggStage#isReadyToHatch() final stage}'s egg hatching.
	 */
	public static final List<Pair<ItemConvertible, ItemConvertible>> INCUBATIONS = Arrays.stream(values())
		.filter(stage -> stage.hasEgg() && stage.nextStage().hasEgg())
		.map(stage -> new Pair<>(stage.egg, stage.nextStage().egg))
		.toList();

	private final String name;
	private final @Nullable ItemConvertible egg;

	HaastsEagleEggStage(String name) {
		this(name, null);
	}

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
	 * Whether there is no {@linkplain HaastsEagleEggStage#getEggItem() egg item} associated with
	 * this stage.
	 *
	 * @see HaastsEagleEggStage#hasEgg()
	 */
	public boolean isEmpty() {
		return this.egg == null;
	}

	/**
	 * Whether there is an {@linkplain HaastsEagleEggStage#getEggItem() egg item} associated with
	 * this stage.
	 *
	 * @see HaastsEagleEggStage#isEmpty()
	 */
	public boolean hasEgg() {
		return this.egg != null;
	}

	/**
	 * Whether this is the final stage of incubation, with the next stage being hatching.
	 */
	public boolean isReadyToHatch() {
		return this.hasEgg() && this.nextStage().isEmpty();
	}

	/**
	 * Returns the Haast's eagle egg item associated with this stage (may be {@code null}).
	 */
	public ItemConvertible getEggItem() {
		return this.egg;
	}

	/**
	 * Creates an {@link ItemStack} of the
	 * {@linkplain HaastsEagleEggStage#getEggItem() Haast's eagle egg item associated with this stage}.
	 * <p>
	 * If {@linkplain HaastsEagleEggStage#isEmpty() there is no egg}, {@link ItemStack#EMPTY} is returned.
	 */
	public ItemStack createEggStack() {
		return this.egg == null ? ItemStack.EMPTY : new ItemStack(this.egg);
	}

	/**
	 * Returns the appropriate stage for the given {@code egg} item, or {@code null} if there is no
	 * stage for that item.
	 */
	public static HaastsEagleEggStage getStageForItem(@NotNull ItemConvertible egg) {
		return EGG_TO_STAGE.get(egg);
	}

	/**
	 * Returns the appropriate stage for the given {@code egg} stack.
	 * <p>
	 * If {@code egg} is empty, {@link HaastsEagleEggStage#NONE} is returned. If there is otherwise
	 * no stage representing the stack's item, {@code null} is returned.
	 */
	public static HaastsEagleEggStage getStageForStack(@NotNull ItemStack egg) {
		return egg.isEmpty() ? HaastsEagleEggStage.NONE : getStageForItem(egg.getItem());
	}

	/**
	 * Whether there is a stage associated with the given {@code item}.
	 *
	 * @see HaastsEagleEggStage#isValidEgg(ItemVariant)
	 * @see HaastsEagleEggStage#getStageForItem(ItemConvertible)
	 */
	@SuppressWarnings("UnstableApiUsage")
	public static boolean isValidEgg(ItemConvertible item) {
		return item != null && EGG_TO_STAGE.containsKey(item);
	}

	/**
	 * Whether there is a stage associated with the {@code variant}'s item.
	 *
	 * @see HaastsEagleEggStage#isValidEgg(ItemConvertible)
	 * @see HaastsEagleEggStage#getStageForStack(ItemStack)
	 */
	@SuppressWarnings("UnstableApiUsage")
	public static boolean isValidEgg(@NotNull ItemVariant variant) {
		return !variant.isBlank() && isValidEgg(variant.getItem());
	}

	/**
	 * {@return the next stage after incubation}
	 * <p>
	 * If this is the {@linkplain HaastsEagleEggStage#isReadyToHatch() final stage} or
	 * {@linkplain HaastsEagleEggStage#isEmpty() there is no egg}, {@link HaastsEagleEggStage#NONE}
	 * is returned.
	 */
	public HaastsEagleEggStage nextStage() {
		return switch (this) {
			case INTACT -> PARTIALLY_CRACKED;
			case PARTIALLY_CRACKED -> CRACKED;
			case CRACKED, NONE -> NONE;
		};
	}
}
