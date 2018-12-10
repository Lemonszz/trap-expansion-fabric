package party.lemons.trapexpansion.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import party.lemons.trapexpansion.block.entity.FanBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.FacingProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Facing;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class FanBlock extends BlockWithEntity
{
	public static final BooleanProperty POWERED = Properties.POWERED;
	public static final FacingProperty FACING = Properties.FACING;

	public FanBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.stateFactory.getDefaultState().with(POWERED, false).with(FACING, Facing.SOUTH));
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if(state.get(POWERED) && random.nextInt(3) == 0)
		{
			Facing facing = state.get(FACING);
			double xPos = pos.offset(facing).getX() + random.nextFloat();
			double yPos = pos.offset(facing).getY() + random.nextFloat();
			double zPos = pos.offset(facing).getZ() + random.nextFloat();

			world.method_8406(ParticleTypes.CLOUD, xPos, yPos, zPos, facing.getOffsetX() / 2F, facing.getOffsetY() / 2F, facing.getOffsetZ() / 2F);
		}
	}

	@Override
	public RenderTypeBlock getRenderType(BlockState var1) {
		return RenderTypeBlock.MODEL;
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		boolean powered = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());

		if (powered)
		{
			world.getBlockTickScheduler().schedule(pos, this, this.getTickRate(world));
			world.setBlockState(pos, state.with(POWERED, true));
		}
		else
		{
			if(state.get(POWERED))
			{
				world.getBlockTickScheduler().schedule(pos, this, this.getTickRate(world));
				world.setBlockState(pos, state.with(POWERED, false));
			}
		}
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState)
	{
		if(world.isReceivingRedstonePower(pos))
		{
			world.getBlockTickScheduler().schedule(pos, this, this.getTickRate(world));
			world.setBlockState(pos, state.with(POWERED, true));
		}
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
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new FanBlockEntity();
	}
}
