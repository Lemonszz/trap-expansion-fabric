package party.lemons.trapexpansion.block.entity;

import party.lemons.trapexpansion.block.BlockDetector;
import party.lemons.trapexpansion.init.TrapExpansionBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.math.Facing;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class BlockEntityDetector extends BlockEntity implements Tickable
{
	private static final int STEP_TIME = 4;
	private static final int RANGE = 5;

	public BlockEntityDetector()
	{
		super(TrapExpansionBlocks.DETECTOR_BE);
	}


	@Override
	public void tick()
	{
		if(world.getTime() % STEP_TIME == 0 && !world.isRemote)
		{
			BlockState state = world.getBlockState(pos);

			if(!(state.getBlock() instanceof BlockDetector))
				return;

			Facing facing = state.get(BlockDetector.FACING);
			BoundingBox bb = new BoundingBox(0, 0, 0, 1, 1, 1).offset(pos.offset(facing)).expand(facing.getOffsetX() * RANGE, facing.getOffsetY() * RANGE, facing.getOffsetZ() * RANGE);
			List<Entity> entities = world.getEntities(Entity.class, bb, e->true);

			int entityCount = entities.size();
			boolean hasEntity = entityCount > 0;

			if(hasEntity)
			{
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
							entityCount--;
							if(entityCount <= 0)
							{
								hasEntity = false;
								break;
							}
						}
					}
				}
			}

			boolean powered = state.get(BlockDetector.POWERED);

			if(powered != hasEntity)
			{
				world.setBlockState(pos, state.with(BlockDetector.POWERED, hasEntity));
			}
		}
	}
}
