package com.shnupbups.trapexpansion.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class SpiderProofBlock extends Block {
	public SpiderProofBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0.001, 0.001, 0.001, 0.998, 0.998, 0.998);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof SpiderEntity) {
			((SpiderEntity) entity).setClimbingWall(false);
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext context) {
		TranslatableText text = new TranslatableText("trapexpansion.tip.spiderproof");
		text.setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY));

		tooltip.add(text);

		super.appendTooltip(stack, world, tooltip, context);
	}
}
