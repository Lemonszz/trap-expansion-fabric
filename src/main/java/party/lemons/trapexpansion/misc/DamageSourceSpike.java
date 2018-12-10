package party.lemons.trapexpansion.misc;

import party.lemons.trapexpansion.TrapExpansion;
import net.minecraft.entity.damage.DamageSource;

public class DamageSourceSpike extends DamageSource
{
	public static final DamageSourceSpike SPIKE = new DamageSourceSpike();

	protected DamageSourceSpike()
	{
		super(TrapExpansion.MODID + ".spike");
	}
}
