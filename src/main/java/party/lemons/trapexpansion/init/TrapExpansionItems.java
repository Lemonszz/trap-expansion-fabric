package party.lemons.trapexpansion.init;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import party.lemons.trapexpansion.TrapExpansion;

public class TrapExpansionItems {
	public static void init() {

	}

	public static void registerItem(Item item, String name) {
		Registry.register(Registry.ITEM, TrapExpansion.id(name), item);
	}
}
