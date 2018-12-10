package party.lemons.trapexpansion.init;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import party.lemons.trapexpansion.block.entity.DetectorBlockEntity;
import party.lemons.trapexpansion.block.entity.FanBlockEntity;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static party.lemons.trapexpansion.TrapExpansion.MODID;

public class TrapExpansionBlockEntities
{
	public static BlockEntityType<FanBlockEntity> FAN_BE;
	public static BlockEntityType<DetectorBlockEntity> DETECTOR_BE;

	public static void init()
	{
		FAN_BE = registerBlockEntityType("fan", FanBlockEntity::new);
		DETECTOR_BE = registerBlockEntityType("detector", DetectorBlockEntity::new);
	}

	public static BlockEntityType registerBlockEntityType(String name, Supplier<BlockEntity> be)
	{
		return Registry.register(Registry.BLOCK_ENTITIES, MODID + ":" + name, BlockEntityType.Builder.create(be).method_11034(null));
	}
}
