package party.lemons.trapexpansion.init;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import party.lemons.trapexpansion.TrapExpansion;
import party.lemons.trapexpansion.block.entity.DetectorBlockEntity;
import party.lemons.trapexpansion.block.entity.FanBlockEntity;

public class TrapExpansionBlockEntities {
	public static BlockEntityType<FanBlockEntity> FAN = createBlockEntityType(FanBlockEntity::new, TrapExpansionBlocks.FAN, TrapExpansionBlocks.ANALOG_FAN);
	public static BlockEntityType<DetectorBlockEntity> DETECTOR = createBlockEntityType(DetectorBlockEntity::new, TrapExpansionBlocks.DETECTOR);

	public static void init() {
		registerBlockEntityType("fan", FAN);
		registerBlockEntityType("detector", DETECTOR);
	}

	public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
		return FabricBlockEntityTypeBuilder.create(factory, blocks).build();
	}

	public static void registerBlockEntityType(String name, BlockEntityType<?> type) {
		Registry.register(Registry.BLOCK_ENTITY_TYPE, TrapExpansion.id(name), type);
	}
}
