package party.lemons.trapexpansion.block;

import party.lemons.trapexpansion.block.entity.DetectorBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.RenderTypeBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.FacingProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Facing;
import net.minecraft.world.BlockView;

public class DetectorBlock extends BlockWithEntity
{
	public static final BooleanProperty POWERED = Properties.POWERED;
	public static final FacingProperty FACING = Properties.FACING;

	public DetectorBlock(Settings var1)
	{
		super(var1);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView var1)
	{
		return new DetectorBlockEntity();
	}

	@Override
	public boolean emitsRedstonePower(BlockState var1)
	{
		return true;
	}

	@Override
	public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Facing facing)
	{
		return state.getWeakRedstonePower(world, pos, facing);
	}

	@Override
	public int getWeakRedstonePower(BlockState state, BlockView blockView, BlockPos pos, Facing facing)
	{
		return state.get(POWERED).booleanValue() && state.get(FACING) == facing ? 15 : 0;
	}


	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Override
	public boolean hasBlockEntity()
	{
		return true;
	}

	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> st)
	{
		st.with(FACING).with(POWERED);
	}

	@Override
	public RenderTypeBlock getRenderType(BlockState var1) {
		return RenderTypeBlock.MODEL;
	}
}
