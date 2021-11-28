package com.shnupbups.trapexpansion.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shnupbups.trapexpansion.init.TrapExpansionSounds;
import com.shnupbups.trapexpansion.misc.SpikeDamageSource;

public class PoweredSpikeTrapBlock extends SpikeTrapBlock {
	public PoweredSpikeTrapBlock(Settings settings) {
		super(settings);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!world.isClient && !entity.isRemoved()) {
			int i = state.get(OUT);

			if (i == 2 && world.getTime() % 5 == 0) {
				entity.damage(SpikeDamageSource.SPIKE, 3);
			}

		}
	}

	@Override
	protected void updateState(World world, BlockPos pos, BlockState state, int outValue) {
		int change = 0;
		boolean powered = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.offset(state.get(FACING).getOpposite()));
		if (!powered) {
			change = -1;
		} else if (outValue < 2) {
			change = 1;
		}

		int endValue = Math.max(0, outValue + change);
		if (change != 0) {

			SoundEvent sound = TrapExpansionSounds.SOUND_SPIKE_1;
			if (endValue == 2)
				sound = TrapExpansionSounds.SOUND_SPIKE_2;

			world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1F, 0.5F + (world.random.nextFloat() / 2));
		}

		world.setBlockState(pos, state.with(OUT, endValue));
		world.scheduleBlockRerenderIfNeeded(pos, state, state.with(OUT, endValue));
		if (endValue != 2 || !powered)
			world.createAndScheduleBlockTick(pos, this, this.getTickRate());
	}
}
