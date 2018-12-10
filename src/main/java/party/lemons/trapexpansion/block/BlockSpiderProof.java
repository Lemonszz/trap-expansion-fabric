package party.lemons.trapexpansion.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipOptions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapeContainer;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class BlockSpiderProof extends Block
{
	public BlockSpiderProof(Settings settings)
	{
		super(settings);
	}

	@Deprecated
	@Override
	public VoxelShapeContainer getBoundingShape(BlockState var1, BlockView var2, BlockPos var3) {
		return VoxelShapes.cube(0.001, 0.001, 0.001,  0.998, 0.998, 0.998);
	}

	@Deprecated
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity)
	{
		if(entity instanceof SpiderEntity)
		{
			((SpiderEntity)entity).setCanClimb(false);
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void addInformation(ItemStack stack, BlockView world, List<TextComponent> tooltip, TooltipOptions options)
	{
		TranslatableTextComponent text = new TranslatableTextComponent("trapexpansion.tip.spiderproof");
		text.setStyle(new Style().setColor(TextFormat.DARK_GRAY));

		tooltip.add(text);

		super.addInformation(stack, world, tooltip, options);
	}
}
