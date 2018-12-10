package party.lemons.trapexpansion.init;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import static party.lemons.trapexpansion.TrapExpansion.MODID;

public class TrapExpansionItems
{
	public static Item registerItem(Item item, String name)
	{
		Registry.register(Registry.ITEMS, MODID + ":" + name, item);

		return item;
	}

	//Force static stuff to be initialized
	//TODO: probably don't do everything statically :^)
	public static void init(){}
}
