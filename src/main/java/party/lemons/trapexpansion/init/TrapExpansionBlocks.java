package party.lemons.trapexpansion.init;

import party.lemons.trapexpansion.block.*;
import party.lemons.trapexpansion.block.entity.DetectorBlockEntity;
import party.lemons.trapexpansion.block.entity.FanBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.registry.Registry;

import static party.lemons.trapexpansion.TrapExpansion.MODID;

public class TrapExpansionBlocks
{
	public static final Block SLIPPERY_STONE;
	public static final Block SPIKE_TRAP;
	public static final Block SPIKE_TRAP_WALL;
	public static final Block FAN;
	public static final Block DETECTOR;

	public static final BlockEntityType<FanBlockEntity> FAN_BE;
	public static final BlockEntityType<DetectorBlockEntity> DETECTOR_BE;

	static
	{

		SLIPPERY_STONE = registerBlock(new SpiderProofBlock(Block.Settings.create(Material.STONE).setStrength(0.5F, 1.5F)), "slippery_stone");
		SPIKE_TRAP = registerBlock(new SpikeTrapFloorBlock(Block.Settings.create(Material.ANVIL).setStrength(0.5F, 1.5F)), "spike_trap");
		SPIKE_TRAP_WALL = registerBlock(new SpikeTrapWallBlock(Block.Settings.create(Material.ANVIL).setStrength(0.5F, 1.5F).copyDropTable(SPIKE_TRAP)), "spike_trap_wall", false);
		FAN = registerBlock( new FanBlock(Block.Settings.create(Material.STONE).setStrength(0.5F, 1.5F)), "fan");
		DETECTOR = registerBlock(new DetectorBlock(Block.Settings.create(Material.STONE).setStrength(0.5F, 1.5F)), "detector");

		//TODO: move these somewhere else
		//TODO: create method for easy registration
		FAN_BE = Registry.register(Registry.BLOCK_ENTITIES, MODID + ":" + "fan", BlockEntityType.Builder.create(FanBlockEntity::new).method_11034(null));
		DETECTOR_BE = Registry.register(Registry.BLOCK_ENTITIES, MODID + ":" + "detector", BlockEntityType.Builder.create(DetectorBlockEntity::new).method_11034(null));
	}

	private static Block registerBlock(Block block, String name)
	{
		return registerBlock(block, name, true);
	}

	private static Block registerBlock(Block block, String name, boolean doItem)
	{
		Registry.register(Registry.BLOCKS, MODID + ":" + name, block);

		if(doItem)
		{
			BlockItem item = new BlockItem(block, new Item.Settings().itemGroup(ItemGroup.REDSTONE));
			item.registerBlockItemMap(Item.BLOCK_ITEM_MAP, item);
			TrapExpansionItems.registerItem(item, name);
		}
		return block;
	}

	//Force static stuff to be initialized
	//TODO: probably don't do everything statically :^)
	public static void init(){}
}
