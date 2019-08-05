package party.lemons.trapexpansion.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
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
	public VoxelShape getCollisionShape(BlockState var1, BlockView var2, BlockPos var3, EntityContext context) {
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
	public void buildTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext context)
	{
		TranslatableText text = new TranslatableText("trapexpansion.tip.spiderproof");
		text.setStyle(new Style().setColor(Formatting.DARK_GRAY));

		tooltip.add(text);

		super.buildTooltip(stack, world, tooltip, context);
	}
}
