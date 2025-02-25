// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item.kete;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class KeteItem extends Item {
	protected static final String CONTENTS_KEY = "Contents";
	protected static final int ITEM_BAR_COLOR = MathHelper.packRgb(0.4F, 0.4F, 1.0F);
	protected static final int MAX_STACKS = 8;

	public KeteItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean canBeNested() {
		return false;
	}

	/**
	 * Whether there are any stacks contained within the kete.
	 */
	public static boolean isEmpty(@NotNull ItemStack kete) {
		NbtList contents = getContents(kete);

		return contents == null || contents.isEmpty();
	}

	/**
	 * Returns the total count of all stacks (all the same item) within the kete.
	 */
	public static int getCount(@NotNull ItemStack kete) {
		return getContentStacks(kete).mapToInt(ItemStack::getCount).sum();
	}

	/**
	 * Returns the maximum count within the kete of the current item.
	 * <p>
	 * Note that if the kete is empty, {@code 0} is returned, so be careful to check
	 * {@code !}{@link KeteItem#isEmpty(ItemStack) KeteItem.isEmpty}{@code (kete)} before dividing by the result.
	 */
	public static int getMaxCount(@NotNull ItemStack kete) {
		NbtList contents = getContents(kete);

		if (contents == null || contents.isEmpty()) {
			return 0;
		} else {
			ItemStack firstContentStack = ItemStack.fromNbt(contents.getCompound(0));

			return MAX_STACKS * firstContentStack.getMaxCount();
		}
	}

	/**
	 * Returns {@link KeteItem#getMaxCount(ItemStack)} - {@link KeteItem#getCount(ItemStack)}.
	 */
	public static int getAvailableSpace(@NotNull ItemStack kete) {
		return getMaxCount(kete) - getCount(kete);
	}

	@Override
	public Text getName(@NotNull ItemStack kete) {
		NbtList contents = getContents(kete);

		if (contents != null && !contents.isEmpty()) {
			ItemStack stack = ItemStack.fromNbt(contents.getCompound(0));

			return Text.translatable(this.getTranslationKey() + ".filled", stack.getName());
		} else {
			return super.getName(kete);
		}
	}

	protected static int addToKete(@NotNull ItemStack kete, @NotNull ItemStack stack) {
		if (!stack.isEmpty() && stack.getItem().canBeNested()) {
			NbtList contents = getContents(kete);

			if (contents == null || contents.isEmpty()) {
				// No items already in the kete

				if (contents == null) {
					contents = new NbtList();
					kete.getOrCreateNbt().put(CONTENTS_KEY, contents);
				}

				NbtCompound insertNbt = new NbtCompound();
				ItemStack insertStack = stack.copyAndEmpty();
				insertStack.writeNbt(insertNbt);

				contents.add(insertNbt);

				return insertStack.getCount();
			} else {
				ItemStack firstContentStack = ItemStack.fromNbt(contents.getCompound(0));

				if (ItemStack.canCombine(firstContentStack, stack)) {
					// Stack is of the same item as the existing ones

					int originalCount = stack.getCount();

					for (int i = 0; i < contents.size(); i++) {
						if (stack.isEmpty()) break;

						NbtCompound contentNbt = contents.getCompound(i);
						ItemStack contentStack = ItemStack.fromNbt(contentNbt);

						int availableSpace = contentStack.getMaxCount() - contentStack.getCount();
						int insertCount = Math.min(availableSpace, stack.getCount());

						contentStack.increment(insertCount);
						stack.decrement(insertCount);

						contentStack.writeNbt(contentNbt);
					}

					// If there are still items left to insert, then we add a new stack if possible.
					if (!stack.isEmpty() && contents.size() < MAX_STACKS) {
						NbtCompound contentNbt = new NbtCompound();
						ItemStack insertStack = stack.copyAndEmpty();
						insertStack.writeNbt(contentNbt);

						contents.add(contentNbt);
					}

					return originalCount - stack.getCount();
				}
			}
		}

		return 0;
	}

	/**
	 * Removes one regular-sized stack from the kete.
	 */
	protected static @Nullable ItemStack removeStack(@NotNull ItemStack kete) {
		NbtList contents = getContents(kete);

		if (contents == null || contents.isEmpty()) {
			return null;
		} else {
			NbtCompound lastNbt = (NbtCompound) contents.remove(contents.size() - 1);

			// Remove contents key if now empty
			if (contents.isEmpty()) {
				kete.getOrCreateNbt().remove(CONTENTS_KEY);
			}

			return ItemStack.fromNbt(lastNbt);
		}
	}

	@Override
	public ActionResult useOnBlock(@NotNull ItemUsageContext context) {
		ItemStack kete = context.getStack();
		NbtList contents = getContents(kete);

		if (contents == null || contents.isEmpty()) {
			return ActionResult.PASS;
		} else {
			NbtCompound lastNbt = contents.getCompound(contents.size() - 1);
			ItemStack lastStack = ItemStack.fromNbt(lastNbt);

			// Create a context for the content stack to use
			BlockHitResult hit = new BlockHitResult(
				context.getHitPos(),
				context.getSide(),
				context.getBlockPos(),
				context.hitsInsideBlock()
			);
			ItemUsageContext contentContext = new ItemUsageContext(
				context.getWorld(),
				context.getPlayer(),
				context.getHand(),
				lastStack,
				hit
			);

			// Use the contents on the block
			ActionResult result = lastStack.useOnBlock(contentContext);

			if (lastStack.isEmpty()) {
				// If empty, remove from kete
				contents.remove(contents.size() - 1);

				if (contents.isEmpty()) {
					kete.getOrCreateNbt().remove(CONTENTS_KEY);
				}
			} else {
				// Write any changes to the contents, e.g. count decrement
				lastStack.writeNbt(lastNbt);
			}

			return result;
		}
	}

	// On clicked with another stack
	@Override
	public boolean onClicked(
		ItemStack kete,
		ItemStack stack,
		Slot slot,
		ClickType clickType,
		PlayerEntity player,
		StackReference cursorStackReference
	) {
		if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
			if (stack.isEmpty()) {
				ItemStack removedStack = removeStack(kete);

				if (removedStack != null) {
					// TODO: play remove sound
					cursorStackReference.set(removedStack);
				}

			} else {
				int count = addToKete(kete, stack);

				if (count > 0) {
					// TODO: play add sound
					stack.decrement(count);
				}
			}

			return true;
		}

		return false;
	}

	// On clicked into a slot
	@Override
	public boolean onStackClicked(ItemStack kete, Slot slot, ClickType clickType, PlayerEntity player) {
		if (clickType == ClickType.RIGHT) {
			ItemStack stack = slot.getStack();

			if (stack.isEmpty()) {
				ItemStack removedStack = removeStack(kete);

				if (removedStack != null) {
					// TODO: play remove sound

					// Put any items that didn't fit in the slot back in the kete
					addToKete(kete, slot.insertStack(removedStack));
				}
			} else {
				// TODO: play insert sound if > 0
				addToKete(kete, slot.takeStackRange(stack.getCount(), getAvailableSpace(kete), player));
			}

			return true;
		}

		return false;
	}

	@Override
	public void onItemEntityDestroyed(@NotNull ItemEntity entity) {
		ItemStack kete = entity.getStack();

		// Spawn the contents of the kete
		ItemUsage.spawnItemContents(entity, getContentStacks(kete));
	}

	@Override
	public void appendTooltip(
		@NotNull ItemStack kete,
		@Nullable World world,
		@NotNull List<Text> tooltip,
		TooltipContext context
	) {
		if (!isEmpty(kete)) {
			tooltip.add(
				Text.translatable(
					this.getTranslationKey() + ".fullness",
					getCount(kete), getMaxCount(kete)
				).formatted(Formatting.GRAY)
			);
		}
	}

	@Override
	public Optional<TooltipData> getTooltipData(@NotNull ItemStack kete) {
		NbtList contents = getContents(kete);

		if (contents == null || contents.isEmpty()) {
			return Optional.empty();
		} else {
			ItemStack firstContentStack = ItemStack.fromNbt(contents.getCompound(0));
			// We don't want to give the impression that this can be used for getting the count
			// within the kete, as there are more than one stack.
			firstContentStack.setCount(1);

			return Optional.of(new KeteTooltipData(firstContentStack));
		}
	}

	@Override
	public boolean isItemBarVisible(@NotNull ItemStack kete) {
		return !isEmpty(kete);
	}

	@Override
	public int getItemBarStep(@NotNull ItemStack kete) {
		int barWidth = 12;
		return Math.min(barWidth * getCount(kete) / getMaxCount(kete), barWidth) + 1;
	}

	@Override
	public int getItemBarColor(ItemStack kete) {
		return ITEM_BAR_COLOR;
	}

	protected static Stream<ItemStack> getContentStacks(@NotNull ItemStack kete) {
		NbtList contents = getContents(kete);

		return contents == null ? Stream.of() : contents.stream().map(NbtCompound.class::cast).map(ItemStack::fromNbt);
	}

	protected static @Nullable NbtList getContents(@NotNull ItemStack kete) {
		return getContents(kete.getOrCreateNbt());
	}

	protected static @Nullable NbtList getContents(@NotNull NbtCompound keteNbt) {
		if (keteNbt.contains(CONTENTS_KEY, NbtElement.LIST_TYPE)) {
			return keteNbt.getList(CONTENTS_KEY, NbtElement.COMPOUND_TYPE);
		} else {
			return null;
		}
	}
}
