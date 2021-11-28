package com.shnupbups.trapexpansion.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;

import com.shnupbups.trapexpansion.init.TrapExpansionSounds;
import com.shnupbups.trapexpansion.misc.SpikeDamageSource;

public class SpikeTrapBlock extends Block {
	public static final IntProperty OUT = IntProperty.of("out", 0, 2);
	public static final DirectionProperty FACING = Properties.FACING;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	protected static final VoxelShape AABB_UP = VoxelShapes.cuboid(0.0D, 0.0D, 0.0D, 1.0D, 0.06D, 1.0D);
	protected static final VoxelShape AABB_DOWN = VoxelShapes.cuboid(0.0D, 0.94D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final VoxelShape AABB_NORTH = VoxelShapes.cuboid(0.0D, 0.0D, 0.94D, 1.0D, 1.0D, 1.0D);
	protected static final VoxelShape AABB_SOUTH = VoxelShapes.cuboid(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.06D);
	protected static final VoxelShape AABB_WEST = VoxelShapes.cuboid(0.94D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final VoxelShape AABB_EAST = VoxelShapes.cuboid(0.0D, 0.0D, 0.0D, 0.06D, 1.0D, 1.0D);

	public SpikeTrapBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getStateManager().getDefaultState().with(OUT, 0).with(FACING, Direction.UP).with(WATERLOGGED, false));

		if (FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER)
			BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
		if (state.get(WATERLOGGED)) {
			world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch (state.get(FACING)) {
			case NORTH:
				return AABB_NORTH;
			case SOUTH:
				return AABB_SOUTH;
			case WEST:
				return AABB_WEST;
			case EAST:
				return AABB_EAST;
			case DOWN:
				return AABB_DOWN;
			default:
				return AABB_UP;
		}
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return getCollisionShape(state, world, pos, context);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!world.isClient && !entity.isRemoved()) {
			int i = state.get(OUT);

			if (i == 0) {
				this.updateState(world, pos, state, i);
			}

			if (i == 2 && world.getTime() % 5 == 0) {
				entity.damage(SpikeDamageSource.SPIKE, 3);
			}
		}
	}

	@Deprecated
	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block otherBlock, BlockPos otherPos, boolean var6) {
		world.createAndScheduleBlockTick(pos, this, this.getTickRate());
	}

	@Deprecated
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!world.isClient) {
			int i = state.get(OUT);

			if (i > 0 || world.isReceivingRedstonePower(pos)) {
				this.updateState(world, pos, state, i);
			}
		}
	}

	@Deprecated
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean moved) {
		if (state.get(OUT) > 0 || world.isReceivingRedstonePower(pos))
			world.createAndScheduleBlockTick(pos, this, this.getTickRate());
	}

	public int getTickRate() {
		return 5;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fs = ctx.getWorld().getFluidState(ctx.getBlockPos());
		boolean isWater = fs.getFluid() == Fluids.WATER;
		return this.getDefaultState().with(FACING, ctx.getSide()).with(WATERLOGGED, isWater);
	}

	@Deprecated
	@Override
	public boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Deprecated
	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return state.get(OUT);
	}

	protected void updateState(World world, BlockPos pos, BlockState state, int outValue) {
		int change = 0;
		boolean powered = world.isReceivingRedstonePower(pos);

		if (!powered && !hasEntity(world, pos, state)) {
			change = -1;
		} else if (outValue < 2) {
			change = 1;
		}

		int endValue = Math.max(0, outValue + change);
		if (change != 0) {

			SoundEvent sound = TrapExpansionSounds.SOUND_SPIKE_1;
			if (endValue == 2)
				sound = TrapExpansionSounds.SOUND_SPIKE_2;

			world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1F, 0.5F + (world.random.nextFloat() / 2));
		}

		world.setBlockState(pos, state.with(OUT, endValue));
		world.scheduleBlockRerenderIfNeeded(pos, state, state.with(OUT, endValue));
		if (endValue != 2 || !powered)
			world.createAndScheduleBlockTick(pos, this, this.getTickRate());
	}

	protected boolean hasEntity(World world, BlockPos pos, BlockState state) {
		List<? extends Entity> list = world.getEntitiesByClass(Entity.class, new Box(0, 0, 0, 1, 1, 1).offset(pos), e -> true);
		if (!list.isEmpty()) {
			for (Entity entity : list) {
				if (!entity.canAvoidTraps()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> st) {
		st.add(OUT).add(FACING).add(WATERLOGGED);
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}
}
