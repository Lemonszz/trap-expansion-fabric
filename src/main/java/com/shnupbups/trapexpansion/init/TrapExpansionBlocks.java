package com.shnupbups.trapexpansion.init;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import com.shnupbups.trapexpansion.TrapExpansion;
import com.shnupbups.trapexpansion.block.AnalogFanBlock;
import com.shnupbups.trapexpansion.block.DetectorBlock;
import com.shnupbups.trapexpansion.block.FanBlock;
import com.shnupbups.trapexpansion.block.PoweredSpikeTrapBlock;
import com.shnupbups.trapexpansion.block.SpiderProofBlock;
import com.shnupbups.trapexpansion.block.SpikeTrapBlock;

public class TrapExpansionBlocks {
	public static Block SLIPPERY_STONE = new SpiderProofBlock(FabricBlockSettings.of(Material.STONE).strength(0.5F, 1.5F));
	public static Block SPIKE_TRAP = new SpikeTrapBlock(FabricBlockSettings.of(Material.REPAIR_STATION).nonOpaque().strength(0.5F, 1.5F));
	public static Block POWERED_SPIKE_TRAP = new PoweredSpikeTrapBlock(FabricBlockSettings.of(Material.REPAIR_STATION).nonOpaque().strength(0.5F, 1.5F));
	public static Block FAN = new FanBlock(FabricBlockSettings.of(Material.STONE).strength(0.5F, 1.5F));
	public static Block ANALOG_FAN = new AnalogFanBlock(FabricBlockSettings.of(Material.STONE).strength(0.5F, 1.5F));
	public static Block DETECTOR = new DetectorBlock(FabricBlockSettings.of(Material.STONE).strength(0.5F, 1.5F));

	public static void init() {
		registerBlock(SLIPPERY_STONE, "slippery_stone");
		registerBlock(SPIKE_TRAP, "spike_trap");
		registerBlock(POWERED_SPIKE_TRAP, "powered_spike_trap");
		registerBlock(FAN, "fan");
		registerBlock(ANALOG_FAN, "analog_fan");
		registerBlock(DETECTOR, "detector");
	}

	private static void registerBlock(Block block, String name) {
		registerBlock(block, name, true);
	}

	private static void registerBlock(Block block, String name, boolean doItem) {
		Registry.register(Registry.BLOCK, TrapExpansion.id(name), block);

		if (doItem) {
			BlockItem item = new BlockItem(block, new Item.Settings().group(ItemGroup.REDSTONE));
			item.appendBlocks(Item.BLOCK_ITEMS, item);
			TrapExpansionItems.registerItem(item, name);
		}
	}
}
