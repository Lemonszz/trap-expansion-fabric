package com.shnupbups.trapexpansion.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import com.shnupbups.trapexpansion.block.FanBlock;
import com.shnupbups.trapexpansion.init.TrapExpansionBlockEntities;

public class FanBlockEntity extends BlockEntity {
	private static final int STEP_TIME = 1;
	private static final float SPEED = 1F;

	public FanBlockEntity(BlockPos pos, BlockState state) {
		super(TrapExpansionBlockEntities.FAN, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, FanBlockEntity fan) {
		if (world.getTime() % STEP_TIME == 0) {
			if (!(state.getBlock() instanceof FanBlock))
				return;

			if (!state.get(FanBlock.POWERED))
				return;

			Direction facing = state.get(FanBlock.FACING);
			double range = ((FanBlock) state.getBlock()).getFanRange(state);

			Box bb = new Box(0, 0, 0, 1, 1, 1).offset(pos.offset(facing)).stretch(facing.getOffsetX() * range, facing.getOffsetY() * range, facing.getOffsetZ() * range);

			for (Entity e : world.getEntitiesByClass(Entity.class, bb, e -> true)) {
				int xCheck = facing.getOffsetX() * (MathHelper.floor(e.getX()) - pos.getX());
				int yCheck = facing.getOffsetY() * (MathHelper.floor(e.getY()) - pos.getY());
				int zCheck = facing.getOffsetZ() * (MathHelper.floor(e.getZ()) - pos.getZ());

				for (int b = 1; b < Math.abs(xCheck + yCheck + zCheck); b++) {
					BlockPos checkPos = pos.offset(facing, b);
					BlockState checkState = world.getBlockState(checkPos);
					if (checkState.isSideSolidFullSquare(world, checkPos, facing) || checkState.isSideSolidFullSquare(world, checkPos, facing.getOpposite())) {
						return;
					}
				}

				double distance = e.getPos().distanceTo(new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
				float distanceDecay = Math.max(0, (float) ((range - distance) / (range * 8)));
				float speed = SPEED;
				if (facing == Direction.UP || facing == Direction.DOWN)
					speed += 1;

				float velX = speed * (facing.getOffsetX() * distanceDecay);
				float velY = speed * (facing.getOffsetY() * distanceDecay);
				float velZ = speed * (facing.getOffsetZ() * distanceDecay);

				e.addVelocity(velX, velY, velZ);

				e.fallDistance = Math.max(0, e.fallDistance - 1);
			}
		}
	}
}
