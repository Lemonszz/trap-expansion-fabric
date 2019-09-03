package party.lemons.trapexpansion.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import party.lemons.trapexpansion.block.entity.DetectorBlockEntity;

public class DetectorBlock extends BlockWithEntity {
	public static final BooleanProperty POWERED = Properties.POWERED;
	public static final DirectionProperty FACING = Properties.FACING;

	public DetectorBlock(Settings var1) {
		super(var1);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView var1) {
		return new DetectorBlockEntity();
	}

	@Override
	public boolean emitsRedstonePower(BlockState var1) {
		return true;
	}

	@Override
	public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return state.getWeakRedstonePower(world, pos, direction);
	}

	@Override
	public int getWeakRedstonePower(BlockState state, BlockView blockView, BlockPos pos, Direction direction) {
		return state.get(POWERED).booleanValue() && state.get(FACING) == direction ? 15 : 0;
	}


	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Override
	public boolean hasBlockEntity() {
		return true;
	}

	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> st) {
		st.add(FACING).add(POWERED);
	}

	@Override
	public BlockRenderType getRenderType(BlockState var1) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public BlockState rotate(BlockState blockState_1, BlockRotation blockRotation_1) {
		return blockState_1.with(FACING, blockRotation_1.rotate(blockState_1.get(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState blockState_1, BlockMirror blockMirror_1) {
		return blockState_1.rotate(blockMirror_1.getRotation(blockState_1.get(FACING)));
	}
}
