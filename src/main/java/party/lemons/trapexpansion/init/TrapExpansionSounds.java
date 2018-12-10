package party.lemons.trapexpansion.init;

import party.lemons.trapexpansion.TrapExpansion;
import net.minecraft.sound.Sound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TrapExpansionSounds
{
	public static final Sound SOUND_SPIKE_1 = register("spike_out_1");
	public static final Sound SOUND_SPIKE_2 = register("spike_out_2");

	private static Sound register(String name)
	{
		return Registry.register(Registry.SOUNDS, TrapExpansion.MODID + ":" + name, new Sound(new Identifier(TrapExpansion.MODID + ":" + name)));
	}

	//Force static stuff to be initialized
	//TODO: probably don't do everything statically :^)
	public static void init(){}
}
