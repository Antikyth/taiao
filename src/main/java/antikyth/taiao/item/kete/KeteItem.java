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
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class KeteItem extends Item {
	protected static final String CONTENTS_KEY = "Contents";
	protected static final int ITEM_BAR_COLOR = MathHelper.packRgb(0.4F, 0.4F, 1.0F);

	public KeteItem(Settings settings) {
		super(settings);
	}

	@Contract(pure = true)
	public static int maxStackSize(@NotNull ItemStack contents) {
		return contents.getMaxCount() * 8;
	}

	@Override
	public Text getName(@NotNull ItemStack kete) {
		ItemStack contents = ItemStack.fromNbt(getOrCreateContents(kete.getOrCreateNbt()));

		if (!contents.isEmpty()) {
			return Text.translatable(this.getTranslationKey() + ".filled", contents.getName());
		} else {
			return Text.translatable(this.getTranslationKey());
		}
	}

	protected static int addToKete(@NotNull ItemStack kete, @NotNull ItemStack stack) {
		NbtCompound nbt = kete.getOrCreateNbt();
		NbtCompound contentsNbt = getOrCreateContents(nbt);
		ItemStack contents = ItemStack.fromNbt(contentsNbt);

		if (!stack.isEmpty() && stack.getItem().canBeNested()) {
			int maxCount = maxStackSize(stack);
			int contentsCount = contents.getCount();
			int insertCount = Math.max(Math.min(stack.getCount(), maxCount - contentsCount), 0);

			if (contents.isEmpty()) {
				// No items in the kete

				// Insert the stack
				ItemStack insertStack = stack.copyWithCount(insertCount);
				insertStack.writeNbt(contentsNbt);

				nbt.put(CONTENTS_KEY, contentsNbt);
				return insertCount;
			} else if (ItemStack.canCombine(stack, contents)) {
				// Can combine with the items already in the kete

				// Increment the stack
				contents.increment(insertCount);
				contents.writeNbt(contentsNbt);

				nbt.put(CONTENTS_KEY, contentsNbt);
				return insertCount;
			}
		}

		return 0;
	}

	/**
	 * Removes one regular-sized stack from the kete.
	 */
	protected static @Nullable ItemStack removeStack(@NotNull ItemStack kete) {
		NbtCompound nbt = kete.getOrCreateNbt();
		NbtCompound contentsNbt = getOrCreateContents(nbt);
		ItemStack contents = ItemStack.fromNbt(contentsNbt);

		if (contents.isEmpty()) return null;

		// Remove stack from kete
		int removalCount = Math.min(contents.getCount(), contents.getMaxCount());
		ItemStack stack = contents.copyWithCount(removalCount);
		contents.decrement(removalCount);

		contents.writeNbt(contentsNbt);
		nbt.put(CONTENTS_KEY, contentsNbt);

		return stack;
	}

	@Override
	public ActionResult useOnBlock(@NotNull ItemUsageContext context) {
		ItemStack kete = context.getStack();

		NbtCompound nbt = kete.getOrCreateNbt();
		NbtCompound contentsNbt = getOrCreateContents(nbt);
		ItemStack contents = ItemStack.fromNbt(contentsNbt);

		// Create a context for the contents stack to use
		BlockHitResult hit = new BlockHitResult(
			context.getHitPos(),
			context.getSide(),
			context.getBlockPos(),
			context.hitsInsideBlock()
		);
		ItemUsageContext contentsContext = new ItemUsageContext(
			context.getWorld(),
			context.getPlayer(),
			context.getHand(),
			contents,
			hit
		);

		// Use the contents on the block
		ActionResult result = contents.useOnBlock(contentsContext);
		// Write any changes to the contents, e.g. count decrement
		contents.writeNbt(contentsNbt);
		nbt.put(CONTENTS_KEY, contentsNbt);

		return result;
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
			} else if (slot.canTakeItems(player) && slot.canTakePartial(player)) {

				ItemStack contents = ItemStack.fromNbt(getOrCreateContents(kete.getOrCreateNbt()));
				int count = maxStackSize(contents) - contents.getCount();

				// TODO: play insert sound if > 0
				addToKete(kete, slot.takeStackRange(stack.getCount(), count, player));
			}

			return true;
		}

		return false;
	}

	@Override
	public void onItemEntityDestroyed(@NotNull ItemEntity entity) {
		ItemStack kete = entity.getStack();

		NbtCompound nbt = kete.getOrCreateNbt();
		NbtCompound contentsNbt = getOrCreateContents(nbt);
		ItemStack contents = ItemStack.fromNbt(contentsNbt);

		// Spawn the contents of the kete
		ItemUsage.spawnItemContents(entity, splitContents(contents).stream());
	}

	@Override
	public void appendTooltip(
		@NotNull ItemStack kete,
		@Nullable World world,
		@NotNull List<Text> tooltip,
		TooltipContext context
	) {
		ItemStack contents = ItemStack.fromNbt(getOrCreateContents(kete.getOrCreateNbt()));

		if (!contents.isEmpty()) {
			KeteTooltipData data = new KeteTooltipData(contents);

			tooltip.add(
				Text.translatable(
					this.getTranslationKey() + ".fullness",
					data.getCount(), data.getMaxCount()
				).formatted(Formatting.GRAY)
			);
		}
	}

	@Override
	public Optional<TooltipData> getTooltipData(@NotNull ItemStack kete) {
		ItemStack contents = ItemStack.fromNbt(getOrCreateContents(kete.getOrCreateNbt()));

		return Optional.of(new KeteTooltipData(contents));
	}

	@Override
	public boolean isItemBarVisible(@NotNull ItemStack kete) {
		ItemStack contents = ItemStack.fromNbt(getOrCreateContents(kete.getOrCreateNbt()));

		return !contents.isEmpty();
	}

	@Override
	public int getItemBarStep(@NotNull ItemStack kete) {
		ItemStack contents = ItemStack.fromNbt(getOrCreateContents(kete.getOrCreateNbt()));

		return Math.min(12 * contents.getCount() / maxStackSize(contents), 12) + 1;
	}

	@Override
	public int getItemBarColor(ItemStack kete) {
		return ITEM_BAR_COLOR;
	}

	/**
	 * Splits {@code contents} into stacks based on the maximum stack size for
	 * that item.
	 */
	protected static @NotNull List<ItemStack> splitContents(@NotNull ItemStack contents) {
		List<ItemStack> stacks = Lists.newArrayList();

		int count = contents.getCount();
		while (count > 0) {
			int stackCount = Math.min(count, contents.getMaxCount());
			stacks.add(contents.copyWithCount(stackCount));

			count -= stackCount;
		}

		return stacks;
	}

	protected static @NotNull NbtCompound getOrCreateContents(@NotNull NbtCompound keteNbt) {
		NbtCompound contentsNbt;

		if (!keteNbt.contains(CONTENTS_KEY)) {
			contentsNbt = new NbtCompound();
			ItemStack.EMPTY.writeNbt(contentsNbt);
			keteNbt.put(CONTENTS_KEY, contentsNbt);
		} else {
			contentsNbt = keteNbt.getCompound(CONTENTS_KEY);
		}

		return contentsNbt;
	}
}
