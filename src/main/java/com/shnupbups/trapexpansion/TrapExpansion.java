package com.shnupbups.trapexpansion;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

import com.shnupbups.trapexpansion.init.TrapExpansionBlockEntities;
import com.shnupbups.trapexpansion.init.TrapExpansionBlocks;
import com.shnupbups.trapexpansion.init.TrapExpansionItems;
import com.shnupbups.trapexpansion.init.TrapExpansionSounds;

public class TrapExpansion implements ModInitializer {
	public static final String MOD_ID = "trapexpansion";

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}

	@Override
	public void onInitialize() {
		TrapExpansionBlocks.init();
		TrapExpansionItems.init();
		TrapExpansionSounds.init();
		TrapExpansionBlockEntities.init();
	}
}
