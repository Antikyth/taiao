// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.block.state;

import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Pair;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Stream;

public enum HorizontalDoubleSquareBlockPart implements StringIdentifiable {
	// These are in clockwise order
	NORTH_WEST("north_west", 0, 0),
	NORTH_EAST("north_east", 1, 0),
	SOUTH_EAST("south_east", 1, 1),
	SOUTH_WEST("south_west", 0, 1);

	private final String name;
	private final int dx;
	private final int dz;

	HorizontalDoubleSquareBlockPart(String name, int dx, int dz) {
		this.name = name;
		this.dx = dx;
		this.dz = dz;
	}

	@Override
	public String asString() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static HorizontalDoubleSquareBlockPart getRandom(@NotNull Random random) {
		return values()[random.nextInt(values().length)];
	}

	@Contract(value = "_ -> new", pure = true)
	public @NotNull BlockPos offsetTo(@NotNull HorizontalDoubleSquareBlockPart part) {
		return new BlockPos(part.dx - this.dx, 0, part.dz - this.dz);
	}

	/**
	 * Returns a stream of diagonal parts, relative to this part.
	 * <p>
	 * This can be used to ensure there is diagonal support underneath the block.
	 */
	public @NotNull Stream<Stream<Pair<BlockPos, HorizontalDoubleSquareBlockPart>>> diagonals() {
		return Stream.of(
			Stream.of(
				new Pair<>(this.offsetTo(NORTH_WEST), NORTH_WEST),
				new Pair<>(this.offsetTo(SOUTH_EAST), SOUTH_EAST)
			),
			Stream.of(
				new Pair<>(this.offsetTo(NORTH_EAST), NORTH_EAST),
				new Pair<>(this.offsetTo(SOUTH_WEST), SOUTH_WEST)
			)
		);
	}

	/**
	 * Returns the part that should be at the {@code offset} from this part.
	 */
	public @Nullable HorizontalDoubleSquareBlockPart getPartAtOffset(Vec3i offset) {
		return this.otherPlacements()
			.filter(placement -> placement.getLeft().equals(offset))
			.map(Pair::getRight)
			.findFirst()
			.orElse(null);
	}

	/**
	 * Returns the appropriate starting part for placement based on the horizontal {@code facing}
	 * direction of the player.
	 */
	@Contract(pure = true)
	@SuppressWarnings("DuplicateBranchesInSwitch")
	public static HorizontalDoubleSquareBlockPart placement(@NotNull Direction facing) {
		return switch (facing) {
			case UP, DOWN -> NORTH_WEST;

			case NORTH -> SOUTH_WEST;
			case WEST -> SOUTH_EAST;
			case SOUTH -> NORTH_EAST;
			case EAST -> NORTH_WEST;
		};
	}

	/**
	 * Returns a stream of positions to the associated parts, relative to this part, including this
	 * part.
	 */
	public Stream<Pair<BlockPos, HorizontalDoubleSquareBlockPart>> allPlacements() {
		return Arrays.stream(values()).map(part -> new Pair<>(this.offsetTo(part), part));
	}

	/**
	 * Returns a stream of other positions and associated parts relative to this part.
	 * <p>
	 * This can be used to place other parts of a block.
	 */
	public Stream<Pair<BlockPos, HorizontalDoubleSquareBlockPart>> otherPlacements() {
		return allPlacements().filter(pair -> pair.getRight() != this);
	}

	public HorizontalDoubleSquareBlockPart rotate(@NotNull BlockRotation rotation) {
		int rotatedOrdinal = (this.ordinal() + rotation.ordinal()) % values().length;

		return values()[rotatedOrdinal];
	}

	@Contract(pure = true)
	public HorizontalDoubleSquareBlockPart mirror(@NotNull BlockMirror mirror) {
		return switch (mirror) {
			// West/east
			case FRONT_BACK -> switch (this) {
				case NORTH_WEST -> NORTH_EAST;
				case NORTH_EAST -> NORTH_WEST;

				case SOUTH_WEST -> SOUTH_EAST;
				case SOUTH_EAST -> SOUTH_WEST;
			};
			// North/south
			case LEFT_RIGHT -> switch (this) {
				case NORTH_WEST -> SOUTH_WEST;
				case NORTH_EAST -> SOUTH_EAST;

				case SOUTH_WEST -> NORTH_WEST;
				case SOUTH_EAST -> NORTH_EAST;
			};
			// None
			case NONE -> this;
		};
	}
}
