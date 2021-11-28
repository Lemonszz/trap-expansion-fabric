package com.shnupbups.trapexpansion.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

import com.shnupbups.trapexpansion.TrapExpansion;

public class TrapExpansionSounds {
	public static SoundEvent SOUND_SPIKE_1 = new SoundEvent(TrapExpansion.id("spike_out_1"));
	public static SoundEvent SOUND_SPIKE_2 = new SoundEvent(TrapExpansion.id("spike_out_2"));

	public static void init() {
		register(SOUND_SPIKE_1);
		register(SOUND_SPIKE_2);
	}

	private static void register(SoundEvent soundEvent) {
		Registry.register(Registry.SOUND_EVENT, soundEvent.getId(), soundEvent);
	}
}
