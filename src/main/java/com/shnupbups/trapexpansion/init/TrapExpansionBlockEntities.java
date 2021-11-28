package com.shnupbups.trapexpansion.init;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import com.shnupbups.trapexpansion.TrapExpansion;
import com.shnupbups.trapexpansion.block.entity.DetectorBlockEntity;
import com.shnupbups.trapexpansion.block.entity.FanBlockEntity;

public class TrapExpansionBlockEntities {
	public static void init() {
		registerBlockEntityType("fan", FAN);
		registerBlockEntityType("detector", DETECTOR);
	}	public static BlockEntityType<FanBlockEntity> FAN = createBlockEntityType(FanBlockEntity::new, TrapExpansionBlocks.FAN, TrapExpansionBlocks.ANALOG_FAN);

	public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
		return FabricBlockEntityTypeBuilder.create(factory, blocks).build();
	}	public static BlockEntityType<DetectorBlockEntity> DETECTOR = createBlockEntityType(DetectorBlockEntity::new, TrapExpansionBlocks.DETECTOR);

	public static void registerBlockEntityType(String name, BlockEntityType<?> type) {
		Registry.register(Registry.BLOCK_ENTITY_TYPE, TrapExpansion.id(name), type);
	}




}
