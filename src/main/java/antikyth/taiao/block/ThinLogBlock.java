package antikyth.taiao.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class ThinLogBlock extends ConnectingBlock implements Waterloggable, Strippable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    /**
     * The radius of the log, i.e. half its width.
     */
    protected static final float RADIUS = 3f / 16f;

    public ThinLogBlock(Settings settings) {
        super(RADIUS, settings);

        this.setDefaultState(this.getStateManager()
                .getDefaultState()
                .with(WATERLOGGED, false)
                .with(UP, false)
                .with(DOWN, false)
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false));
    }

    public static BooleanProperty getDirectionProperty(Direction direction) {
        return switch (direction) {
            case UP -> UP;
            case DOWN -> DOWN;
            case NORTH -> NORTH;
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
        };
    }

    public BlockState getState(@NotNull BlockView world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);

        return getStateWith(
                this.getDefaultState(),
                fluidState.getFluid() == Fluids.WATER,
                shouldConnect(world, pos, Direction.UP),
                shouldConnect(world, pos, Direction.DOWN),
                shouldConnect(world, pos, Direction.NORTH),
                shouldConnect(world, pos, Direction.EAST),
                shouldConnect(world, pos, Direction.SOUTH),
                shouldConnect(world, pos, Direction.WEST)
        );
    }

    public BlockState getStateWith(
            BlockState state,
            boolean waterlogged,
            boolean up,
            boolean down,
            boolean north,
            boolean east,
            boolean south,
            boolean west
    ) {
        return state.with(WATERLOGGED, waterlogged)
                .with(UP, up)
                .with(DOWN, down)
                .with(NORTH, north)
                .with(EAST, east)
                .with(SOUTH, south)
                .with(WEST, west);
    }

    protected boolean shouldConnect(@NotNull BlockView world, @NotNull BlockPos pos, Direction direction) {
        BlockPos potentialConnectionPos = pos.offset(direction);
        BlockState state = world.getBlockState(potentialConnectionPos);

        boolean faceFullSquare = state.isSideSolidFullSquare(world, potentialConnectionPos, direction.getOpposite());
        boolean wallOrFence = direction == Direction.DOWN && (state.isIn(BlockTags.WALLS) || state.isIn(BlockTags.FENCES));

        return state.isIn(TaiaoBlockTags.THIN_LOG_CONNECTION_OVERRIDE) || (!cannotConnect(state) && faceFullSquare) || wallOrFence;
    }


    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getState(ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            WorldAccess world,
            BlockPos pos,
            BlockPos neighborPos
    ) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        boolean up = direction == Direction.UP ? shouldConnect(world, pos, Direction.UP) : state.get(UP);
        boolean down = direction == Direction.DOWN ? shouldConnect(world, pos, Direction.DOWN) : state.get(DOWN);
        boolean north = direction == Direction.NORTH ? shouldConnect(world, pos, Direction.NORTH) : state.get(NORTH);
        boolean east = direction == Direction.EAST ? shouldConnect(world, pos, Direction.EAST) : state.get(EAST);
        boolean south = direction == Direction.SOUTH ? shouldConnect(world, pos, Direction.SOUTH) : state.get(SOUTH);
        boolean west = direction == Direction.WEST ? shouldConnect(world, pos, Direction.WEST) : state.get(WEST);

        return state.with(UP, up)
                .with(DOWN, down)
                .with(NORTH, north)
                .with(EAST, east)
                .with(SOUTH, south)
                .with(WEST, west);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_90 -> state.with(NORTH, state.get(WEST))
                    .with(EAST, state.get(NORTH))
                    .with(SOUTH, state.get(EAST))
                    .with(WEST, state.get(SOUTH));
            case CLOCKWISE_180 -> state.with(NORTH, state.get(SOUTH))
                    .with(EAST, state.get(WEST))
                    .with(SOUTH, state.get(NORTH))
                    .with(WEST, state.get(EAST));
            case COUNTERCLOCKWISE_90 -> state.with(NORTH, state.get(EAST))
                    .with(EAST, state.get(SOUTH))
                    .with(SOUTH, state.get(WEST))
                    .with(WEST, state.get(NORTH));
            default -> state;
        };
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
            case FRONT_BACK -> state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
            default -> super.mirror(state, mirror);
        };
    }
}
