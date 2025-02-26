// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.item.kete;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.screen.slot.Slot;
import net.minecraft.stat.Stats;
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

	protected final int maxStacks;

	/**
	 * Returns a new kete item.
	 *
	 * @param maxStacks the maximum number of stacks of a particular item that can be stored in the
	 *                  kete
	 */
	public KeteItem(int maxStacks, Settings settings) {
		super(settings);

		this.maxStacks = maxStacks;
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
	 * Returns the maximum count within the kete of the current item, or {@code -1} if empty.
	 */
	public int getMaxCount(@NotNull ItemStack kete) {
		ItemStack stack = getFirstStack(kete);

		if (!stack.isEmpty()) {
			return this.maxStacks * stack.getMaxCount();
		} else {
			return -1;
		}
	}

	/**
	 * Returns {@link KeteItem#getMaxCount(ItemStack)} - {@link KeteItem#getCount(ItemStack)}.
	 */
	public int getAvailableSpace(@NotNull ItemStack kete) {
		return this.getMaxCount(kete) - getCount(kete);
	}

	@Override
	public Text getName(@NotNull ItemStack kete) {
		ItemStack stack = getFirstStack(kete);

		if (!stack.isEmpty()) {
			return Text.translatable(this.getTranslationKey() + ".filled", stack.getName());
		} else {
			return super.getName(kete);
		}
	}

	public int addToKete(@NotNull ItemStack kete, @NotNull ItemStack stack) {
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
				ItemStack firstContentStack = getFirstStack(contents);

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
					if (!stack.isEmpty() && contents.size() < this.maxStacks) {
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
	public static @Nullable ItemStack removeStack(@NotNull ItemStack kete) {
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

		if (contents != null && !contents.isEmpty()) {
			NbtCompound lastNbt = contents.getCompound(contents.size() - 1);
			ItemStack lastStack = ItemStack.fromNbt(lastNbt);

			if (lastStack.getItem() instanceof BlockItem block) {
				PlayerEntity player = context.getPlayer();

				// Create a context for the content stack to use
				BlockHitResult hit = new BlockHitResult(
					context.getHitPos(),
					context.getSide(),
					context.getBlockPos(),
					context.hitsInsideBlock()
				);
				ItemPlacementContext placementContext = new ItemPlacementContext(
					context.getWorld(),
					player,
					context.getHand(),
					lastStack,
					hit
				);

				// Use the contents on the block
				ActionResult result = block.place(placementContext);

				if (result.shouldIncrementStat() && player != null) {
					player.incrementStat(Stats.USED.getOrCreateStat(this));
				}

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

		return ActionResult.PASS;
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

					return true;
				}

			} else {
				// TODO: play add sound

				addToKete(kete, stack);

				// Returns true whether successful or not, as we don't want to fall through to other
				// behavior, considering the player probably expected this to work, and did not
				// expect any other behavior to take place.
				return true;
			}
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

				int availableSpace = this.getAvailableSpace(kete);
				ItemStack taken = slot.takeStackRange(
					stack.getCount(),
					availableSpace < 0 ? stack.getCount() : availableSpace,
					player
				);

				addToKete(kete, taken);
				// Put back any remaining items
				slot.insertStack(taken);
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
		ItemStack stack = getFirstStack(kete);

		if (!stack.isEmpty()) {
			tooltip.add(
				Text.translatable(
					this.getTranslationKey() + ".fullness",
					getCount(kete), this.getMaxCount(kete)
				).formatted(Formatting.GRAY)
			);

			if (stack.getItem() instanceof BlockItem) {
				tooltip.add(ScreenTexts.EMPTY);

				tooltip.add(
					Text.translatable(this.getTranslationKey() + ".desc.place1")
						.formatted(Formatting.GRAY)
				);
				tooltip.add(
					ScreenTexts.space()
						.append(Text.translatable(this.getTranslationKey() + ".desc.place2", stack.getName()))
						.formatted(Formatting.BLUE)
				);
			}
		}
	}

	@Override
	public Optional<TooltipData> getTooltipData(@NotNull ItemStack kete) {
		ItemStack stack = getFirstStack(kete);

		if (!stack.isEmpty()) {
			// We don't want to give the impression that this can be used for getting the count
			// within the kete, as there are more than one stack.
			stack.setCount(1);

			return Optional.of(new KeteTooltipData(stack));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public boolean isItemBarVisible(@NotNull ItemStack kete) {
		return !isEmpty(kete);
	}

	@Override
	public int getItemBarStep(@NotNull ItemStack kete) {
		int barWidth = 12;
		return Math.min(barWidth * getCount(kete) / this.getMaxCount(kete), barWidth) + 1;
	}

	@Override
	public int getItemBarColor(ItemStack kete) {
		return ITEM_BAR_COLOR;
	}

	/**
	 * Gets the first stack within the kete, or {@link ItemStack#EMPTY} if there is none.
	 */
	protected static ItemStack getFirstStack(@NotNull ItemStack kete) {
		return getFirstStack(getContents(kete));
	}

	/**
	 * Gets the first stack within the kete, or {@link ItemStack#EMPTY} if there is none.
	 */
	protected static ItemStack getFirstStack(@Nullable NbtList contents) {
		if (contents != null && !contents.isEmpty()) {
			return ItemStack.fromNbt(contents.getCompound(0));
		} else {
			return ItemStack.EMPTY;
		}
	}

	protected static Stream<ItemStack> getContentStacks(@NotNull ItemStack kete) {
		NbtList contents = getContents(kete);

		return contents == null ? Stream.of() : contents.stream().map(NbtCompound.class::cast).map(ItemStack::fromNbt);
	}

	protected static @Nullable NbtList getContents(@NotNull ItemStack kete) {
		return getContents(kete.getNbt());
	}

	protected static @Nullable NbtList getContents(@Nullable NbtCompound keteNbt) {
		if (keteNbt != null && keteNbt.contains(CONTENTS_KEY, NbtElement.LIST_TYPE)) {
			return keteNbt.getList(CONTENTS_KEY, NbtElement.COMPOUND_TYPE);
		} else {
			return null;
		}
	}
}
