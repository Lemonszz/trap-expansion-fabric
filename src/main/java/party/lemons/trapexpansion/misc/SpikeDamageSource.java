package party.lemons.trapexpansion.misc;

import net.minecraft.entity.damage.DamageSource;
import party.lemons.trapexpansion.TrapExpansion;

public class SpikeDamageSource extends DamageSource {
	public static final SpikeDamageSource SPIKE = new SpikeDamageSource();

	protected SpikeDamageSource() {
		super(TrapExpansion.MOD_ID + ".spike");
	}
}
