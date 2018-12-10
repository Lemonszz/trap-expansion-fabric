package party.lemons.trapexpansion.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.FacingProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Facing;
import net.minecraft.util.shape.VoxelShapeContainer;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class BlockSpikeTrapWall extends BlockSpikeTrapVertical
{
	protected static final VoxelShapeContainer AABB_NORTH = VoxelShapes.cube(0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 0.9D);
	protected static final VoxelShapeContainer AABB_SOUTH = VoxelShapes.cube(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1D);
	protected static final VoxelShapeContainer AABB_WEST = VoxelShapes.cube(1D, 0D, 0.0D, 0.9D, 1.0D, 1.0D);
	protected static final VoxelShapeContainer AABB_EAST = VoxelShapes.cube(0.0D, 0D, 0.0D, 0.1D, 1D, 1.0D);

	public static final FacingProperty DIRECTION_WALL = FacingProperty.create("direction", f->f.getAxis().isHorizontal());

	public BlockSpikeTrapWall(Settings settings)
	{
		super(settings, true);
		this.setDefaultState(this.stateFactory.getDefaultState().with(OUT, 0).with(DIRECTION_WALL, Facing.NORTH));
	}

	public VoxelShapeContainer getBoundingShape(BlockState state, BlockView world, BlockPos pos)
	{
		switch(state.get(DIRECTION_WALL))
		{
			case NORTH:
				return AABB_NORTH;
			case SOUTH:
				return AABB_SOUTH;
			case WEST:
				return AABB_WEST;
			case EAST:
				return AABB_EAST;
			default:
				return AABB_EAST;
		}
	}


	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> st) {
		st.with(OUT).with(DIRECTION_WALL).with(WATERLOGGED);
	}
}
