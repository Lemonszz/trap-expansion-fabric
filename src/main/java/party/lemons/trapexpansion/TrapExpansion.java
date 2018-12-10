package party.lemons.trapexpansion;

import net.fabricmc.api.ModInitializer;
import party.lemons.trapexpansion.init.TrapExpansionBlocks;
import party.lemons.trapexpansion.init.TrapExpansionItems;
import party.lemons.trapexpansion.init.TrapExpansionSounds;

public class TrapExpansion implements ModInitializer
{
	public static final String MODID = "trapexpansion";

	@Override
	public void onInitialize() {
		//make jarbo load static stuff
		//TODO: don't do this
		TrapExpansionBlocks.init();
		TrapExpansionItems.init();
		TrapExpansionSounds.init();
	}

}
