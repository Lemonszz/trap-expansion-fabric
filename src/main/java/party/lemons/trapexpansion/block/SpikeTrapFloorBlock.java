package party.lemons.trapexpansion.block;

import net.minecraft.entity.EntityContext;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import party.lemons.trapexpansion.init.TrapExpansionBlocks;
import party.lemons.trapexpansion.init.TrapExpansionSounds;
import party.lemons.trapexpansion.misc.SpikeDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntegerProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class SpikeTrapFloorBlock extends Block
{
	protected static final VoxelShape AABB_UP = VoxelShapes.cuboid(0.0D, 0.0D, 0.0D, 1.0D, 0.1D, 1.0D);
	protected static final VoxelShape AABB_DOWN =  VoxelShapes.cuboid(0.0D, 0.9D, 0.0D, 1.0D, 1.0D, 1.0D);

	public static final IntegerProperty OUT = IntegerProperty.create("out", 0, 2);
	public static final DirectionProperty DIRECTION = DirectionProperty.create("direction", f->f.getAxis().isVertical());
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	public SpikeTrapFloorBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.stateFactory.getDefaultState().with(OUT, 0).with(DIRECTION, Direction.UP).with(WATERLOGGED, false));
	}

	public SpikeTrapFloorBlock(Settings settings, boolean child)
	{
		super(settings);
	}

	@Override
	public FluidState getFluidState(BlockState var1) {
		return var1.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(var1);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState var1, Direction var2, BlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
		if (var1.get(WATERLOGGED)) {
			var4.getFluidTickScheduler().schedule(var5, Fluids.WATER, Fluids.WATER.getTickRate(var4));
		}

		return super.getStateForNeighborUpdate(var1, var2, var3, var4, var5, var6);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity)
	{
		if(!world.isClient && !entity.removed)
		{
			int i = state.get(OUT);

			if(i == 0)
			{
				this.updateState(world, pos, state, i);
			}

			if(i == 2 && world.getTime() % 5 == 0)
			{
				entity.damage(SpikeDamageSource.SPIKE, 3);
			}

		}
	}

	@Deprecated
	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block var4, BlockPos var5, boolean var6)
	{
		world.getBlockTickScheduler().schedule(pos, this, this.getTickRate(world));
	}

	@Deprecated
	@Override
	public void onScheduledTick(BlockState state, World world,  BlockPos pos, Random random)
	{
		if (!world.isClient)
		{
			int i = state.get(OUT);

			if (i > 0 || world.isReceivingRedstonePower(pos))
			{
				this.updateState(world, pos, state, i);
			}
		}
	}

	@Deprecated
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState state2, boolean bool)
	{
		if(state.get(OUT) > 0 || world.isReceivingRedstonePower(pos))
			world.getBlockTickScheduler().schedule(pos, this, this.getTickRate(world));
	}

	@Override
	public int getTickRate(ViewableWorld var1)
	{
		return 5;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, EntityContext context) {
		if(state.get(DIRECTION) == Direction.UP)
			return AABB_UP;

		return AABB_DOWN;
	}

	@Override
	public boolean isFullBoundsCubeForCulling(BlockState var1)
	{
		return false;
	}

	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		FluidState fs = ctx.getWorld().getFluidState(ctx.getBlockPos());
		boolean isWater = fs.getFluid() == Fluids.WATER;

		if(ctx.getFacing() == Direction.DOWN)
			return this.getDefaultState().with(DIRECTION, Direction.DOWN).with(WATERLOGGED, isWater);


		switch(ctx.getFacing())
		{
			case NORTH:
			case SOUTH:
			case WEST:
			case EAST:
				return TrapExpansionBlocks.SPIKE_TRAP_WALL.getDefaultState().with(SpikeTrapWallBlock.DIRECTION_WALL, ctx.getFacing()).with(WATERLOGGED, isWater);
		}

		return this.getDefaultState().with(WATERLOGGED, isWater);
	}

	@Deprecated
	@Override
	public boolean hasComparatorOutput(BlockState var1) {
		return true;
	}

	@Deprecated
	@Override
	public int getComparatorOutput(BlockState var1, World var2, BlockPos var3) {
		return var1.get(OUT);
	}

	protected void updateState(World world, BlockPos pos, BlockState state, int outValue)
	{
		int change = 0;
		boolean powered = world.isReceivingRedstonePower(pos);

		if(!powered && !hasEntity(world, pos, state))
		{
			change = -1;
		}
		else if(outValue < 2)
		{
			change = 1;
		}

		int endValue = Math.max(0, outValue + change);
		if(change != 0)
		{

			SoundEvent sound = TrapExpansionSounds.SOUND_SPIKE_1;
			if(endValue == 2)
				sound = TrapExpansionSounds.SOUND_SPIKE_2;

			world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1F, 0.5F + (world.random.nextFloat() / 2));
		}

		world.setBlockState(pos, state.with(OUT, endValue));
		world.scheduleBlockRender(pos);
		if(endValue != 2 || !powered)
			world.getBlockTickScheduler().schedule(pos, this, this.getTickRate(world));
	}

	protected boolean hasEntity(World worldIn, BlockPos pos, BlockState state)
	{
		List<? extends Entity > list;
		list= worldIn.getEntities(Entity.class, new BoundingBox(0, 0, 0, 1, 1, 1).offset(pos), e->true);
		if (!list.isEmpty())
		{
			for (Entity entity : list)
			{
				if (!entity.canAvoidTraps())
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> st) {
		st.add(OUT).add(DIRECTION).add(WATERLOGGED);
	}

}
