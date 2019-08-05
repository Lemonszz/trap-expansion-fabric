package party.lemons.trapexpansion.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class SpikeTrapWallBlock extends SpikeTrapFloorBlock
{
	protected static final VoxelShape AABB_NORTH = VoxelShapes.cuboid(0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 0.9D);
	protected static final VoxelShape AABB_SOUTH = VoxelShapes.cuboid(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1D);
	protected static final VoxelShape AABB_WEST = VoxelShapes.cuboid(1D, 0D, 0.0D, 0.9D, 1.0D, 1.0D);
	protected static final VoxelShape AABB_EAST = VoxelShapes.cuboid(0.0D, 0D, 0.0D, 0.1D, 1D, 1.0D);

	public static final DirectionProperty DIRECTION_WALL = DirectionProperty.of("direction", f->f.getAxis().isHorizontal());

	public SpikeTrapWallBlock(Settings settings)
	{
		super(settings, true);
		this.setDefaultState(this.stateFactory.getDefaultState().with(OUT, 0).with(DIRECTION_WALL, Direction.NORTH));
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, EntityContext context)
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
		st.add(OUT).add(DIRECTION_WALL).add(WATERLOGGED);
	}
}
