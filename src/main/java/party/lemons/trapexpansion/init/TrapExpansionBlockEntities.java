package party.lemons.trapexpansion.init;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import party.lemons.trapexpansion.block.entity.DetectorBlockEntity;
import party.lemons.trapexpansion.block.entity.FanBlockEntity;

import java.util.function.Supplier;

import static party.lemons.trapexpansion.TrapExpansion.MODID;

public class TrapExpansionBlockEntities {
	public static BlockEntityType<FanBlockEntity> FAN_BE;
	public static BlockEntityType<DetectorBlockEntity> DETECTOR_BE;

	public static void init() {
		FAN_BE = registerBlockEntityType("fan", FanBlockEntity::new, TrapExpansionBlocks.FAN, TrapExpansionBlocks.ANALOG_FAN);
		DETECTOR_BE = registerBlockEntityType("detector", DetectorBlockEntity::new, TrapExpansionBlocks.DETECTOR);
	}

	public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String name, Supplier<T> be, Block... blocks) {
		return Registry.register(Registry.BLOCK_ENTITY, MODID + ":" + name, BlockEntityType.Builder.create(be, blocks).build(null));
	}
}
