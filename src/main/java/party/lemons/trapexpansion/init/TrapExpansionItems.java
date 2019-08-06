package party.lemons.trapexpansion.init;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import static party.lemons.trapexpansion.TrapExpansion.MODID;

public class TrapExpansionItems {
	public static void init() {

	}

	public static Item registerItem(Item item, String name) {
		Registry.register(Registry.ITEM, MODID + ":" + name, item);

		return item;
	}
}
