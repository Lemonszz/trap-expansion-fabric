package party.lemons.trapexpansion;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import party.lemons.trapexpansion.init.TrapExpansionBlockEntities;
import party.lemons.trapexpansion.init.TrapExpansionBlocks;
import party.lemons.trapexpansion.init.TrapExpansionItems;
import party.lemons.trapexpansion.init.TrapExpansionSounds;

public class TrapExpansion implements ModInitializer {
	public static final String MOD_ID = "trapexpansion";

	@Override
	public void onInitialize() {
		TrapExpansionBlocks.init();
		TrapExpansionItems.init();
		TrapExpansionSounds.init();
		TrapExpansionBlockEntities.init();
	}

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}
}
