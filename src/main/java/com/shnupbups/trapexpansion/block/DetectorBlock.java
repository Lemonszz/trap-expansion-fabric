package com.shnupbups.trapexpansion.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import com.shnupbups.trapexpansion.block.entity.DetectorBlockEntity;
import com.shnupbups.trapexpansion.init.TrapExpansionBlockEntities;

public class DetectorBlock extends BlockWithEntity {
	public static final BooleanProperty POWERED = Properties.POWERED;
	public static final DirectionProperty FACING = Properties.FACING;

	public DetectorBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DetectorBlockEntity(pos, state);
	}

	@Override
	public boolean emitsRedstonePower(BlockState state) {
		return true;
	}

	@Override
	public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return state.getWeakRedstonePower(world, pos, direction);
	}

	@Override
	public int getWeakRedstonePower(BlockState state, BlockView blockView, BlockPos pos, Direction direction) {
		return state.get(POWERED) && state.get(FACING) == direction ? 15 : 0;
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
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, TrapExpansionBlockEntities.DETECTOR, DetectorBlockEntity::tick);
	}
}
