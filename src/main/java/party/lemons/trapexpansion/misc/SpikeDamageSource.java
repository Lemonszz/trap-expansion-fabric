package party.lemons.trapexpansion.misc;

import party.lemons.trapexpansion.TrapExpansion;
import net.minecraft.entity.damage.DamageSource;

public class SpikeDamageSource extends DamageSource
{
	public static final SpikeDamageSource SPIKE = new SpikeDamageSource();

	protected SpikeDamageSource()
	{
		super(TrapExpansion.MODID + ".spike");
	}
}
