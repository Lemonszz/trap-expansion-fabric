package party.lemons.trapexpansion.block.entity;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import party.lemons.trapexpansion.block.FanBlock;
import party.lemons.trapexpansion.init.TrapExpansionBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class FanBlockEntity extends BlockEntity implements Tickable
{
	private static final int STEP_TIME = 1;
	private static final float SPEED = 1F;
	private static final float RANGE = 8F;

	public FanBlockEntity()
	{
		super(TrapExpansionBlockEntities.FAN_BE);
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

			Direction facing = state.get(FanBlock.FACING);

			Box bb = new Box(0, 0, 0, 1, 1, 1).offset(pos.offset(facing)).expand(facing.getOffsetX() * RANGE, facing.getOffsetY() * RANGE, facing.getOffsetZ() * RANGE);
			List<Entity> entities = world.getEntities(Entity.class, bb, e->true);

			for(int i = 0; i < entities.size(); i++)
			{
				Entity e = entities.get(i);

				int xCheck = facing.getOffsetX() * (MathHelper.floor(e.x) - this.pos.getX());
				int yCheck = facing.getOffsetY() * (MathHelper.floor(e.y) - this.pos.getY());
				int zCheck = facing.getOffsetZ() * (MathHelper.floor(e.z) - this.pos.getZ());

				for(int b = 1; b < Math.abs(xCheck + yCheck + zCheck); b++)
				{
					if(world.getBlockState(this.pos.offset(facing, b)).isOpaque())
					{
						return;
					}
				}

				double distance = e.getPos().distanceTo(new Vec3d(pos));
				float distanceDecay =  Math.max(0, (float) ((RANGE - distance) / (RANGE * 8)));
				float speed = SPEED;
				if(facing == Direction.UP || facing == Direction.DOWN)
					speed += 1;

				float velX = speed * (facing.getOffsetX() * distanceDecay);
				float velY = speed * (facing.getOffsetY() * distanceDecay);
				float velZ = speed * (facing.getOffsetZ() * distanceDecay);

				e.addVelocity(velX,velY,velZ);

				e.fallDistance = Math.max(0, e.fallDistance - 1);
			}
		}
	}
}
