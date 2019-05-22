package party.lemons.trapexpansion.init;

import net.minecraft.sound.SoundEvent;
import party.lemons.trapexpansion.TrapExpansion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TrapExpansionSounds
{
	public static SoundEvent SOUND_SPIKE_1;
	public static SoundEvent SOUND_SPIKE_2;

	public static void init()
	{
		SOUND_SPIKE_1 = register("spike_out_1");
		SOUND_SPIKE_2 = register("spike_out_2");
	}

	private static SoundEvent register(String name)
	{
		return Registry.register(Registry.SOUND_EVENT, TrapExpansion.MODID + ":" + name, new SoundEvent(new Identifier(TrapExpansion.MODID + ":" + name)));
	}
}
