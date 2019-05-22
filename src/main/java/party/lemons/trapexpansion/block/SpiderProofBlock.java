package party.lemons.trapexpansion.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormat;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class SpiderProofBlock extends Block
{
	public SpiderProofBlock(Settings settings)
	{
		super(settings);
	}

	@Deprecated
	@Override
	public VoxelShape method_9571(BlockState var1, BlockView var2, BlockPos var3) {
		return VoxelShapes.cuboid(0.001, 0.001, 0.001,  0.998, 0.998, 0.998);
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
	public void buildTooltip(ItemStack stack, BlockView world, List<Component> tooltip, TooltipContext context)
	{
		TranslatableComponent text = new TranslatableComponent("trapexpansion.tip.spiderproof");
		text.setStyle(new Style().setColor(ChatFormat.DARK_GRAY));

		tooltip.add(text);

		super.buildTooltip(stack, world, tooltip, context);
	}
}
