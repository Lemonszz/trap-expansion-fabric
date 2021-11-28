package com.shnupbups.trapexpansion.block.entity;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.shnupbups.trapexpansion.block.DetectorBlock;
import com.shnupbups.trapexpansion.init.TrapExpansionBlockEntities;

public class DetectorBlockEntity extends BlockEntity {
	private static final int STEP_TIME = 4;
	private static final int RANGE = 5;

	public DetectorBlockEntity(BlockPos pos, BlockState state) {
		super(TrapExpansionBlockEntities.DETECTOR, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, DetectorBlockEntity detector) {
		if (world.getTime() % STEP_TIME == 0 && !world.isClient) {
			if (!(state.getBlock() instanceof DetectorBlock))
				return;

			Direction facing = state.get(DetectorBlock.FACING);
			Box bb = new Box(0, 0, 0, 1, 1, 1).offset(pos.offset(facing)).stretch(facing.getOffsetX() * RANGE, facing.getOffsetY() * RANGE, facing.getOffsetZ() * RANGE);
			List<Entity> entities = world.getEntitiesByClass(Entity.class, bb, e -> true);

			int entityCount = entities.size();
			boolean hasEntity = entityCount > 0;

			if (hasEntity) {
				for (Entity e : entities) {
					int xCheck = facing.getOffsetX() * (MathHelper.floor(e.getX()) - pos.getX());
					int yCheck = facing.getOffsetY() * (MathHelper.floor(e.getY()) - pos.getY());
					int zCheck = facing.getOffsetZ() * (MathHelper.floor(e.getZ()) - pos.getZ());

					for (int b = 1; b < Math.abs(xCheck + yCheck + zCheck); b++) {
						if (world.getBlockState(pos.offset(facing, b)).isOpaque()) {
							entityCount--;
							if (entityCount <= 0) {
								hasEntity = false;
								break;
							}
						}
					}
				}
			}

			boolean powered = state.get(DetectorBlock.POWERED);

			if (powered != hasEntity) {
				world.setBlockState(pos, state.with(DetectorBlock.POWERED, hasEntity));
			}
		}
	}
}
