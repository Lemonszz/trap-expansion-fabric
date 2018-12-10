package party.lemons.trapexpansion.block.entity;

import party.lemons.trapexpansion.block.FanBlock;
import party.lemons.trapexpansion.init.TrapExpansionBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.math.Facing;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class FanBlockEntity extends BlockEntity implements Tickable
{
	private static final int STEP_TIME = 1;
	private static final float SPEED = 1F;
	private static final float RANGE = 8F;

	public FanBlockEntity()
	{
		super(TrapExpansionBlocks.FAN_BE);
	}

	@Override
	public void tick()
	{
		if(world.getTime() % STEP_TIME == 0)
		{
			BlockState state = world.getBlockState(pos);

			if(!(state.getBlock() instanceof FanBlock))
				return;

			if(!state.get(FanBlock.POWERED))
				return;

			Facing facing = state.get(FanBlock.FACING);

			BoundingBox bb = new BoundingBox(0, 0, 0, 1, 1, 1).offset(pos.offset(facing)).expand(facing.getOffsetX() * RANGE, facing.getOffsetY() * RANGE, facing.getOffsetZ() * RANGE);
			List<Entity> entities = world.getEntities(Entity.class, bb, e->true);

			for(int i = 0; i < entities.size(); i++)
			{
				Entity e = entities.get(i);

				int xCheck = facing.getOffsetX() * (MathHelper.floor(e.x) - this.pos.getX());
				int yCheck = facing.getOffsetY() * (MathHelper.floor(e.y) - this.pos.getY());
				int zCheck = facing.getOffsetZ() * (MathHelper.floor(e.z) - this.pos.getZ());

				for(int b = 1; b < Math.abs(xCheck + yCheck + zCheck); b++)
				{
					if(world.getBlockState(this.pos.offset(facing, b)).isFullBoundsCubeForCulling())
					{
						return;
					}
				}

				double distance = e.getPos().distanceTo(pos.getX(), pos.getY(), pos.getZ());
				float distanceDecay =  Math.max(0, (float) ((RANGE - distance) / (RANGE * 8)));
				float speed = SPEED;
				if(facing == Facing.UP || facing == Facing.DOWN)
					speed += 1;

				float velX = speed * (facing.getOffsetX() * distanceDecay);
				float velY = speed * (facing.getOffsetY() * distanceDecay);
				float velZ = speed * (facing.getOffsetZ() * distanceDecay);

				if(velX != 0)
					e.velocityX += velX;

				if(velY != 0)
					e.velocityY += velY;

				if(velZ != 0)
					e.velocityZ += velZ;

				e.fallDistance = Math.max(0, e.fallDistance - 1);
			}
		}
	}
}
