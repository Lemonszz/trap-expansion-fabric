package com.shnupbups.trapexpansion.block;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.shnupbups.trapexpansion.block.entity.FanBlockEntity;
import com.shnupbups.trapexpansion.init.TrapExpansionBlockEntities;

public class FanBlock extends BlockWithEntity {
	public static final BooleanProperty POWERED = Properties.POWERED;
	public static final DirectionProperty FACING = Properties.FACING;

	public FanBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getStateManager().getDefaultState().with(POWERED, false).with(FACING, Direction.SOUTH));
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(POWERED) && random.nextInt(3) == 0) {
			Direction facing = state.get(FACING);
			double xPos = pos.offset(facing).getX() + (random.nextFloat() / 0.8f);
			double yPos = pos.offset(facing).getY() + (random.nextFloat() / 0.8f);
			double zPos = pos.offset(facing).getZ() + (random.nextFloat() / 0.8f);

			world.addParticle(ParticleTypes.CLOUD, xPos, yPos, zPos, (facing.getOffsetX() / 24F) * getFanRange(state), (facing.getOffsetY() / 24F) * getFanRange(state), (facing.getOffsetZ() / 24F) * getFanRange(state));
		}
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
		boolean powered = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());

		if (powered) {
			world.createAndScheduleBlockTick(pos, this, 0);
			world.setBlockState(pos, state.with(POWERED, true));
		} else {
			if (state.get(POWERED)) {
				world.createAndScheduleBlockTick(pos, this, 0);
				world.setBlockState(pos, state.with(POWERED, false));
			}
		}
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean moved) {
		if (world.isReceivingRedstonePower(pos)) {
			world.createAndScheduleBlockTick(pos, this, 0);
			world.setBlockState(pos, state.with(POWERED, true));
		}
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> st) {
		st.add(FACING).add(POWERED);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new FanBlockEntity(pos, state);
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	public double getFanRange(BlockState state) {
		return 8.5;
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, TrapExpansionBlockEntities.FAN, FanBlockEntity::tick);
	}
}
